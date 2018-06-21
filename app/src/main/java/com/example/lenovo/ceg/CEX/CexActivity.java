package com.example.lenovo.ceg.CEX;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import com.example.lenovo.ceg.Exchanges.CEXAPI.CEX;
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

public class CexActivity extends AppCompatActivity {

    public GetInterfaceCEX getInterface;
    public CEX data;
    public String symbol1="BTC";
    public String symbol2="USD";
    public String depth="14";
    Response<CEX> res;
    public CEXAPI cexAPI = new CEXAPI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cex_activity);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cex.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getInterface = retrofit.create(GetInterfaceCEX.class);
        cexAPI.execute();
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(cexAPI == null) {
            cexAPI.execute();
        }
    }

    class CEXAPI extends AsyncTask<Void, Response<CEX>, Response<CEX>> {

        @Override
        protected Response<CEX> doInBackground(Void... voids) {
            res = null;
            while (!isCancelled()) {
                try {
                    Call<CEX> responseCall = getInterface.getData(symbol1, symbol2, depth);
                    res = responseCall.execute();
                    publishProgress(res);
                    Thread.sleep(3000);
                    Log.d("CYCLE", "+1");
                } catch (IOException | InterruptedException e) {
                    Log.e("ERROR", e.toString());
                }
            }
            return res;
        }

        @Override
        protected void onProgressUpdate(Response<CEX>... CEXResponse) {
            super.onProgressUpdate(CEXResponse);
            data = CEXResponse[0].body();
            TextView tv = findViewById(R.id.chartDataView);

            String listStr = new String();
            List<BarEntry> bidEntries = new ArrayList<>();
            List<BarEntry> askEntries = new ArrayList<>();
            List<List<Float>> buyList = data.getBids();
            List<List<Float>> sellList = data.getAsks();

            float maxQuan = 0;
            float minSell = 999999999;
            float maxBuy = 0;
            float percentageChange = 0;

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
            bidChart.setColor(Color.RED);

            BarDataSet askChart = new  BarDataSet(askEntries, "Ask");
            askChart.setColor(Color.BLACK);

            BarChart chart = findViewById(R.id.barChart);

            BarData chartData = new BarData();

            chartData.setBarWidth(1f);
            chartData.addDataSet(bidChart);
            chartData.addDataSet(askChart);
            chartData.setValueTextColor(Color.BLUE);
            chartData.setValueTextSize(10);
            chart.setData(chartData);
            chart.setDescription(null);
            chart.getLegend().setTextSize(12);

            chart.invalidate();
        }

        @Override
        protected void onPostExecute(Response<CEX> CEXResponse) {
            super.onPostExecute(CEXResponse);
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(cexAPI != null){
            cexAPI.cancel(true);
        }
    }
}
