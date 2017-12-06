package com.joyfullkiwi.converterlab.Common;


import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseActivity extends MvpAppCompatActivity {

    //отписка от всех потоков с помощью СompositeDisposable
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    //подписываясь на несколько потоков возвращается disposable
    public void unsubscribeOnDestroy(Disposable disposable) { compositeDisposable.add(disposable);}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();//отписка
    }

    public void toast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    public void hortToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
