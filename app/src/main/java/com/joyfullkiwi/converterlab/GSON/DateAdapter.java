package com.joyfullkiwi.converterlab.GSON;


import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.joyfullkiwi.converterlab.Models.DateRate;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateAdapter extends TypeAdapter<DateRate> {

    public static final TypeAdapter<DateRate> INSTANCE = new DateAdapter().nullSafe();

    private DateAdapter() {}

    @Override
    public void write(JsonWriter jsonWriter, DateRate dateRate) throws IOException {

    }

    @Override
    public DateRate read(JsonReader jsonReader) throws IOException {
        DateRate dateRate = new DateRate();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss+02:00", Locale.getDefault());
        try {
            Date serverDate = format.parse(jsonReader.nextString());
            dateRate.setTime(serverDate.getTime());
        } catch (ParseException e){
            e.printStackTrace();
        }
        return dateRate;
    }
}
