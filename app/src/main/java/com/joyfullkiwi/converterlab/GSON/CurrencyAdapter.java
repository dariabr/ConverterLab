package com.joyfullkiwi.converterlab.GSON;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.joyfullkiwi.converterlab.Models.Currency;
import com.joyfullkiwi.converterlab.Models.Price;

import java.io.IOException;
import java.util.List;

import io.realm.RealmList;

public class CurrencyAdapter extends TypeAdapter<RealmList<Currency>> {

    public static final TypeAdapter<RealmList<Currency>> INSTANCE = new CurrencyAdapter().nullSafe();

    private CurrencyAdapter() {
    }

    @Override
    public void write(JsonWriter jsonWriter, RealmList<Currency> currencies) throws IOException {

    }

    @Override
    public RealmList<Currency> read(JsonReader jsonReader) throws IOException {
        RealmList<Currency> currencies = new RealmList<>();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            Currency currency = new Currency();
            Price price = new Price();
            currency.setCurrencyName(jsonReader.nextName());
            jsonReader.beginObject();
            jsonReader.nextName();
            price.setAsk(Double.valueOf(jsonReader.nextString()));
            jsonReader.nextName();
            price.setBid(Double.valueOf(jsonReader.nextString()));
            jsonReader.endObject();
            currency.setPrice(price);
            currencies.add(currency);

        }
        jsonReader.endObject();
        return currencies;
    }
}
