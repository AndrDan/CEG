package com.example.lenovo.ceg.CEG;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.lenovo.ceg.CEX.GetInterfaceCEX;
import com.example.lenovo.ceg.Exchanges.CEXAPI.CEX;
import com.example.lenovo.ceg.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CegActivity extends AppCompatActivity {
    public Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ceg_activity);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final MainOrderBook data = new MainOrderBook();
                String listStr;
                final List<BarEntry> bidEntries = new ArrayList<>();
                final List<BarEntry> askEntries = new ArrayList<>();
                final List<List<Float>> buyList = data.getBids();
                final List<List<Float>> sellList = data.getAsks();

                //Log.e("MainOrderBook", data.getTest1().toString());
                float maxQuan = 0;
                float percentageChange = 0;

                for (List<Float> i : buyList) {
                    Float quan = i.get(1);
                    Float rate = i.get(0);
                    if (quan > maxQuan) {
                        maxQuan = quan;
                    }
                    bidEntries.add(new BarEntry(rate, quan));
                }
                listStr = ("MaxBuy:" + buyList.get(0).get(0) + "  MaxQuan:" + maxQuan);
                maxQuan = 0;
                for (List<Float> i : sellList) {
                    Float quan = i.get(1);
                    Float rate = i.get(0);
                    if (quan > maxQuan) {
                        maxQuan = quan;
                    }
                    askEntries.add(new BarEntry(rate, quan));
                }
                percentageChange = (buyList.get(0).get(0)-sellList.get(0).get(0))/sellList.get(0).get(0);
                listStr = listStr + ( "\nMinSell:" + sellList.get(0).get(0) + "  MaxQuan:" + maxQuan
                        + "\nPercentage Change:" + percentageChange + "%");

                final String finalListStr = listStr;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv = findViewById(R.id.chartDataView_ceg);
                        tv.setText(finalListStr);

                        BarDataSet bidChart = new BarDataSet(bidEntries, "Bid");
                        bidChart.setColor(Color.RED);

                        BarDataSet askChart = new  BarDataSet(askEntries, "Ask");
                        askChart.setColor(Color.BLACK);
                        askChart.setValueTextColor(Color.parseColor("#FFFFFFFF"));

                        BarChart chart = findViewById(R.id.barChart_ceg);

                        BarData chartData = new BarData();

                        chartData.setBarWidth(2f);
                        chartData.addDataSet(bidChart);
                        chartData.addDataSet(askChart);
                        chart.setData(chartData);
                        chart.setDescription(null);
                        chartData.setValueTextColor(Color.BLUE);
                        chartData.setValueTextSize(10);
                        chart.getLegend().setTextSize(12);

                        chart.invalidate();
                    }
                });
            }
        }, 0, 5000);
    }


    @Override
    protected void onStop(){
        super.onStop();
        if(timer != null){
            timer.cancel();
        }
    }
}
