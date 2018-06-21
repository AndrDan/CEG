package com.example.lenovo.ceg.CEG;

import com.example.lenovo.ceg.Exchanges.BitfinexAPI.Bitfinex;
import com.example.lenovo.ceg.Exchanges.BitstampAPI.Bitstamp;
import com.example.lenovo.ceg.Exchanges.CEXAPI.CEX;
import com.example.lenovo.ceg.Exchanges.CoinsBankAPI.CoinsBank;
import com.example.lenovo.ceg.Exchanges.EXMOAPI.EXMO;
import com.example.lenovo.ceg.Exchanges.GDAXAPI.GDAX;
import com.example.lenovo.ceg.Exchanges.LakeBTCAPI.LakeBTC;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetInterfaceExchanges {
    @GET("/api/order_book/{symbol1}/{symbol2}/")
    Call<CEX> getCexData(@Path("symbol1") String symbol1,
                         @Path("symbol2") String symbol2,
                         @Query("depth") String depth);

    @GET("/v1/book/btcusd")
    Call<Bitfinex> getBitfinexData(@Query("limit_bids") String limitBids,
                                   @Query("limit_asks") String limitAsks,
                                   @Query("group") String group);

    @GET("/api/v2/order_book/{symbol1}/")
    Call<Bitstamp> getBitstampData(@Path("symbol1") String symbol1);

    @GET("/api/bitcoincharts/orderbook/{symbol1}")
    Call<CoinsBank> getCoinsBankData(@Path("symbol1") String symbol1);

    @GET("/v1/order_book/")
    Call<EXMO> getExmoData(@Query("pair") String pair,
                           @Query("limit") String limit);

    @GET("/products/{symbol1}/book")
    Call<GDAX> getGdaxData(@Path("symbol1") String symbol1,
                          @Query("level") String level);

    @GET("/api_v2/bcorderbook")
    Call<LakeBTC> getLakeBTCData(@Query("symbol") String symbol);
}
