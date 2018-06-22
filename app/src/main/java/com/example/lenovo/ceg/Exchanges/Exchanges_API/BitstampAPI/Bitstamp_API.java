package com.example.lenovo.ceg.Exchanges.Exchanges_API.BitstampAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Bitstamp_API {

    @SerializedName("high")
    @Expose
    private String high;
    @SerializedName("last")
    @Expose
    private String last;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("bid")
    @Expose
    private List<List<Float>> bid;
    @SerializedName("vwap")
    @Expose
    private String vwap;
    @SerializedName("volume")
    @Expose
    private String volume;
    @SerializedName("low")
    @Expose
    private String low;
    @SerializedName("ask")
    @Expose
    private List<List<Float>> ask;
    @SerializedName("open")
    @Expose
    private String open;

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public Float getLast() {

        Float lastw  = Float.valueOf(last);
        return lastw;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public Float getTimestamp() {
        Float timew  = Float.valueOf(timestamp);
        return timew;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<List<Float>> getBids() {
        return bid;
    }

    public void setBids(List<List<Float>> bids) {
        this.bid = bids;
    }

    public String getVwap() {
        return vwap;
    }

    public void setVwap(String vwap) {
        this.vwap = vwap;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }


    public List<List<Float>> getAsks() {
        return ask;
    }

    public void setAsks(List<List<Float>> asks) {
        this.ask = asks;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

}
