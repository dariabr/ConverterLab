package com.joyfullkiwi.converterlab.GSON;


import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.joyfullkiwi.converterlab.Models.DateRate;

import java.io.IOException;

public class DateAdapter extends TypeAdapter<DateRate> {

    public static final TypeAdapter<DateRate> INSTANCE = new DateAdapter().nullSafe();

    private DateAdapter() {}

    @Override
    public void write(JsonWriter jsonWriter, DateRate dateRate) throws IOException {

    }

    @Override
    public DateRate read(JsonReader jsonReader) throws IOException {
        DateRate dateRate = new DateRate();
        dateRate.setDate(jsonReader.nextName());
        return dateRate;
    }
}
