package com.example.lenovo.ceg.CEG;

import android.util.Log;

import com.example.lenovo.ceg.Exchanges.Exchanges_API.BitstampAPI.Bitstamp_API;
import com.example.lenovo.ceg.Exchanges.Exchanges_API.CEXAPI.CEX_API;
import com.example.lenovo.ceg.Exchanges.Exchanges_API.CoinsBankAPI.CoinsBank_API;
import com.example.lenovo.ceg.Exchanges.Exchanges_API.EXMOAPI.EXMO_API;
import com.example.lenovo.ceg.Exchanges.Exchanges_API.GDAXAPI.GDAX_API;
import com.example.lenovo.ceg.Exchanges.Exchanges_API.LakeBTCAPI.LakeBTC_API;
import com.example.lenovo.ceg.Exchanges.GetInterfaceExchanges;

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

    private CEX_API getCexResponse() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cex.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetInterfaceExchanges getInterface = retrofit.create(GetInterfaceExchanges.class);

        String symbol1="BTC";
        String symbol2="USD";
        String depth="10";
        Call<CEX_API> responseCall = getInterface.getCexData(symbol1, symbol2, depth);
        Response<CEX_API> res;
        CEX_API cexAPIResponse = null;
        try {
            res = responseCall.execute();
            cexAPIResponse = res.body();
        } catch (IOException e) {
            Log.e("getCexResponse", e.toString());
        }

        return cexAPIResponse;
    }

    private Bitstamp_API getBitstampResponse() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.bitstamp.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetInterfaceExchanges getInterface = retrofit.create(GetInterfaceExchanges.class);

        String symbol1="btcusd";
        Call<Bitstamp_API> responseCall = getInterface.getBitstampData(symbol1);
        Response<Bitstamp_API> res;
        Bitstamp_API bitstampResponse = null;
        try {
            res = responseCall.execute();
            bitstampResponse = res.body();
        } catch (IOException e) {
            Log.e("getBitstampResponse", e.toString());
        }

        return bitstampResponse;
    }

    private CoinsBank_API getCoinsBankResponse() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://coinsbank.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetInterfaceExchanges getInterface = retrofit.create(GetInterfaceExchanges.class);

        String symbol1="BTCUSD";
        Call<CoinsBank_API> responseCall = getInterface.getCoinsBankData(symbol1);
        Response<CoinsBank_API> res;
        CoinsBank_API coinsBankResponse = null;
        try {
            res = responseCall.execute();
            coinsBankResponse  = res.body();
        } catch (IOException e) {
            Log.e("getCoinsBankResponse ", e.toString());
        }

        return coinsBankResponse ;
    }

    private EXMO_API getExmoResponse() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.exmo.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetInterfaceExchanges getInterface = retrofit.create(GetInterfaceExchanges.class);

        String pair = "BTC_USD";
        String limit = "10";
        Call<EXMO_API> responseCall = getInterface.getExmoData(pair,limit);
        Response<EXMO_API> res;
        EXMO_API exmoAPIResponse = null;
        try {
            res = responseCall.execute();
            exmoAPIResponse = res.body();
        } catch (IOException e) {
            Log.e("getExmoResponse ", e.toString());
        }

        return exmoAPIResponse;
    }

    private GDAX_API getGdaxResponse() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.gdax.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetInterfaceExchanges getInterface = retrofit.create(GetInterfaceExchanges.class);

        String symbol1 = "BTC-USD";
        String level = "2";
        Call<GDAX_API> responseCall = getInterface.getGdaxData(symbol1, level);
        Response<GDAX_API> res;
        GDAX_API gdaxAPIResponse = null;
        try {
            res = responseCall.execute();
            gdaxAPIResponse = res.body();
        } catch (IOException e) {
            Log.e("getExmoResponse ", e.toString());
        }

        return gdaxAPIResponse;
    }

    private LakeBTC_API getLakeBTCResponse() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.lakebtc.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetInterfaceExchanges getInterface = retrofit.create(GetInterfaceExchanges.class);

        String symbol = "btcusd";
        Call<LakeBTC_API> responseCall = getInterface.getLakeBTCData(symbol);
        Response<LakeBTC_API> res;
        LakeBTC_API lakeBTCAPIResponse = null;
        try {
            res = responseCall.execute();
            lakeBTCAPIResponse = res.body();
        } catch (IOException e) {
            Log.e("getExmoResponse ", e.toString());
        }

        return lakeBTCAPIResponse;
    }

    public List<List<Float>> getTest1() {
        CEX_API cexAPI = getCexResponse();
        Bitstamp_API bitstamp = getBitstampResponse();
        CoinsBank_API coinsBank = getCoinsBankResponse();
        EXMO_API exmoAPI = getExmoResponse();
        GDAX_API gdaxAPI = getGdaxResponse();
        LakeBTC_API lakeBTCAPI = getLakeBTCResponse();
        addAllIfNotNull(test1, lakeBTCAPI.getBids());
        return test1;
    }

    public List<List<Float>> getBids() {
        CEX_API cexAPI = getCexResponse();
        Bitstamp_API bitstamp = getBitstampResponse();
        CoinsBank_API coinsBank = getCoinsBankResponse();
        EXMO_API exmoAPI = getExmoResponse();
        GDAX_API gdaxAPI = getGdaxResponse();
        //LakeBTC_API lakeBTCAPI = getLakeBTCResponse();
        addAllIfNotNull(bids, cexAPI.getBids());
        addAllIfNotNull(bids, bitstamp.getBids());
        addAllIfNotNull(bids, coinsBank.getBids());
        addAllIfNotNull(bids, exmoAPI.getBTCUSD().getBid());
        addAllIfNotNull(bids, gdaxAPI.getBids());
        //addAllIfNotNull(bids, lakeBTCAPI.getBids());

        Collections.sort(bids, new ListFloatReverseComparator());
        bids.subList(100, bids.size()).clear();
        return bids;
    }

    public List<List<Float>> getAsks() {
        CEX_API cexAPI = getCexResponse();
        Bitstamp_API bitstamp = getBitstampResponse();
        CoinsBank_API coinsBank = getCoinsBankResponse();
        EXMO_API exmoAPI = getExmoResponse();
        GDAX_API gdaxAPI = getGdaxResponse();
        //LakeBTC_API lakeBTCAPI = getLakeBTCResponse();
        addAllIfNotNull(asks, cexAPI.getAsks());
        addAllIfNotNull(asks, bitstamp.getAsks());
        addAllIfNotNull(asks, coinsBank.getAsks());
        addAllIfNotNull(asks, exmoAPI.getBTCUSD().getAsk());
        addAllIfNotNull(asks, gdaxAPI.getAsks());
        //addAllIfNotNull(asks, lakeBTCAPI.getAsks());

        Collections.sort(asks, new ListFloatComparator());
        asks.subList(100, asks.size()).clear();
        return asks;
    }
}
