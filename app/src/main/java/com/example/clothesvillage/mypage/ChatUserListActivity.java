package com.example.clothesvillage.mypage;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.clothesvillage.L;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseActivity;
import com.example.clothesvillage.databinding.ActivityChatUserListBinding;
import com.example.clothesvillage.remote.request.ChatListRequest;
import com.example.clothesvillage.remote.response.ChatListResponse;
import com.example.clothesvillage.remote.response.TradeListResponse;
import com.example.clothesvillage.saleFragment.TradeDetailActivity;
import com.example.clothesvillage.saleFragment.TradeListAdapter;
import com.example.clothesvillage.saleFragment.chat.ChatActivity;
import com.example.clothesvillage.utils.PreferenceHelper;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class ChatUserListActivity extends BaseActivity<ActivityChatUserListBinding> {
    private ChatListAdapter chatListAdapter;

    @Override
    protected int layoutRes() {
        return R.layout.activity_chat_user_list;
    }

    @Override
    protected void onViewCreated() {
        initRecyclerView();
        onLoad();
        binding.ivBack.setOnClickListener(v -> finish());
    }

    private void initRecyclerView() {
        chatListAdapter = new ChatListAdapter(this);
        binding.rvChat.setLayoutManager(new LinearLayoutManager(this));
        binding.rvChat.setHasFixedSize(true);
        binding.rvChat.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        binding.rvChat.setAdapter(chatListAdapter);


        chatListAdapter.setOnItemClickListener(position -> {
            ChatListResponse item = chatListAdapter.getItem(position);
            L.i("::::item " + item);
            Intent intent = new Intent(ChatUserListActivity.this, ChatActivity.class);
            intent.putExtra("chat_no",item.getChat_no());
            intent.putExtra("user_name",item.getUser_name());
            startActivity(intent);
        });


    }

    private void onLoad(){
        compositeDisposable.add(repository.getChatList(new ChatListRequest(Integer.parseInt(PreferenceHelper.getCurrentUser(getApplicationContext()).getUser_no())))
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    binding.loading.setVisibility(View.GONE);
                })
                .subscribe(response -> {
                    L.i(":::::::::::response " + response);
                    chatListAdapter.updateItems(response);
                }, throwable -> {
                    L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                }));
    }

}
