package com.example.lenovo.ceg;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public GetInterfaceCEX getInterface;
    public Retrofit retrofit;
    public CEX data;
    public String symbol1="BTC";
    public String symbol2="USD";
    public String depth="14";
    public ArrayList<CEX> bidList = new ArrayList<>();
    Response<CEX> res;
    public CEXAPI cexAPI = new CEXAPI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://cex.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getInterface = retrofit.create(GetInterfaceCEX.class);
        cexAPI.execute();
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(cexAPI==null) {
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
            bidList.add(data);
            TextView tv = findViewById(R.id.chartDataView);

            String ListStr = new String();
            List<BarEntry> bidEntries = new ArrayList<>();
            List<BarEntry> askEntries = new ArrayList<>();
            List<List<Float>> buyList = data.getBids();
            List<List<Float>> sellList = data.getAsks();

            float maxQuan = 0;
            float minSell = 999999999;
            float maxBuy = 0;

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
            ListStr = ("MaxBuy:" + maxBuy + "  MaxQuan:" + maxQuan);
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
            ListStr = ListStr + ( "\nMinSell:" + minSell + "  MaxQuan:" + maxQuan);
            tv.setText( ListStr);

            BarDataSet bidChart = new BarDataSet(bidEntries, "Bid");
            bidChart.setColor(Color.GREEN);

            BarDataSet askChart = new  BarDataSet(askEntries, "Ask");
            askChart.setColor(Color.BLUE);

            BarChart chart = findViewById(R.id.barChart);

            BarData chartData = new BarData();

            chartData.setBarWidth(1f);
            chartData.addDataSet(bidChart);
            chartData.addDataSet(askChart);
            chart.setData(chartData);
            chart.setDescription(null);
            chart.getLegend().setEnabled(false);

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
        if(cexAPI!=null){
            cexAPI.cancel(true);
        }
    }
}
