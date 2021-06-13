package com.example.clothesvillage.dashboard.cody;

import android.text.TextUtils;
import android.view.View;

import com.example.clothesvillage.L;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseActivity;
import com.example.clothesvillage.databinding.ActivityCodyDetailRegisterBinding;
import com.example.clothesvillage.remote.request.StyleInsertRequest;
import com.example.clothesvillage.utils.MessageUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

import static com.example.clothesvillage.utils.KeyboardUtilsKt.hideKeyboard;

public class CodyContentRegisterActivity extends BaseActivity<ActivityCodyDetailRegisterBinding> {
    private TagAdapter tagAdapter;
    private String selectedClothesNo = null;
    private LinkedHashMap<String, String> selectedHashTagMap = new LinkedHashMap<>();

    @Override
    protected int layoutRes() {
        return R.layout.activity_cody_detail_register;
    }

    @Override
    protected void onViewCreated() {
        initRecyclerView();

        selectedClothesNo = getIntent().getStringExtra("clothes_no");
        binding.ivBack.setOnClickListener(view -> finish());

        binding.viewTagButton.setOnClickListener(v -> {

            if (TextUtils.isEmpty(binding.editTagContent.getText()) || binding.editTagContent.getText().toString().equalsIgnoreCase("")) {
                MessageUtils.showLongToastMsg(getApplicationContext(), "태그 입력해주세요.");
                return;
            }
            selectedHashTagMap.put(binding.editTagContent.getText().toString(),binding.editTagContent.getText().toString());

            List<String> selectedSet = new ArrayList<>();
            for (Map.Entry<String, String> entey : selectedHashTagMap.entrySet()) {
                selectedSet.add(entey.getValue());
            }
            tagAdapter.updateItems(selectedSet);
            hideKeyboard(CodyContentRegisterActivity.this);
            binding.editTagContent.setText("");
        });


        binding.tvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(binding.editTitle.getText()) || binding.editTitle.getText().toString().equalsIgnoreCase("")) {
                    MessageUtils.showLongToastMsg(getApplicationContext(), "제목을 입력해주세요.");
                    return;
                }

                if (TextUtils.isEmpty(binding.editContent.getText()) || binding.editContent.getText().toString().equalsIgnoreCase("")) {
                    MessageUtils.showLongToastMsg(getApplicationContext(), "내용을 입력해주세요.");
                    return;
                }


                String hash_tag = "";
                ArrayList<String> selectedHashTagList = new ArrayList<>();
                for (Map.Entry<String, String> entey : selectedHashTagMap.entrySet()) {
                    selectedHashTagList.add(String.valueOf(entey.getValue()));
                }
                String style_name = binding.editTitle.getText().toString();
                String style_content = binding.editContent.getText().toString();
                hash_tag = getHashTagResult(selectedHashTagList);

                StyleInsertRequest request = new StyleInsertRequest(style_name,style_content,hash_tag,selectedClothesNo);
                compositeDisposable.add(repository.styleInsert(request)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            L.i("::::response -> " + response);
                            MessageUtils.showLongToastMsg(getApplicationContext(), "코디등록에 성공하였습니다.");
                            setResult(RESULT_OK);
                            finish();
                        }, error -> {
                            MessageUtils.showLongToastMsg(getApplicationContext(), "다시 시도해주세요.");
                        }));
            }
        });

    }

    private void initRecyclerView() {
        tagAdapter = new TagAdapter(CodyContentRegisterActivity.this);
        binding.rvTag.setHasFixedSize(true);
        binding.rvTag.setAdapter(tagAdapter);
    }

    private String getHashTagResult(List<String> list) {
        StringBuilder builder = new StringBuilder();
        if (null != list) {
            for (int i = 0; i < list.size(); i++) {
                if (false == "".equals(list.get(i))) {
                    if (1 < getValidCount(list)) {
                        if (getFirstValidIndex(list) == i) {
                            builder.append(list.get(i));
                        } else {
                            builder.append("|" + list.get(i));
                        }
                    } else {
                        builder.append(list.get(i));
                    }
                }
            }
        }
        return builder.toString();
    }

    private int getValidCount(List<String> list) {
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            if (false == "".equals(list.get(i))) {
                count++;
            }
        }
        return count;
    }

    public int getFirstValidIndex(List<String> list) {
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            if (false == "".equals(list.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public int getLastValidIndex(List<String> list) {
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            if (false == "".equals(list.get(i))) {
                index = i;
            }
        }
        return index;
    }
}
