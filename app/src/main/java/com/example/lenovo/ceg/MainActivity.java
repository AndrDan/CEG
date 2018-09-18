package com.example.lenovo.ceg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.lenovo.ceg.CEG.CegActivity;
import com.example.lenovo.ceg.Exchanges.Exchanges_Activity.BitstampActivity;
import com.example.lenovo.ceg.Exchanges.Exchanges_Activity.CexActivity;
import com.example.lenovo.ceg.Exchanges.Exchanges_Activity.CoinsBankActivity;
import com.example.lenovo.ceg.Exchanges.Exchanges_Activity.EXMOActivity;
import com.example.lenovo.ceg.Exchanges.Exchanges_Activity.GDAXActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.ceg_btn) {
            Intent intentCeg = new Intent(this, CegActivity.class);
            startActivity(intentCeg);
        }
        if (view.getId() == R.id.cex_btn) {
            Intent intentCex = new Intent(this, CexActivity.class);
            startActivity(intentCex);
        }
        if (view.getId() == R.id.bitstamp_btn) {
            Intent intentBitstamp = new Intent(this, BitstampActivity.class);
            startActivity(intentBitstamp);
        }
        if (view.getId() == R.id.exmo_btn) {
            Intent intentExmo = new Intent(this, EXMOActivity.class);
            startActivity(intentExmo);
        }
        if (view.getId() == R.id.gdax_btn) {
            Intent intentGdax = new Intent(this, GDAXActivity.class);
            startActivity(intentGdax);
        }
        if (view.getId() == R.id.coins_bank_btn) {
            Intent intentCoinsBank = new Intent(this, CoinsBankActivity.class);
            startActivity(intentCoinsBank);
        }
    }
}