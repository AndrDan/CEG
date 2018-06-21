package com.example.lenovo.ceg.CEX;

import com.example.lenovo.ceg.Exchanges.CEXAPI.CEX;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetInterfaceCEX {
    @GET("/api/order_book/{symbol1}/{symbol2}/")
    Call<CEX> getData(@Path("symbol1") String symbol1, @Path("symbol2") String symbol2, @Query("depth") String depth);
}
