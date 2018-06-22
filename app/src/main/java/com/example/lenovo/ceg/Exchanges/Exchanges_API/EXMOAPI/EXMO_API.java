
package com.example.lenovo.ceg.Exchanges.Exchanges_API.EXMOAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EXMO_API {

    @SerializedName("BTC_USD")
    @Expose
    private BTCUSD bTCUSD;

    public BTCUSD getBTCUSD() {
        return bTCUSD;
    }

    public void setBTCUSD(BTCUSD bTCUSD) {
        this.bTCUSD = bTCUSD;
    }

}
