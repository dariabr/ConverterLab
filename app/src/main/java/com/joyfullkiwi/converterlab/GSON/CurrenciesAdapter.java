package com.joyfullkiwi.converterlab.GSON;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.joyfullkiwi.converterlab.Models.Currencies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CurrenciesAdapter extends TypeAdapter<List<Currencies>> {

    public static final TypeAdapter<List<Currencies>> INSTANCE = new CurrenciesAdapter().nullSafe();

    private CurrenciesAdapter() {
    }

    @Override
    public void write(JsonWriter jsonWriter, List<Currencies> currencies) throws IOException {

    }

    @Override
    public List<Currencies> read(JsonReader jsonReader) throws IOException {
        List<Currencies> currencies = new ArrayList<>();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            currencies.add(new Currencies(jsonReader.nextName(), jsonReader.nextString()));
        }
        jsonReader.endObject();

        return currencies;
    }
}
