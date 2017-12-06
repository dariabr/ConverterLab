package com.joyfullkiwi.converterlab.Map;


import android.app.Fragment;

import android.Manifest.permission;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.joyfullkiwi.converterlab.Models.City;
import com.joyfullkiwi.converterlab.Models.Organization;
import com.joyfullkiwi.converterlab.Models.Region;
import com.joyfullkiwi.converterlab.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;



public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String orgId;
    private Organization organization;
    private String region;
    private String city;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        TedPermission.with(this).setPermissionListener(new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                initMap();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> arrayList) {
                finish();
            }
        }).setRationaleMessage(getString(R.string.rationale_msg_map))
                .setRationaleConfirmText(getString(R.string.confirm))
                .setDeniedCloseButtonText(getString(R.string.cancel))
                .setGotoSettingButtonText(getString(R.string.settings))
                .setDeniedMessage(getString(R.string.msg_denied))
                .setPermissions(permission.ACCESS_FINE_LOCATION)
                .check();

    }


    private void initMap(){

        initData();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initData(){
        if(getIntent().getExtras() == null){
            finish();
        }

        orgId = getIntent().getExtras().getString("orgId");

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        organization = realm.where(Organization.class).equalTo("id",orgId).findFirst();

        region = realm.where(Region.class).equalTo("id", organization.getRegionId()).findFirst().getName();

        city = realm.where(City.class).equalTo("id",organization.getCityId()).findFirst().getName();

        realm.commitTransaction();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);//зум вкл

        Geocoder geocoder = new Geocoder(this);//преобр улицы в кооординаты

        List<Address> addresses = new ArrayList<>();
        try {
            //обпработоный адресс в коллекцию адресов
            addresses = geocoder.getFromLocationName(String.format("%s, %s, %s",city,region,organization.getAddress()),5);
        } catch (IOException i) {}

        if(addresses.size() !=0){
            Address address = addresses.get(0);//получаем обработаныей адрес
            LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());//стпвим на карту маркер с инфой про банк
            mMap.addMarker(new MarkerOptions().position(latLng).title(organization.getTitle())
                    .snippet(organization.getAddress()+",tel:"+organization.getPhone()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));

        }


    }
}
