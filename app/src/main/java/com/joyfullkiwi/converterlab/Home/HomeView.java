package com.joyfullkiwi.converterlab.Home;


import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.joyfullkiwi.converterlab.Models.Organization;


import io.realm.RealmList;
import io.realm.RealmResults;

@StateStrategyType(SingleStateStrategy.class)
public interface HomeView extends MvpView{

    void onSuccessLoaded(RealmList<Organization> organizations);

    void setSwipeRefreshing(boolean swipe);
}
