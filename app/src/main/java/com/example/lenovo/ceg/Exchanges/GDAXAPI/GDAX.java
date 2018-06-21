package com.example.lenovo.ceg.Exchanges.GDAXAPI;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GDAX {

    @SerializedName("sequence")
    @Expose
    private String sequence;
    @SerializedName("bids")
    @Expose
    private List<List<Float>> bids = null;
    @SerializedName("asks")
    @Expose
    private List<List<Float>> asks = null;

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public List<List<Float>> getBids() {
        return bids;
    }

    public void setBids(List<List<Float>> bids) {
        this.bids = bids;
    }

    public List<List<Float>> getAsks() {
        return asks;
    }

    public void setAsks(List<List<Float>> asks) {
        this.asks = asks;
    }

}