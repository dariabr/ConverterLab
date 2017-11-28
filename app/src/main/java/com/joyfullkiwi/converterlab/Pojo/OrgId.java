package com.joyfullkiwi.converterlab.Pojo;
import com.google.gson.annotations.SerializedName;

public class OrgId {

    @SerializedName("id")
    private String orgId;

    public OrgId(String _orgId) {orgId = _orgId;}
}
