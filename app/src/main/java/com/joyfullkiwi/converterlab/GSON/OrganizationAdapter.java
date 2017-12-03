package com.joyfullkiwi.converterlab.GSON;


import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.joyfullkiwi.converterlab.Models.Organization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrganizationAdapter extends TypeAdapter<List<Organization>> {

    public static final TypeAdapter<List<Organization>> INSTANCE = new OrganizationAdapter()
            .nullSafe();

    private OrganizationAdapter() {
    }

    @Override
    public void write(JsonWriter jsonWriter, List<Organization> organizations) throws IOException {

    }

    @Override
    public List<Organization> read(JsonReader jsonReader) throws IOException {
        jsonReader.beginArray();
        List<Organization> organizations = new ArrayList<>();
        while (jsonReader.hasNext()) {
            jsonReader.beginObject();
            Organization organization = new Organization();
            organization.setId(jsonReader.nextString());
            organization.setOldId(jsonReader.nextInt());
            organization.setOrgType(jsonReader.nextInt());
            organization.setBranch(jsonReader.nextBoolean());
            organization.setTitle(jsonReader.nextString());
            organization.setRegionId(jsonReader.nextString());
            organization.setCityId(jsonReader.nextString());
            organization.setPhone(jsonReader.nextString());
            organization.setAddress(jsonReader.nextString());
            organization.setLink(jsonReader.nextString());
            organizations.add(organization);
            jsonReader.endObject();
        }
        jsonReader.endArray();
        return organizations;
    }
}
