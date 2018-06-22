package com.example.lenovo.ceg.Exchanges;

import com.example.lenovo.ceg.Exchanges.Exchanges_API.BitfinexAPI.Bitfinex_API;
import com.example.lenovo.ceg.Exchanges.Exchanges_API.BitstampAPI.Bitstamp_API;
import com.example.lenovo.ceg.Exchanges.Exchanges_API.CEXAPI.CEX_API;
import com.example.lenovo.ceg.Exchanges.Exchanges_API.CoinsBankAPI.CoinsBank_API;
import com.example.lenovo.ceg.Exchanges.Exchanges_API.EXMOAPI.EXMO_API;
import com.example.lenovo.ceg.Exchanges.Exchanges_API.GDAXAPI.GDAX_API;
import com.example.lenovo.ceg.Exchanges.Exchanges_API.LakeBTCAPI.LakeBTC_API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetInterfaceExchanges {
    @GET("/api/order_book/{symbol1}/{symbol2}/")
    Call<CEX_API> getCexData(@Path("symbol1") String symbol1,
                             @Path("symbol2") String symbol2,
                             @Query("depth") String depth);

    @GET("/v1/book/btcusd")
    Call<Bitfinex_API> getBitfinexData(@Query("limit_bids") String limitBids,
                                       @Query("limit_asks") String limitAsks,
                                       @Query("group") String group);

    @GET("/api/v2/order_book/{symbol1}/")
    Call<Bitstamp_API> getBitstampData(@Path("symbol1") String symbol1);

    @GET("/api/bitcoincharts/orderbook/{symbol1}")
    Call<CoinsBank_API> getCoinsBankData(@Path("symbol1") String symbol1);

    @GET("/v1/order_book/")
    Call<EXMO_API> getExmoData(@Query("pair") String pair,
                               @Query("limit") String limit);

    @GET("/products/{symbol1}/book")
    Call<GDAX_API> getGdaxData(@Path("symbol1") String symbol1,
                               @Query("level") String level);

    @GET("/api_v2/bcorderbook")
    Call<LakeBTC_API> getLakeBTCData(@Query("symbol") String symbol);
}
