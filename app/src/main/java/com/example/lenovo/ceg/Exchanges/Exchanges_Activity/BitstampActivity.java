package com.example.lenovo.ceg.Exchanges.Exchanges_Activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.lenovo.ceg.Exchanges.Exchanges_API.BitstampAPI.Bitstamp_API;
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

public class BitstampActivity extends Activity {
    GetInterfaceExchanges getInterface;

    public BitstampAT bitstampAT = new BitstampAT();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bitstamp_activity);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.bitstamp.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getInterface = retrofit.create(GetInterfaceExchanges.class);
        bitstampAT.execute();
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(bitstampAT == null) {
            bitstampAT.execute();
        }
    }

    class BitstampAT extends AsyncTask<Void, Response<Bitstamp_API>, Response<Bitstamp_API>> {

        @Override
        protected Response<Bitstamp_API> doInBackground(Void... voids) {
            Response<Bitstamp_API> res = null;
            while (!isCancelled()) {
                try {
                    String symbol1 = "btcusd";
                    Call<Bitstamp_API> responseCall = getInterface.getBitstampData(symbol1);
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
        protected void onProgressUpdate(Response<Bitstamp_API>... BitstampResponse) {
            super.onProgressUpdate(BitstampResponse);
            Bitstamp_API data = BitstampResponse[0].body();
            TextView tv = findViewById(R.id.chartDataView_bitstamp);
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
            listStr = ("MaxBuy:" + maxBuy + "  MaxQuan:" + maxQuan);
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

            BarChart chart = findViewById(R.id.barChart_bitstamp);

            BarData chartData = new BarData();

            chartData.setBarWidth(1f);
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
        protected void onPostExecute(Response<Bitstamp_API> BitstampResponse) {
            super.onPostExecute(BitstampResponse);
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(bitstampAT != null){
            bitstampAT.cancel(true);
        }
    }
}
