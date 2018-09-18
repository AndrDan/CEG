package com.example.lenovo.ceg.Exchanges.Exchanges_Activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.lenovo.ceg.Exchanges.Exchanges_API.EXMOAPI.EXMO_API;
import com.example.lenovo.ceg.Exchanges.Exchanges_API.GDAXAPI.GDAX_API;
import com.example.lenovo.ceg.Exchanges.GetInterfaceExchanges;
import com.example.lenovo.ceg.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GDAXActivity extends Activity {

    GetInterfaceExchanges getInterface;
    public GdaxAT gdaxAT = new GdaxAT();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gdax_activity);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.gdax.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getInterface = retrofit.create(GetInterfaceExchanges.class);
        gdaxAT.execute();
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(gdaxAT == null) {
            gdaxAT.execute();
        }
    }

    class GdaxAT extends AsyncTask<Void, Response<GDAX_API>, Response<GDAX_API>> {

        @Override
        protected Response<GDAX_API> doInBackground(Void... voids) {
            Response<GDAX_API> res = null;
            while (!isCancelled()) {
                try {
                    String symbol1 = "BTC-USD";
                    String level = "2";
                    Call<GDAX_API> responseCall = getInterface.getGdaxData(symbol1, level);
                    res = responseCall.execute();
                    publishProgress(res);
                    Thread.sleep(3000);
                } catch (IOException | InterruptedException e) {
                    Log.e("ERROR", e.toString());
                }
            }
            return res;
        }

        @Override
        protected void onProgressUpdate(Response<GDAX_API>... GdaxResponse) {
            super.onProgressUpdate(GdaxResponse);
            GDAX_API data = GdaxResponse[0].body();
            TextView tv = findViewById(R.id.chartDataView_gdax);
            String listStr;
            List<BarEntry> bidEntries = new ArrayList<>();
            List<BarEntry> askEntries = new ArrayList<>();
            List<List<Float>> buyList = data.getBids();
            List<List<Float>> sellList = data.getAsks();

            float maxQuan = 0;
            float minSell = 999999999;
            float maxBuy = 0;
            float percentageChange;

            for (List<Float> i : buyList) {
                Float quan = i.get(1);
                Float rate = i.get(0);
                if (quan > maxQuan) {
                    maxQuan = quan;
                }
                if (rate > maxBuy) {
                    maxBuy = rate;
                }
                bidEntries.add(new BarEntry(rate, quan));
            }
            listStr = ("\nMaxBuy:" + maxBuy + "  MaxQuan:" + maxQuan);
            maxQuan = 0;
            for (List<Float> i : sellList) {
                Float quan = i.get(1);
                Float rate = i.get(0);
                if (quan > maxQuan) {
                    maxQuan = quan;
                }
                if (rate < minSell) {
                    minSell = rate;
                }
                askEntries.add(new BarEntry(rate, quan));
            }
            percentageChange = (maxBuy-minSell)/minSell;
            listStr = listStr + ( "\nMinSell:" + minSell + "  MaxQuan:" + maxQuan
                    + "\nPercentage Change:" + percentageChange + "%");
            tv.setText(listStr);

            BarDataSet bidChart = new BarDataSet(bidEntries, "Bid");
            bidChart.setColor(Color.parseColor("#b60f13"));

            BarDataSet askChart = new  BarDataSet(askEntries, "Ask");
            askChart.setColor(Color.parseColor("#0523c1"));

            BarChart chart = findViewById(R.id.barChart_gdax);

            BarData chartData = new BarData();

            chartData.setBarWidth(0.5f);
            chartData.addDataSet(bidChart);
            chartData.addDataSet(askChart);
            chartData.setValueTextColor(Color.TRANSPARENT);
            chartData.setValueTextSize(10);
            chart.setData(chartData);
            chart.setDescription(null);
            chart.getLegend().setTextSize(12);

            chart.invalidate();
        }

        @Override
        protected void onPostExecute(Response<GDAX_API> GdaxResponse) {
            super.onPostExecute(GdaxResponse);
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(gdaxAT != null){
            gdaxAT.cancel(true);
        }
    }
}
