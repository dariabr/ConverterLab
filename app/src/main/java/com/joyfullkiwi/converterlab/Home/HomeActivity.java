package com.joyfullkiwi.converterlab.Home;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.joyfullkiwi.converterlab.Common.BaseActivity;
import com.joyfullkiwi.converterlab.Models.Organization;
import com.joyfullkiwi.converterlab.R;
import com.joyfullkiwi.converterlab.Service.UpdateService;
import com.joyfullkiwi.converterlab.Utils.RxAndroid;
import com.joyfullkiwi.converterlab.databinding.OrganizationsListBinding;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.RealmList;
import io.realm.RealmResults;

public class HomeActivity extends BaseActivity implements HomeView {

    public static final String TAG = "TAG";
    @InjectPresenter
    HomePresenter homePresenter;

    private HomeAdapter adapter;

    private OrganizationsListBinding bind;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        stopUpdateService();
        bind = DataBindingUtil.setContentView(this,R.layout.organizations_list);
        bind.swipe.setColorSchemeColors(R.color.colorPrimary,R.color.colorAccent, R.color.colorPrimaryDark);
        adapter = new HomeAdapter(this);
        bind.recyclerHome.setLayoutManager(new LinearLayoutManager(this));
        bind.recyclerHome.setAdapter(adapter);
        setSupportActionBar(bind.toolbar);

        bind.swipe.setOnRefreshListener(() -> homePresenter.loadData());

    }


    //старт сервиса запись
    private void startUpdateService(){
        AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), UpdateService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(),AlarmManager.INTERVAL_HALF_HOUR,pendingIntent);
        Log.d(TAG,"start update servicr");

    }


    //остановка сервиса по обновлению даных в бд
    private void stopUpdateService(){
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(),UpdateService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.cancel(pendingIntent);
        Log.d(TAG,"cancel update service");
    }
    @Override
    public void onSuccessLoaded(RealmList<Organization> organizations) {
        adapter.setOrganizations(organizations);
    }



    @Override
    public void setSwipeRefreshing(boolean swipe) {
        bind.swipe.setRefreshing(swipe);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SearchView searchView = (SearchView) item.getActionView();
        unsubscribeOnDestroy(RxAndroid.search(searchView).debounce(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(result -> adapter.filter(result)));

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startUpdateService();
    }
}
