package com.example.lenovo.ceg.CEG;

import android.util.Log;

import com.example.lenovo.ceg.CEX.GetInterfaceCEX;
import com.example.lenovo.ceg.Exchanges.BitstampAPI.Bitstamp;
import com.example.lenovo.ceg.Exchanges.CEXAPI.CEX;
import com.example.lenovo.ceg.Exchanges.CoinsBankAPI.CoinsBank;
import com.example.lenovo.ceg.Exchanges.EXMOAPI.EXMO;
import com.example.lenovo.ceg.Exchanges.GDAXAPI.GDAX;
import com.example.lenovo.ceg.Exchanges.LakeBTCAPI.LakeBTC;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainOrderBook {
    private List<List<Float>> asks = new ArrayList<>();
    private List<List<Float>> bids = new ArrayList<>();

    private List<List<Float>> test1 = new ArrayList<>();

    //public MainOrderBook(){}

    public <E> void addAllIfNotNull(List<E> list, Collection<? extends E> c) {
        if (c != null) {
            list.addAll(c);
        }
    }

    private class ListFloatComparator implements Comparator<List<Float>> {
        @Override
        public int compare(List<Float> o1, List<Float> o2) {
            return o1.get(0).compareTo(o2.get(0));
        }
    }

    private class ListFloatReverseComparator implements Comparator<List<Float>> {
        @Override
        public int compare(List<Float> o1, List<Float> o2) {
            return (-1) * o1.get(0).compareTo(o2.get(0));
        }
    }

    private CEX getCexResponse() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cex.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetInterfaceExchanges getInterface = retrofit.create(GetInterfaceExchanges.class);

        String symbol1="BTC";
        String symbol2="USD";
        String depth="10";
        Call<CEX> responseCall = getInterface.getCexData(symbol1, symbol2, depth);
        Response<CEX> res;
        CEX cexResponse = null;
        try {
            res = responseCall.execute();
            cexResponse = res.body();
        } catch (IOException e) {
            Log.e("getCexResponse", e.toString());
        }

        return cexResponse;
    }

    /*private Bitstamp getBitstampResponse() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.bitstamp.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetInterfaceExchanges getInterface = retrofit.create(GetInterfaceExchanges.class);

        String symbol1="btcusd";
        Call<Bitstamp> responseCall = getInterface.getBitstampData(symbol1);
        Response<Bitstamp> res;
        Bitstamp bitstampResponse = null;
        try {
            res = responseCall.execute();
            bitstampResponse = res.body();
        } catch (IOException e) {
            Log.e("getBitstampResponse", e.toString());
        }

        return bitstampResponse;
    }*/

    private CoinsBank getCoinsBankResponse() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://coinsbank.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetInterfaceExchanges getInterface = retrofit.create(GetInterfaceExchanges.class);

        String symbol1="BTCUSD";
        Call<CoinsBank> responseCall = getInterface.getCoinsBankData(symbol1);
        Response<CoinsBank> res;
        CoinsBank coinsBankResponse = null;
        try {
            res = responseCall.execute();
            coinsBankResponse  = res.body();
        } catch (IOException e) {
            Log.e("getCoinsBankResponse ", e.toString());
        }

        return coinsBankResponse ;
    }

    private EXMO getExmoResponse() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.exmo.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetInterfaceExchanges getInterface = retrofit.create(GetInterfaceExchanges.class);

        String pair = "BTC_USD";
        String limit = "10";
        Call<EXMO> responseCall = getInterface.getExmoData(pair,limit);
        Response<EXMO> res;
        EXMO exmoResponse = null;
        try {
            res = responseCall.execute();
            exmoResponse = res.body();
        } catch (IOException e) {
            Log.e("getExmoResponse ", e.toString());
        }

        return exmoResponse ;
    }

    private GDAX getGdaxResponse() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.gdax.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetInterfaceExchanges getInterface = retrofit.create(GetInterfaceExchanges.class);

        String symbol1 = "BTC-USD";
        String level = "2";
        Call<GDAX> responseCall = getInterface.getGdaxData(symbol1, level);
        Response<GDAX> res;
        GDAX gdaxResponse = null;
        try {
            res = responseCall.execute();
            gdaxResponse = res.body();
        } catch (IOException e) {
            Log.e("getExmoResponse ", e.toString());
        }

        return gdaxResponse ;
    }

    private LakeBTC getLakeBTCResponse() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.lakebtc.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetInterfaceExchanges getInterface = retrofit.create(GetInterfaceExchanges.class);

        String symbol = "btcusd";
        Call<LakeBTC> responseCall = getInterface.getLakeBTCData(symbol);
        Response<LakeBTC> res;
        LakeBTC lakeBTCResponse = null;
        try {
            res = responseCall.execute();
            lakeBTCResponse = res.body();
        } catch (IOException e) {
            Log.e("getExmoResponse ", e.toString());
        }

        return lakeBTCResponse;
    }

    public List<List<Float>> getTest1() {
        CEX cex = getCexResponse();
        //Bitstamp bitstamp = getBitstampResponse();
        CoinsBank coinsBank = getCoinsBankResponse();
        EXMO exmo = getExmoResponse();
        GDAX gdax = getGdaxResponse();
        LakeBTC lakeBTC = getLakeBTCResponse();
        addAllIfNotNull(test1, lakeBTC.getBids());
        return test1;
    }

    public List<List<Float>> getBids() {
        CEX cex = getCexResponse();
        //Bitstamp bitstamp = getBitstampResponse();
        CoinsBank coinsBank = getCoinsBankResponse();
        EXMO exmo = getExmoResponse();
        GDAX gdax = getGdaxResponse();
        LakeBTC lakeBTC = getLakeBTCResponse();
        addAllIfNotNull(bids, cex.getBids());
        //addAllIfNotNull(bids, bitstamp.getBids());
        addAllIfNotNull(bids, coinsBank.getBids());
        addAllIfNotNull(bids, exmo.getBTCUSD().getBid());
        addAllIfNotNull(bids, gdax.getBids());
        addAllIfNotNull(bids, lakeBTC.getBids());

        Collections.sort(bids, new ListFloatReverseComparator());
        bids.subList(100, bids.size()).clear();
        return bids;
    }
    public List<List<Float>> getAsks() {
        CEX cex = getCexResponse();
        //Bitstamp bitstamp = getBitstampResponse();
        CoinsBank coinsBank = getCoinsBankResponse();
        EXMO exmo = getExmoResponse();
        GDAX gdax = getGdaxResponse();
        LakeBTC lakeBTC = getLakeBTCResponse();
        addAllIfNotNull(asks, cex.getAsks());
        //addAllIfNotNull(asks, bitstamp.getAsks());
        addAllIfNotNull(asks, coinsBank.getAsks());
        addAllIfNotNull(asks, exmo.getBTCUSD().getAsk());
        addAllIfNotNull(asks, gdax.getAsks());
        addAllIfNotNull(asks, lakeBTC.getAsks());

        Collections.sort(asks, new ListFloatComparator());
        asks.subList(100, asks.size()).clear();
        return asks;
    }
}
