package com.example.lenovo.ceg;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by student on 16.06.18.
 */

public interface GetInterfaceCEX {
    @GET("/api/order_book/{symbol1}/{symbol2}/")
    Call<CEX> getData(@Path("symbol1") String symbol1, @Path("symbol2") String symbol2, @Query("depth") String depth);
}
