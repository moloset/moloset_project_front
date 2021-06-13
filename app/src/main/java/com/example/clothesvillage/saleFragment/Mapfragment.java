package com.example.clothesvillage.saleFragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.clothesvillage.L;
import com.example.clothesvillage.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;



public class Mapfragment extends Fragment implements OnMapReadyCallback {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    private final double CAL_TOLERANCE = 3L;
    private GoogleMap mGoogleMap;
    private View mView;
    private View mMarkerView;
    private SupportMapFragment mMapFragment;
    private static Mapfragment mInstance;
    private UiSettings mUiSettings;
    private Handler mHandler = new Handler();
    private CameraMoveCompleteListener mCameraMoveCompleteListener;
    private float mPolyLineWidth;
    private int mBoundaryPadding;

    private Marker mGoalMaker;

    public interface CameraMoveCompleteListener {
        void animateCameraComplete(Location location);
    }

    public void setCameraMoveCompleteListener(CameraMoveCompleteListener listener) {
        this.mCameraMoveCompleteListener = listener;
    }


    public static Mapfragment getInstance() {
        if (mInstance == null) {
            mInstance = new Mapfragment();
        }
        return mInstance;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.map_fragment, container, false);

        FragmentManager childFragMan = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragMan.beginTransaction();
        mMapFragment = SupportMapFragment.newInstance();
        mMapFragment.getMapAsync(this);
        fragmentTransaction.add(R.id.map, mMapFragment);
        fragmentTransaction.commit();
        mPolyLineWidth = TypedValue.applyDimension(1, 4.0F, getContext().getResources().getDisplayMetrics());

        int width = getResources().getDisplayMetrics().widthPixels;

        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View getView() {
        return mView;
    }



    public void updateCamera(com.google.android.libraries.places.api.model.Place place) {
        //검색해서 불러온 동네의 place 값 (좌표 ,주소가 포함되어있는 객체)를 불러와서 db에 있는 데이터들과 500m 이하 체크를 하도록한다.
        if (place != null) {
            final long Time = System.currentTimeMillis();
            final LatLng latLng = place.getLatLng();
            Location goalLocation = new Location("goal_location");
            goalLocation.setLatitude(latLng.latitude);
            goalLocation.setLongitude(latLng.longitude);
            if (mGoalMaker != null) {
                mGoogleMap.clear();
                mGoalMaker.remove();
            }
            mGoalMaker = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(place.getName()));
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng), new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {

                }

                @Override
                public void onCancel() {
                }
            });
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        L.e("onMapReady");
        L.d("googleMap : " + googleMap);
        if (googleMap == null) return;

        mGoogleMap = googleMap;
        mUiSettings = mGoogleMap.getUiSettings();
        mUiSettings.setMyLocationButtonEnabled(true);
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(true);
            if (getLocation() == null) return;
            drawBoundaryOnMap();

        }

    }




    public void drawBoundaryOnMap() {
        updateLocation(getLocation(), true);
    }


    public Location getLocation() {
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocationGPS != null) {
                    return lastKnownLocationGPS;
                } else {
                    Location loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                    return loc;
                }
            } else {
                return null;
            }
        }
        return null;
    }

    public void onLoation() {
        if (mGoogleMap != null) {
            onMapReady(mGoogleMap);
        }
    }

    private void updateLocation(LatLngBounds bounds) {
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, mBoundaryPadding));
    }

    private void updateLocation(List<LatLng> gpslist) {
        Location updateLocation = new Location(Mapfragment.class.getSimpleName());
        L.e("gpslist : " + gpslist.size());
        for (int i = 0; i < gpslist.size(); i++) {
            updateLocation.setLatitude(gpslist.get(i).latitude);
            updateLocation.setLongitude(gpslist.get(i).longitude);
        }
        CameraPosition position = new CameraPosition.Builder().target(new LatLng(updateLocation.getLatitude(), updateLocation.getLongitude()))
                .zoom(16f)
                .bearing(0)
                .build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
    }

    private void updateLocation(Location location, boolean flag) {
        if (location == null) return;

        if (flag) {
            //CameraPosition 유연성 확보를 위해 카메라를 주어진 위치로 이동하는 애니메이션을 보여준다.
            CameraPosition position = new CameraPosition.Builder().target(new LatLng(location.getLatitude(), location.getLongitude()))
                    .zoom(16f)
                    .bearing(0)
                    .build();
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), Math.max(30, 1), null);
        } else {
            //카메라의 위도를 변경할때사용
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
        }
    }

    public String convertMeterToKilometer(int totalDistance) {
        double ff = totalDistance / 1000.0;
        BigDecimal bd = BigDecimal.valueOf(ff);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return String.valueOf(bd.doubleValue());
    }


}
