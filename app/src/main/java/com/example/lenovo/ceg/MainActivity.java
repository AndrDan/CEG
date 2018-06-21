package com.example.lenovo.ceg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.lenovo.ceg.CEG.CegActivity;
import com.example.lenovo.ceg.CEX.CexActivity;

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
    }
}
