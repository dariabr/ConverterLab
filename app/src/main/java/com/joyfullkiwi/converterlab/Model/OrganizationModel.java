package com.joyfullkiwi.converterlab.Model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrganizationModel implements Parcelable {

    private String id;
    private String title;
    private String regionId;
    private String cityId;
    private String phone;
    private String adress;
    private String link;
    private Map<String,MoneyModel> currencies = new LinkedHashMap<>();
    //currencies EUR = [value.ask,vlue.bid];

    public String getId() { return id; }

    public String getTitle() { return title; }

    public String getRegionId() { return regionId; }

    public String getCityId() { return cityId;}

    public String getPhone() { return phone;}

    public String getAdress() { return adress;}

    public String getLink() { return link; }

    public Map<String, MoneyModel> getCurrencies() {return currencies;}

    public OrganizationModel setId(String _id) {
        id = _id;
        return this;
    }

    public OrganizationModel setTitle(String _title) {
       title = _title;
        return this;
    }

    public OrganizationModel setRegionId(String _regionId) {
        regionId = _regionId;
        return this;
    }

    public OrganizationModel setCityId(String _cityId) {
        cityId = _cityId;
        return this;
    }

    public OrganizationModel setPhone(String _phone) {
        phone = _phone;
        return this;
    }

    public OrganizationModel setAdress(String _adress) {
        adress = _adress;
        return this;

    }

    public OrganizationModel setLink(String _link) {
        link = _link;
        return this;
    }

    public OrganizationModel setCurrencies(Map<String, MoneyModel> _currencies) {
        currencies = _currencies;
        return this;
    }

    @Override
    public String toString() {
        return  "id: '" + id + '\'' + "\n" +
                "title: '" + title + '\'' + "\n" +
                "regionId: '" + regionId + '\'' + "\n" +
                "cityId: '" + cityId + '\'' + "\n" +
                "phone: '" + phone + '\'' + "\n" +
                "address: '" + adress + '\'' + "\n" +
                "link: '" + link + '\'' + "\n" +
                "currencies: \n" + currencies;

        //return s;
    }

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(regionId);
        dest.writeString(cityId);
        dest.writeString(phone);
        dest.writeString(adress);
        dest.writeString(link);

        if(currencies != null){
            dest.writeInt(currencies.size());
            for (Map.Entry<String,MoneyModel> entry :currencies.entrySet()){
                dest.writeString(entry.getKey());
                dest.writeParcelable(entry.getValue(),0);
            }
        }
        else {
            dest.writeInt(-1);
        }
    }

    public static final Parcelable.Creator<OrganizationModel> CREATOR = new Parcelable.Creator<OrganizationModel>(){

        @Override
        public OrganizationModel createFromParcel(Parcel source) {
            OrganizationModel model = new OrganizationModel();
            model.setId(source.readString())
                    .setTitle(source.readString())
                    .setRegionId(source.readString())
                    .setCityId(source.readString())
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
                model.setCurrencies(map);
            }
            else { model.setCurrencies(null);}
            return model;
        }

        @Override
        public OrganizationModel[] newArray(int size) {
            return new OrganizationModel[size];
        }
    };
}
