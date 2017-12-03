package com.joyfullkiwi.converterlab.GSON;


import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.joyfullkiwi.converterlab.Models.Region;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegionAdapter extends TypeAdapter<List<Region>> {

    public static final TypeAdapter<List<Region>> INSTANCE = new RegionAdapter().nullSafe();

    private RegionAdapter() {
    }

    @Override
    public void write(JsonWriter jsonWriter, List<Region> regions) throws IOException {

    }

    @Override
    public List<Region> read(JsonReader jsonReader) throws IOException {
        List<Region> regions = new ArrayList<>();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            regions.add(new Region(jsonReader.nextName(), jsonReader.nextString()));
        }
        jsonReader.endObject();

        return regions;
    }
}
