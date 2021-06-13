package com.example.clothesvillage.dashboard.cody;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.clothesvillage.L;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseActivity;
import com.example.clothesvillage.base.OnItemClickListener;
import com.example.clothesvillage.closet.ClosetAdapter;
import com.example.clothesvillage.databinding.ActivityCodyDetailBinding;
import com.example.clothesvillage.databinding.ActivityCodyRegisterBinding;
import com.example.clothesvillage.model.HashTag;
import com.example.clothesvillage.remote.request.ClothesDetailRequest;
import com.example.clothesvillage.remote.request.ClothesListRequest;
import com.example.clothesvillage.remote.request.ReplyInsertRequest;
import com.example.clothesvillage.remote.request.ReplyListRequest;
import com.example.clothesvillage.remote.request.StyleDetailRequest;
import com.example.clothesvillage.remote.request.StylePhotoListRequest;
import com.example.clothesvillage.remote.response.ClothesListResponse;
import com.example.clothesvillage.utils.MessageUtils;
import com.example.clothesvillage.utils.PreferenceHelper;
import com.tedpark.tedonactivityresult.rx2.TedRxOnActivityResult;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;

import static com.example.clothesvillage.utils.KeyboardUtilsKt.hideKeyboard;

public class CodyDetailActivity extends BaseActivity<ActivityCodyDetailBinding> {


    private ImagePagerAdapter imagePagerAdapter;
    private ReplyAdapter replyAdapter;

    private int style_no;
    private int user_no;


    @Override
    protected int layoutRes() {
        return R.layout.activity_cody_detail;
    }

    @Override
    protected void onViewCreated() {

        style_no = getIntent().getIntExtra("style_no", -1);
        user_no = getIntent().getIntExtra("user_no", -1);

        L.i("::::style_no " + style_no + " user_no : " + user_no);

        initRecyclerView();


        onStyleLoad();
        onPhotoListLoad();
        onReplyLoad();
        onReplyInsert();
        binding.ivBack.setOnClickListener(view -> finish());
    }

    private void initRecyclerView() {
        imagePagerAdapter = new ImagePagerAdapter(CodyDetailActivity.this);
        binding.rvPhoto.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvPhoto.setHasFixedSize(true);
        binding.rvPhoto.setAdapter(imagePagerAdapter);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.rvPhoto);


        binding.rvPhoto.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                int visibleFirstPos = -1;
                int visibleEndPos = -1;

                if (manager instanceof LinearLayoutManager) {
                    visibleFirstPos = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
                    visibleEndPos = ((LinearLayoutManager) manager).findLastVisibleItemPosition();

                    int currentPosition = 0;

                    if (visibleFirstPos == 0) {
                        currentPosition = visibleFirstPos;
                    } else if (visibleEndPos == imagePagerAdapter.getItemList().size() - 1) {
                        currentPosition = visibleEndPos;
                    } else {
                        currentPosition = (visibleFirstPos + visibleEndPos) / 2;
                    }

                    binding.tvPhotoIndex.setText((currentPosition + 1) + "/" + imagePagerAdapter.getItemList().size());

                    L.i("::::currentPosition " + currentPosition);

                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        replyAdapter = new ReplyAdapter(CodyDetailActivity.this);
        binding.rvReplyContent.setLayoutManager(new LinearLayoutManager(this));
        binding.rvReplyContent.setHasFixedSize(true);
        binding.rvReplyContent.setAdapter(replyAdapter);
    }


    private void onStyleLoad() {
        L.i(":::ON LOAD");
        compositeDisposable.add(repository.getStyleDetail(new StyleDetailRequest(style_no, user_no))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    L.i("::::결과값 " + response);
                    binding.tvUsreName.setText(response.getUser_name());
                    binding.tvTitle.setText(response.getStyle_name());
                    binding.tvContent.setText(response.getStyle_content());


                    String[] hashTag = response.getHash_tag();

                    if (hashTag.length > 0) {

                        Observable.fromArray(hashTag).map(s -> {
                            return new HashTag("#" + s + " ");
                        }).toList().subscribe(result -> {
                            StringBuilder builder = new StringBuilder();

                            for (int i = 0; i < result.size(); i++) {
                                builder.append(result.get(i).getTag());
                            }
                            binding.tvTag.setText(builder.toString());
                        }, error -> {

                        });
                    }

                    if (response.getGoodCheck().equalsIgnoreCase("Y")) {
                        binding.ivLike.setBackgroundResource(R.drawable.black_heart_button);
                    } else {
                        binding.ivLike.setBackgroundResource(R.drawable.heart_button);
                    }

                    Glide.with(binding.ivProfile.getContext())
                            .load("http://54.180.178.116:8080/" + response.getUser_profile())
                            .placeholder(R.drawable.profile_image_default)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(binding.ivProfile);
                }, throwable -> {
                    L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                }));
    }

    private void onPhotoListLoad() {
        compositeDisposable.add(repository.getStyleDetailImages(new StylePhotoListRequest(style_no))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    binding.tvPhotoIndex.setText("1" + "/" + response.size());
                    imagePagerAdapter.updateItems(response);
                }, throwable -> {
                    L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                }));

    }

    private void onReplyLoad() {
        compositeDisposable.add(repository.getReplyList(new ReplyListRequest(style_no))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    L.e("::::::::::::댓글... " + response.size());
                    replyAdapter.updateItems(response);
                }, throwable -> {
                    L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                }));
    }

    private void onReplyInsert() {
        binding.viewSend.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.editWriteReply.getText())) {
                MessageUtils.showLongToastMsg(getApplicationContext(), "내용을 입력해주세요.");
                return;
            }

            ReplyInsertRequest request = new ReplyInsertRequest(style_no, user_no, binding.editWriteReply.getText().toString());

            compositeDisposable.add(repository.replyInsert(request)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        L.e(":::::::::::::::::::::::::::::::::::::: " + response);
                        replyAdapter.addItem(response);
                        hideKeyboard(this);
                        binding.editWriteReply.setText("");
                        binding.rvReplyContent.scrollToPosition(replyAdapter.getItemCount() - 1);
                    }, throwable -> {
                        L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                    }));
        });

    }

}
