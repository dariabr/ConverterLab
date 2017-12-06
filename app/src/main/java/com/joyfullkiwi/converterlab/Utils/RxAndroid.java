package com.joyfullkiwi.converterlab.Utils;


import android.support.v7.widget.SearchView;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RxAndroid {

    public static <E extends RealmObject> Observable<E> getRealmObject(Class<E> eClass){
        return Observable.fromCallable(()-> {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            E first = realm.where(eClass).findFirst();
            realm.commitTransaction();
            return first;
        });
    }

    public static <E extends RealmObject> Observable<RealmList<E>> getRealmList(Class<E> eClass){
        return Observable.fromCallable(()->{
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RealmResults<E> results = realm.where(eClass).findAll();
            RealmList<E> realmList = new RealmList<>();
            realmList.addAll(results.subList(0, results.size()));
            realm.commitTransaction();
            return realmList;
        });
    }

    public static Observable<String> search(SearchView searchView){
        return Observable.create(sub -> searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                sub.onNext(newText.toLowerCase());
                return false;
            }
        }));
    }
}
