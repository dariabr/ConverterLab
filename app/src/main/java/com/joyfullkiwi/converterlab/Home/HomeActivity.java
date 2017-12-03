package com.joyfullkiwi.converterlab.Home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.joyfullkiwi.converterlab.Models.Organization;
import com.joyfullkiwi.converterlab.R;
import com.joyfullkiwi.converterlab.databinding.OrganizationsListBinding;

import io.realm.RealmResults;

public class HomeActivity extends MvpAppCompatActivity implements HomeView {

    @InjectPresenter
    HomePresenter homePresenter;

    private HomeAdapter adapter;

    private OrganizationsListBinding bind;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this,R.layout.organizations_list);
        adapter = new HomeAdapter(this);
        bind.recyclerHome.setLayoutManager(new LinearLayoutManager(this));
        bind.recyclerHome.setAdapter(adapter);
        setSupportActionBar(bind.toolbar);

    }
    @Override
    public void onSuccessLoaded(RealmResults<Organization> organizations) {
        adapter.setOrganizations(organizations);
    }
}
