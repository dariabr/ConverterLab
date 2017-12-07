package com.joyfullkiwi.converterlab.Detail;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;


import com.arellomobile.mvp.presenter.InjectPresenter;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.joyfullkiwi.converterlab.Common.BaseActivity;
import com.joyfullkiwi.converterlab.Map.MapActivity;
import com.joyfullkiwi.converterlab.Models.City;
import com.joyfullkiwi.converterlab.Models.CurrencyModel;
import com.joyfullkiwi.converterlab.Models.Organization;
import com.joyfullkiwi.converterlab.Models.Price;
import com.joyfullkiwi.converterlab.Models.Region;
import com.joyfullkiwi.converterlab.R;
import com.joyfullkiwi.converterlab.Utils.DividerItemDecoration;
import com.joyfullkiwi.converterlab.databinding.ActivityDetailBinding;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class DetailActivity extends BaseActivity implements DetailView {

    @InjectPresenter
    DetailPresenter detailPresenter;

    private ActivityDetailBinding bind;

    private CurrencyAdapter currencyAdapter;

    private ShareFragment shareFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bind = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        if (getIntent().getExtras() == null) {
            finish();
        }

        currencyAdapter = new CurrencyAdapter(this);

        bind.recyclerCurrency
                .addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        bind.recyclerCurrency.setLayoutManager(layoutManager);

        bind.recyclerCurrency.setAdapter(currencyAdapter);

        String orgId = getIntent().getExtras().getString("orgId");

        shareFragment = ShareFragment.newInstance(orgId);

        detailPresenter.loadDetailOrganization(orgId);

    }


    @Override
    public void showOrganizationInfo(Organization organization, String city, String region) {
        bind.orgInfoCard.textTitleInfoCard.setText(organization.getTitle());
        bind.orgInfoCard.textAdressInfoCard.setText(organization.getAddress());
        bind.orgInfoCard.textPhoneInfoCard.setText(organization.getPhone());
        bind.orgInfoCard.textAddInfoCard.setText(organization.getLink());
        initToolbar(organization.getTitle(), city);

        bind.btns.fabMap.setOnClickListener(view -> {
            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra("orgId", organization.getId());
            startActivity(intent);
        });

        bind.btns.fabLink.setOnClickListener(view -> {
            CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                    .setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left)
                    .setExitAnimations(this, R.anim.slide_in_left, R.anim.slide_out_right)
                    .setToolbarColor(getResources().getColor(R.color.colorPrimary))
                    .build();
            Uri url = Uri.parse(organization.getLink());
            customTabsIntent.launchUrl(this, url);
        });

        bind.btns.fabPhone.setOnClickListener(view -> {
            String phone = organization.getPhone();
            TedPermission.with(this).setPermissionListener(new PermissionListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onPermissionGranted() {
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone)));
                }

                @Override
                public void onPermissionDenied(ArrayList<String> arrayList) {
                }
            })
                    .setRationaleConfirmText(getResources().getString(R.string.confirm))
                    .setDeniedCloseButtonText(getResources().getString(R.string.cancel))
                    .setGotoSettingButtonText(getResources().getString(R.string.settings))
                    .setDeniedMessage(getResources().getString(R.string.msg_denied))
                    .setPermissions(Manifest.permission.CALL_PHONE)
                    .check();
        });
    }

    @Override
    public void showCurrencies(List<CurrencyModel> modelList) {
        currencyAdapter.setCurrencies(modelList);
    }

    @Override
    public void initShareButton(String title, String region, String city,
                                List<CurrencyModel> modelList) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_share) {
            shareFragment.show(getSupportFragmentManager(), "shareFragment");
        }
        return true;
    }

    private void initToolbar(String title, String city) {
        bind.toolbar.setTitle(null);
        bind.toolbarTitleOrgCard.setText(title);
        bind.toolbarCityTitleOrgCard.setText(city);
        setSupportActionBar(bind.toolbar);
    }

}
