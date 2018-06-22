package com.example.lenovo.ceg.Exchanges.Exchanges_Activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import com.example.lenovo.ceg.Exchanges.GetInterfaceExchanges;
import com.example.lenovo.ceg.Exchanges.Exchanges_API.CEXAPI.CEX_API;
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

public class CexActivity extends Activity {

    GetInterfaceExchanges getInterface;

    public CexAT cexAT = new CexAT();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cex_activity);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cex.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getInterface = retrofit.create(GetInterfaceExchanges.class);
        cexAT.execute();
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(cexAT == null) {
            cexAT.execute();
        }
    }

    class CexAT extends AsyncTask<Void, Response<CEX_API>, Response<CEX_API>> {

        @Override
        protected Response<CEX_API> doInBackground(Void... voids) {
            Response<CEX_API> res = null;
            while (!isCancelled()) {
                try {
                    String symbol1="BTC";
                    String symbol2="USD";
                    String depth="20";
                    Call<CEX_API> responseCall = getInterface.getCexData(symbol1, symbol2, depth);
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
        protected void onProgressUpdate(Response<CEX_API>... CEXResponse) {
            super.onProgressUpdate(CEXResponse);
            CEX_API data = CEXResponse[0].body();
            TextView tv = findViewById(R.id.chartDataView_cex);
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

            BarChart chart = findViewById(R.id.barChart_cex);

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
        protected void onPostExecute(Response<CEX_API> CEXResponse) {
            super.onPostExecute(CEXResponse);
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(cexAT != null){
            cexAT.cancel(true);
        }
    }
}
