package com.joyfullkiwi.converterlab.Service;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.joyfullkiwi.converterlab.Home.HomeInteractor;

import static android.content.ContentValues.TAG;

public class UpdateService extends BroadcastReceiver{

    private HomeInteractor interactor = HomeInteractor.init();

    @Override
    public void onReceive(Context context, Intent intent) {
        interactor.getAndWriteData().subscribe(information -> {},trowable -> Log.d(TAG,"error update data - disable network"));
    }
}
