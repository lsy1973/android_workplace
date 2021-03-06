package com.example.paindiarysecond.ui.maps;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.paindiarysecond.databinding.MapsFragmentBinding;

public class MapsFragment extends Fragment {

    private MapsFragmentBinding binding;
    private MapView mMapView;
    private AMap aMap;
    public static MapsFragment newInstance() {
        return new MapsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MapsFragmentBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        mMapView = binding.map;
        mMapView.onCreate(savedInstanceState);
        initmap();
        getlocation();
        binding.btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = binding.editSearch.getText().toString();
                getCoordinate(data);
            }
        });
        return root;
    }


    public void initmap(){
        if(aMap == null){
            aMap = mMapView.getMap();
            //aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        }
    }

    public void getlocation(){
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//??????????????????????????????
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//??????????????????????????????????????????????????????????????????????????????????????????
        myLocationStyle.interval(2000);
        aMap.setMyLocationStyle(myLocationStyle);//?????????????????????Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//?????????????????????????????????????????????????????????
        aMap.setMyLocationEnabled(true);// ?????????true?????????????????????????????????false??????????????????????????????????????????????????????false???
    }

    public void getCoordinate(String cityName){
        GeocodeSearch geocodeSearch=new GeocodeSearch(this.getContext());
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
            }
            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                if (i==1000){
                    if (geocodeResult!=null && geocodeResult.getGeocodeAddressList()!=null &&
                            geocodeResult.getGeocodeAddressList().size()>0){
                        GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
                        double latitude = geocodeAddress.getLatLonPoint().getLatitude();//??????
                        double longititude = geocodeAddress.getLatLonPoint().getLongitude();//??????
                        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng
                                (latitude, longititude), 15));
                        binding.tvLocation.setText("latitude: " +  " " + latitude + "\n" +" longitude: " + " " + longititude);
                        LatLng latLng = new LatLng(latitude,longititude);
                        final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng)
                                .title(binding.editSearch.getText().toString())
                                .snippet("Marker").draggable(true));
                    }
                }
            }
        });
        GeocodeQuery geocodeQuery=new GeocodeQuery(cityName.trim(),"29");
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //???activity??????onDestroy?????????mMapView.onDestroy()???????????????
        mMapView.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        //???activity??????onResume?????????mMapView.onResume ()???????????????????????????
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //???activity??????onPause?????????mMapView.onPause ()????????????????????????
        mMapView.onPause();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //???activity??????onSaveInstanceState?????????mMapView.onSaveInstanceState (outState)??????????????????????????????
        mMapView.onSaveInstanceState(outState);
    }

}