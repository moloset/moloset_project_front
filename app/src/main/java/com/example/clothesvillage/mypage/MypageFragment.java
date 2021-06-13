package com.example.clothesvillage.mypage;

import android.content.Intent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseFragment;
import com.example.clothesvillage.closet.ClosetFragment;
import com.example.clothesvillage.closet.ClosetRegisterActivity;
import com.example.clothesvillage.databinding.FragmentMypageBinding;
import com.example.clothesvillage.remote.response.UserInfoResponse;
import com.example.clothesvillage.utils.PreferenceHelper;
import com.tedpark.tedonactivityresult.rx2.TedRxOnActivityResult;

import static android.app.Activity.RESULT_OK;

public class MypageFragment extends BaseFragment<FragmentMypageBinding> {


    @Override
    protected int layoutRes() {
        return R.layout.fragment_mypage;
    }

    @Override
    protected void onViewCreated() {


        binding.viewLike.setOnClickListener(v -> startActivity(new Intent(getActivity(), MyLikeActivity.class)));
        binding.viewMyFeed.setOnClickListener(v -> startActivity(new Intent(getActivity(), MyFeedActivity.class)));
        binding.viewMyTread.setOnClickListener(v -> startActivity(new Intent(getActivity(), MyTradeActivity.class)));
        binding.viewChat.setOnClickListener(v -> startActivity(new Intent(getActivity(), ChatUserListActivity.class)));
        binding.btnProfileUpdate.setOnClickListener(v -> TedRxOnActivityResult.with(getActivity())
                .startActivityForResult(new Intent(MypageFragment.this.getActivity(), UserInfoUpdateActivity.class))
                .subscribe(result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        setUserInformation();
                    }
                }, error -> {

                }));


        setUserInformation();

    }

    private void setUserInformation() {
        UserInfoResponse currentUser = PreferenceHelper.getCurrentUser(getActivity());

        if (currentUser != null) {
            binding.tvUserName.setText(PreferenceHelper.getCurrentUser(getActivity()).getUser_name());
            binding.tvUserEmail.setText(PreferenceHelper.getCurrentUser(getActivity()).getUser_email());

            Glide.with(binding.ivProfile.getContext())
                    .load("http://54.180.178.116:8080/" + currentUser.getUser_profile())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(binding.ivProfile);

        }
    }


}
