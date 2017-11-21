package com.joyfullkiwi.converterlab.Model;

import android.os.Parcel;
import android.os.Parcelable;


public class MoneyModel implements Parcelable {
    public String ask;
    public String bid;

    public MoneyModel(String _ask, String _bid){
        ask = _ask;
        bid = _bid;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(ask);
        dest.writeValue(bid);

    }

    public static final Parcelable.Creator<MoneyModel> CREATOR = new Creator<MoneyModel>() {
        @Override
        public MoneyModel createFromParcel(Parcel source) {
            String ask = source.readString();
            String bid = source.readString();

            return new MoneyModel(ask,bid);
        }

        @Override
        public MoneyModel[] newArray(int size) {
            return new MoneyModel[size];
        }
    };
}
