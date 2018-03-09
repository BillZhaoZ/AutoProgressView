package com.zhao.bill.autoprogressview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoProgressView autoProgressView = findViewById(R.id.auto_process);

        autoProgressView.setProgress(0.75);
        autoProgressView.setRateBackgroundColor("#e4f6eb");
        autoProgressView.setOrientation(LinearLayout.HORIZONTAL);
        autoProgressView.setAnimRate((int) (0.75 * 30));
    }
}
