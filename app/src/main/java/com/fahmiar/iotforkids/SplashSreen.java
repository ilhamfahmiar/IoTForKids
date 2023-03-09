package com.fahmiar.iotforkids;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import helpers.DataHelper;

public class SplashSreen extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 5000;
    DataHelper dbcenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_sreen);

        dbcenter = new DataHelper(this);


        new Handler().postDelayed(() -> {
            //setelah loading maka akan langsung berpindah ke login activity
            dbcenter.onReset();
            Intent i =new Intent(SplashSreen.this, MainActivity.class);
            startActivity(i);
            finish();
        },SPLASH_SCREEN);

    }
}