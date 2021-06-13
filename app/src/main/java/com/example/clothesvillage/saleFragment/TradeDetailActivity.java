package com.example.clothesvillage.saleFragment;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupMenu;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.clothesvillage.L;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseActivity;
import com.example.clothesvillage.dashboard.cody.CodyContentRegisterActivity;
import com.example.clothesvillage.databinding.ActivityTradeDetailBinding;
import com.example.clothesvillage.mypage.MyFeedActivity;
import com.example.clothesvillage.remote.request.ChatRoomRequest;
import com.example.clothesvillage.remote.request.StyleDeleteRequest;
import com.example.clothesvillage.remote.request.TradeDetailRequest;
import com.example.clothesvillage.remote.request.TradeUpdateRequest;
import com.example.clothesvillage.remote.response.TradeDetailResponse;
import com.example.clothesvillage.saleFragment.chat.ChatActivity;
import com.example.clothesvillage.utils.MessageUtils;
import com.example.clothesvillage.utils.PreferenceHelper;
import com.tedpark.tedonactivityresult.rx2.TedRxOnActivityResult;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class TradeDetailActivity extends BaseActivity<ActivityTradeDetailBinding> {
    private final String baseUrl = "http://54.180.178.116:8080/";
    private int trade_no;
    private boolean isMyself;
    private TradeDetailResponse currentResponse;

    @Override
    protected int layoutRes() {
        return R.layout.activity_trade_detail;
    }

    @Override
    protected void onViewCreated() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        trade_no = getIntent().getIntExtra("trade_no", -1);
        isMyself = getIntent().getBooleanExtra("myself", false);
        binding.ivEdit.setVisibility(isMyself ? View.VISIBLE : View.GONE);
        binding.viewBottom.setVisibility(isMyself ? View.GONE : View.VISIBLE);
        onLoad(trade_no);
        setCreateChatRoom();

        binding.ivBack.setOnClickListener(view -> finish());
        binding.ivEdit.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(getApplicationContext(),v);
            getMenuInflater().inflate(R.menu.card_update,popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                        case R.id.modify:
                            if(currentResponse.getTrade_case().equalsIgnoreCase("3")){

                            }else{
                                compositeDisposable.add(repository.tradeUpdate(new TradeUpdateRequest(trade_no,"3"))
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(response -> {
                                            if(response.getResult().equalsIgnoreCase("success")){
                                                setResult(RESULT_OK);
                                                finish();
                                            }else{
                                                MessageUtils.showLongToastMsg(getApplicationContext(),"변경에 실패하였습니다.");
                                            }
                                        }, throwable -> {
                                            L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                                            MessageUtils.showLongToastMsg(getApplicationContext(),"변경에 실패하였습니다.");
                                        }));
                            }

                            return true;

                }
                return false;
            });
            popup.show();
        });

    }

    private void setCreateChatRoom() {
        binding.btnCreateChat.setOnClickListener(v -> {
            if(currentResponse == null){
                return;
            }

            int user_no1 = Integer.parseInt(currentResponse.getUser_no());
            int user_no2 = Integer.parseInt(PreferenceHelper.getCurrentUser(getApplicationContext()).getUser_no());

            if(user_no1 == user_no2){
                MessageUtils.showLongToastMsg(getApplicationContext(),"자신과의 채팅을 할수 없습니다.");
                return;
            }
            compositeDisposable.add(repository.getChatRoom(new ChatRoomRequest(trade_no, user_no1, user_no2))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        Intent intent = new Intent(TradeDetailActivity.this, ChatActivity.class);
                        intent.putExtra("chat_no",response.getChat_no());
                        intent.putExtra("user_name",currentResponse.getUser_name());
                        startActivity(intent);
                    }, throwable -> {
                        L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                    }));
        });
    }

    private void onLoad(int trade_no) {
        compositeDisposable.add(repository.getTradeDetail(new TradeDetailRequest(trade_no))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    this.currentResponse = response;
                    DecimalFormat moenyFormat = new DecimalFormat("###,###");
                    String formattedStringPrice = moenyFormat.format(Integer.parseInt(response.getTrade_price()));




                    binding.tvName.setText(response.getTrade_name());
                    binding.tvType.setText(response.getTrade_case());
                    binding.tvPrice.setText(formattedStringPrice);
                    binding.tvAddressContent.setText(response.getTrade_area_name());
                    binding.tvUserName.setText(response.getUser_name());
                    binding.editWriteContent.setText(response.getTrade_content());


                    Glide.with(binding.ivPhoto.getContext())
                            .load(baseUrl + response.getPhoto())
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(binding.ivPhoto);
                }, throwable -> {
                    L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                }));
    }


}
