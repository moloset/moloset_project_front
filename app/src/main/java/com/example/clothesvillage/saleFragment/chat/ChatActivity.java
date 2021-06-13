package com.example.clothesvillage.saleFragment.chat;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.clothesvillage.L;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseActivity;
import com.example.clothesvillage.dashboard.cody.CodyContentRegisterActivity;
import com.example.clothesvillage.dashboard.cody.TagAdapter;
import com.example.clothesvillage.databinding.ActivityChatBinding;
import com.example.clothesvillage.model.ChatItem;
import com.example.clothesvillage.model.RoomData;
import com.example.clothesvillage.remote.request.ChatListRequest;
import com.example.clothesvillage.remote.request.ChatMessageRequest;
import com.example.clothesvillage.remote.request.ChatMsgListRequest;
import com.example.clothesvillage.remote.request.StyleSelectRequest;
import com.example.clothesvillage.utils.MessageUtils;
import com.example.clothesvillage.utils.PreferenceHelper;
import com.google.gson.Gson;


import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.EngineIOException;

public class ChatActivity extends BaseActivity<ActivityChatBinding> {

    private int chat_no;
    private int user_no;
    private Gson gson = new Gson();
    private Socket mSocket;
    private ChatAdapter chatAdapter;

    @Override
    protected int layoutRes() {
        return R.layout.activity_chat;
    }

    @Override
    protected void onViewCreated() {

        chat_no = getIntent().getIntExtra("chat_no", chat_no);
        user_no = Integer.parseInt(PreferenceHelper.getCurrentUser(getApplicationContext()).getUser_no());

        binding.tvTitle.setText(getIntent().getStringExtra("user_name"));

        initRecyclerView();
        onLoad();
        binding.ivBack.setOnClickListener(v -> finish());
    }

    private void initRecyclerView() {
        chatAdapter = new ChatAdapter(ChatActivity.this);
        binding.rvChat.setLayoutManager(new LinearLayoutManager(this));
        binding.rvChat.setHasFixedSize(true);
        binding.rvChat.setAdapter(chatAdapter);
    }

    private void sendMessage() {
        binding.viewSend.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.editWriteReply.getText())) {
                MessageUtils.showLongToastMsg(getApplicationContext(), "내용을 입력해주세요.");
                return;
            }
            mSocket.emit("newMessage", gson.toJson(new ChatMessageRequest(chat_no, user_no, binding.editWriteReply.getText().toString())));
            ChatItem chatItem = new ChatItem(binding.editWriteReply.getText().toString(), ChatType.RIGHT_MESSAGE);
            chatAdapter.addItem(chatItem);
            compositeDisposable.add(repository.sendMsg(new ChatMessageRequest(chat_no, user_no, binding.editWriteReply.getText().toString()))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {

                    }, throwable -> {
                        L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                    }));
            binding.rvChat.scrollToPosition(chatAdapter.getItemCount() - 1);
            binding.editWriteReply.setText("");
        });
    }



    private void initSocket() {
        try {
            mSocket = IO.socket("http://54.180.178.116:8081");
            L.d("SOCKET Connection success : " + mSocket.id());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            L.e(":::::e " + e);
        }
        L.i("::::connect try");

        sendMessage();

        mSocket.connect();


        mSocket.on(Socket.EVENT_CONNECT, args -> {
            L.e("::::::::::소켓 EVENT_CONNECT user_no " + user_no + " chat_no " + chat_no);
            mSocket.emit("enter", gson.toJson(new RoomData(String.valueOf(user_no), String.valueOf(chat_no))));
        });
        mSocket.on(Socket.EVENT_DISCONNECT, args -> {
            L.e("::::::::::소켓 EVENT_DISCONNECT");
        });
        mSocket.on(Socket.EVENT_CONNECT_ERROR, args -> {
            L.e("::::::::::EVENT_CONNECT_ERROR");
            String errorMsg = "";
            if (args[0] instanceof EngineIOException) {
                errorMsg = args[0].toString();
            }

            L.e(":::::소켓 에러 " + errorMsg);
        });
        mSocket.on("update", args -> {
            L.i("::::update " + args[0]);
            ChatMessageRequest data = gson.fromJson(args[0].toString(), ChatMessageRequest.class);
            addChat(data);
        });
    }

    private void addChat(ChatMessageRequest data) {
        runOnUiThread(() -> {
            if (data.getUser_no() != Integer.parseInt(PreferenceHelper.getCurrentUser(getApplicationContext()).getUser_no())) {
                ChatItem chatItem = new ChatItem(data.getContent(), ChatType.LEFT_MESSAGE);
                chatAdapter.addItem(chatItem);
                binding.rvChat.scrollToPosition(chatAdapter.getItemCount() - 1);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSocket != null) {
            L.i("::::mSocket " + mSocket.isActive());
            mSocket.disconnect();
        }
    }

    private void onLoad() {
        L.i("::::::::chat_no " + chat_no);
        compositeDisposable.add(repository.getChatMsgList(new ChatMsgListRequest(chat_no))
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    binding.loading.setVisibility(View.GONE);
                })
                .subscribe(response -> {
                    L.i(":::::::::::response " + response);
                    if (response.size() < 0) {
                        return;
                    }
                    List<ChatItem> list = new ArrayList<>();
                    int myUserNo = Integer.parseInt(PreferenceHelper.getCurrentUser(getApplicationContext()).getUser_no());
                    for (int i = 0; i < response.size(); i++) {

                        list.add(new ChatItem(
                                response.get(i).getContent(),
                                response.get(i).getUser_no() == myUserNo ? ChatType.RIGHT_MESSAGE : ChatType.LEFT_MESSAGE)
                        );
                    }
                    chatAdapter.updateItems(list);
                    binding.rvChat.scrollToPosition(chatAdapter.getItemCount() - 1);
                    initSocket();
                }, throwable -> {
                    L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                }));
    }


}
