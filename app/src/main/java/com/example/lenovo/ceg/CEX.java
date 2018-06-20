package com.example.lenovo.ceg;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CEX {

    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;
    @SerializedName("bids")
    @Expose
    private List<List<Float>> bids = null;
    @SerializedName("asks")
    @Expose
    private List<List<Float>> asks = null;
    @SerializedName("pair")
    @Expose
    private String pair;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sell_total")
    @Expose
    private String sellTotal;
    @SerializedName("buy_total")
    @Expose
    private String buyTotal;

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
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

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSellTotal() {
        return sellTotal;
    }

    public void setSellTotal(String sellTotal) {
        this.sellTotal = sellTotal;
    }

    public String getBuyTotal() {
        return buyTotal;
    }

    public void setBuyTotal(String buyTotal) {
        this.buyTotal = buyTotal;
    }

}