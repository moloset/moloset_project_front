package com.example.clothesvillage.saleFragment;

import android.content.Intent;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.clothesvillage.L;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseActivity;
import com.example.clothesvillage.databinding.ActivityTradeMapBinding;
import com.example.clothesvillage.utils.MessageUtils;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.tedpark.tedonactivityresult.rx2.TedRxOnActivityResult;

import java.util.Arrays;
import java.util.List;

public class TradeMapActivity extends BaseActivity<ActivityTradeMapBinding> {

    private Mapfragment mMapfragment;
    private FragmentManager mChiFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Place mCurrentPlace;

    @Override
    protected int layoutRes() {
        return R.layout.activity_trade_map;
    }

    @Override
    protected void onViewCreated() {

        //지도 관련 로직을 추가 한다.
        //Google Map 객체를 불러온후 화면에 셋팅을해준다.
        mMapfragment = Mapfragment.getInstance();
        mChiFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mChiFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_placeholder, mMapfragment);
        mFragmentTransaction.commitAllowingStateLoss();


        binding.tvComplete.setOnClickListener(v -> {
            if (mCurrentPlace == null) {
                MessageUtils.showLongToastMsg(getApplicationContext(), "거래 지역을 지정해주세요.");
                return;
            }
            Intent intent = getIntent();
            intent.putExtra("EXTRA_PLACE_KEY", mCurrentPlace);
            setResult(RESULT_OK, intent);
            finish();
        });
        binding.btnSearch.setOnClickListener(v -> showAutoCompletePlace());

        showAutoCompletePlace();
    }

    private void showAutoCompletePlace() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(this);
        TedRxOnActivityResult.with(this)
                .startActivityForResult(intent)
                .subscribe(activityResult -> {
                    L.i(":::::::activityResult " + activityResult.getResultCode());

                    if (activityResult.getResultCode() == RESULT_OK) {
                        Place place = Autocomplete.getPlaceFromIntent(activityResult.getData());
                        L.i("Place: " + place.getName() + ", " + place.getId() + " , " + place.getLatLng());
                        mCurrentPlace = place;
                        mMapfragment.updateCamera(place);
                    }
                });
    }

}
