package com.joyfullkiwi.converterlab.Model;


import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.joyfullkiwi.converterlab.Utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class InfoModel implements Parcelable {

    private String id;
    private String title;
    private String regionTitle;
    private String cityTitle;
    private String phone;
    private String adress;
    private String link;
    private Map<String, MoneyModel> currencies = null;


    public String getId() { return id; }

    public String getTitle() { return (TextUtils.isEmpty(title))? Constants.UNKNOWN_VALUE : title; }

    public String getRegionTitle() { return (TextUtils.isEmpty(regionTitle))? Constants.UNKNOWN_VALUE : regionTitle; }

    public String getCityTitle() { return  (TextUtils.isEmpty(cityTitle))? Constants.UNKNOWN_VALUE : cityTitle; }

    public String getPhone() { return (TextUtils.isEmpty(phone))? Constants.UNKNOWN_VALUE : phone; }

    public String getAdress() { return (TextUtils.isEmpty(adress))? Constants.UNKNOWN_VALUE : adress; }

    public String getLink() { return link; }

    public Map<String, MoneyModel> getCurrencies() {return currencies;}

    public InfoModel setId(String _id) {
        id = _id;
        return this;
    }

    public InfoModel setTitle(String _title) {
        title = _title;
        return this;
    }

    public InfoModel setRegionTitle(String _regionTitle) {
        regionTitle = _regionTitle;
        return this;
    }

    public InfoModel setCityTitle(String _cityTitle) {
        cityTitle = _cityTitle;
        return this;
    }

    public InfoModel setPhone(String _phone) {
       phone = _phone;
        return this;
    }

    public InfoModel setAdress(String _adress) {
        this.adress = _adress;
        return this;
    }

    public InfoModel setLink(String link) {
        this.link = link;
        return this;
    }

    public InfoModel setCurrencies(Map<String, MoneyModel> _currencies) {
       currencies = _currencies;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(getId());
        dest.writeValue(getTitle());
        dest.writeValue(getRegionTitle());
        dest.writeValue(getCityTitle());
        dest.writeValue(getPhone());
        dest.writeValue(getAdress());
        dest.writeValue(getLink());
    }

    @Override
    public String toString() {
        return  "id: "          + getId() + "\n" +
                "title: "       + getTitle()  + "\n" +
                "regionTitle: " + getRegionTitle()+ "\n" +
                "cityTitle: "   + getCityTitle()+ "\n" +
                "phone: "       + getPhone()+ "\n" +
                "address: "     + getAdress()+ "\n" +
                "link: "        + getLink();
    }

    public static final Parcelable.Creator<InfoModel> CREATOR = new Parcelable.Creator<InfoModel>() {
        @Override
        public InfoModel createFromParcel(Parcel source) {
            InfoModel model = new InfoModel();
            model.setId(source.readString())
                    .setTitle(source.readString())
                    .setRegionTitle(source.readString())
                    .setCityTitle(source.readString())
                    .setPhone(source.readString())
                    .setAdress(source.readString())
                    .setLink(source.readString());

            int mapSize = source.readInt();
            if(mapSize > -1) {
                Map<String,MoneyModel> map = new HashMap<>(mapSize);
                for (int i =0; i < mapSize; i++){
                    String key = source.readString();
                    MoneyModel value =source.readParcelable(MoneyModel.class.getClassLoader());
                    map.put(key,value);
                }
            }
            return model;
        }

        @Override
        public InfoModel[] newArray(int size) {
            return new InfoModel[size];
        }
    };
}
