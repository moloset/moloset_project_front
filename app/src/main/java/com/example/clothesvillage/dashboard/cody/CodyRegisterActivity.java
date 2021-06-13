package com.example.clothesvillage.dashboard.cody;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.clothesvillage.L;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseActivity;
import com.example.clothesvillage.base.OnItemClickListener;
import com.example.clothesvillage.closet.ClosetAdapter;
import com.example.clothesvillage.closet.ClosetFragment;
import com.example.clothesvillage.closet.ClosetRegisterActivity;
import com.example.clothesvillage.databinding.ActivityCodyRegisterBinding;
import com.example.clothesvillage.remote.request.ClothesListRequest;
import com.example.clothesvillage.remote.response.ClothesListResponse;
import com.example.clothesvillage.utils.MessageUtils;
import com.example.clothesvillage.utils.PreferenceHelper;
import com.tedpark.tedonactivityresult.rx2.TedRxOnActivityResult;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;

public class CodyRegisterActivity extends BaseActivity<ActivityCodyRegisterBinding> {
    private CodySelectAdapter codySelectAdapter;
    private ClosetAdapter closetAdapter;
    private PhotoSelectAdapter photoSelectAdapter;

    private LinkedHashMap<String, ClothesListResponse> selectedPhotoMap = new LinkedHashMap<>();

    @Override
    protected int layoutRes() {
        return R.layout.activity_cody_register;
    }

    @Override
    protected void onViewCreated() {


        initRecyclerView();
        initCodeCategory();
        initCloset();

        binding.ivBack.setOnClickListener(view -> finish());
        binding.tvNext.setOnClickListener(v -> {

            if(selectedPhotoMap.size() <= 0){
                MessageUtils.showLongToastMsg(getApplicationContext(), "최소 한장의 이미지를 선택해주세요.");
                return;
            }
            String clothes_no = "";
            ArrayList<String> selectedList = new ArrayList<>();
            for (Map.Entry<String, ClothesListResponse> entey : selectedPhotoMap.entrySet()) {
                selectedList.add(String.valueOf(entey.getValue().getClothes_no()));
            }
            clothes_no = getClothesNumbers(selectedList);
            L.i(":::::clothes_no " + clothes_no);


            TedRxOnActivityResult.with(this)
                    .startActivityForResult(new Intent(CodyRegisterActivity.this, CodyContentRegisterActivity.class).putExtra("clothes_no", clothes_no))
                    .subscribe(activityResult -> {
                        if (activityResult.getResultCode() == RESULT_OK) {
                            setResult(RESULT_OK);
                            finish();
                        }

                    });
        });
        onLoad("A");
    }

    private void initRecyclerView() {
        codySelectAdapter = new CodySelectAdapter(CodyRegisterActivity.this);
        binding.rvContentCategory.setHasFixedSize(true);
        binding.rvContentCategory.setAdapter(codySelectAdapter);

        closetAdapter = new ClosetAdapter(CodyRegisterActivity.this);
        binding.rvPhoto.setLayoutManager(new GridLayoutManager(CodyRegisterActivity.this, 3));
        binding.rvPhoto.setHasFixedSize(true);
        binding.rvPhoto.setAdapter(closetAdapter);

        photoSelectAdapter = new PhotoSelectAdapter(CodyRegisterActivity.this);
        binding.rvSelectPhoto.setHasFixedSize(true);
        binding.rvSelectPhoto.setAdapter(photoSelectAdapter);
    }

    private void initCloset() {
        closetAdapter.setOnItemClickListener(position -> {
            ClothesListResponse selectItem = closetAdapter.getItem(position);
            L.i("::::selectItem " + selectItem.toString());
            if (selectedPhotoMap.containsKey(String.valueOf(selectItem.getClothes_no()))) {
                selectedPhotoMap.remove(String.valueOf(selectItem.getClothes_no()));
            } else {
                selectedPhotoMap.put(String.valueOf(selectItem.getClothes_no()), selectItem);
            }
            List<ClothesListResponse> selectedSet = new ArrayList<>();
            for (Map.Entry<String, ClothesListResponse> entey : selectedPhotoMap.entrySet()) {
                selectedSet.add(entey.getValue());
            }
            photoSelectAdapter.updateItems(selectedSet);
        });
    }


    private void initCodeCategory() {
        ArrayList<CodyType> dataSet = new ArrayList<>();
        dataSet.add(new CodyType("ALL", "A", true));
        dataSet.add(new CodyType("아우터", "3", false));
        dataSet.add(new CodyType("상의", "1", false));
        dataSet.add(new CodyType("하의", "2", false));
        dataSet.add(new CodyType("신발", "4", false));
        dataSet.add(new CodyType("가방", "5", false));
        dataSet.add(new CodyType("기타", "6", false));

        codySelectAdapter.addItems(dataSet);
        codySelectAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                CodyType selectItem = codySelectAdapter.getItem(position);
                compositeDisposable.add(Observable.fromIterable(codySelectAdapter.getItemList())
                        .map(codyType -> {
                            if (selectItem.getName().equalsIgnoreCase(codyType.getName())) {
                                codyType.setSelected(true);
                            } else {
                                codyType.setSelected(false);
                            }
                            return codyType;
                        })
                        .toList()
                        .subscribe(result -> {
                            onLoad(selectItem.getCategoryType());
                            codySelectAdapter.updateItems(result);
                        }, error -> {
                            L.e("::::e " + error);
                        }));
            }
        });
    }


    private void onLoad(String clothes_category) {
        L.i(":::ON LOAD " + clothes_category);
        binding.loading.setVisibility(View.VISIBLE);
        compositeDisposable.add(repository.getClothesList(new ClothesListRequest(Integer.parseInt(PreferenceHelper.getCurrentUser(getApplicationContext()).getUser_no()), clothes_category))
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    binding.loading.setVisibility(View.GONE);
                })
                .subscribe(response -> {
                    closetAdapter.updateItems(response);
                }, throwable -> {
                    L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                }));
    }

    private String getClothesNumbers(List<String> list) {
        StringBuilder builder = new StringBuilder();
        if (null != list) {
            for (int i = 0; i < list.size(); i++) {
                if (false == "".equals(list.get(i))) {
                    if (1 < getValidCount(list)) {
                        if (getFirstValidIndex(list) == i) {
                            builder.append(list.get(i));
                        } else {
                            builder.append("," + list.get(i));
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
