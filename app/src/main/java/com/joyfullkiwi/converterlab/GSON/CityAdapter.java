package com.joyfullkiwi.converterlab.GSON;


import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.joyfullkiwi.converterlab.Models.City;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends TypeAdapter<List<City>> {

    public static final TypeAdapter<List<City>> INSTANCE = new CityAdapter().nullSafe();

    private CityAdapter(){}

    @Override
    public void write(JsonWriter jsonWriter, List<City> cities) throws IOException {
    }

    @Override
    public List<City> read(JsonReader jsonReader) throws IOException {
        List<City> cities = new ArrayList<>();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            cities.add(new City(jsonReader.nextName(), jsonReader.nextString()));
        }
        jsonReader.endObject();

        return cities;
    }

}
