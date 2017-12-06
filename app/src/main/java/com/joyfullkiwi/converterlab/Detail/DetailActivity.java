package com.joyfullkiwi.converterlab.Detail;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


import com.arellomobile.mvp.presenter.InjectPresenter;
import com.joyfullkiwi.converterlab.Common.BaseActivity;
import com.joyfullkiwi.converterlab.Models.City;
import com.joyfullkiwi.converterlab.Models.Organization;
import com.joyfullkiwi.converterlab.Models.Price;
import com.joyfullkiwi.converterlab.Models.Region;
import com.joyfullkiwi.converterlab.R;
import com.joyfullkiwi.converterlab.databinding.ActivityDetailBinding;

import io.realm.Realm;
import io.realm.RealmList;

public class DetailActivity extends BaseActivity implements DetailView {

    @InjectPresenter
    DetailPresenter detailPresenter;

    ActivityDetailBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        if(getIntent().getExtras() == null){
            finish();
        }

        String orgId = getIntent().getExtras().getString("orgId");

        detailPresenter.loadDetailOrganization(orgId);

    }

    @Override
    public void showOrganizationInfo(Organization organization, String city, String regoin) {
        initToolbar(organization.getTitle(),city);
    }

    @Override
    public void showCurrency(String currencyName, RealmList<Price> prices) {
        System.out.println(currencyName+"  " + prices);
    }

    /*private void initData(){
            if(getIntent().getExtras()== null){ finish();}

            String orgId = getIntent().getExtras().getString("orgId");
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();


            Organization organization = realm.where(Organization.class).equalTo("id",orgId).findFirst();

            String region = realm.where(Region.class).equalTo("id",organization.getRegionId()).findFirst().getName();

            String city = realm.where(City.class).equalTo("id",organization.getCityId()).findFirst().getName();
            initToolbar(organization.getTitle(),city);
            realm.commitTransaction();

        }




    */
    private void initToolbar(String title, String city){
        binding.toolbar.setTitle(null);
        binding.toolbarCityTitleOrgCard.setText(title);
        binding.toolbarCityTitleOrgCard.setText(city);
        setSupportActionBar(binding.toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_detail,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
