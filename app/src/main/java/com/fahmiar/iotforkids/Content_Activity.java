package com.fahmiar.iotforkids;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class Content_Activity extends AppCompatActivity {

    private String user, password;
    TextView  txthello;
    private final String KEY_USER = "username";
    private final String KEY_PASS = "password";

    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        RelativeLayout relativeLayout = findViewById(R.id.design_bottom_sheet);
        txthello = findViewById(R.id.txthello);
        Button pro = findViewById(R.id.project);
        Button back = findViewById(R.id.back);
        ImageView img = findViewById(R.id.logo);

        Bundle extras = getIntent().getExtras();
        user = extras.getString(KEY_USER);
        password = extras.getString(KEY_PASS);

        txthello.setText("Hello, " + user + " !.");

        bottomSheetBehavior = BottomSheetBehavior.from(relativeLayout);

        img.setOnClickListener(v -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });

        pro.setOnClickListener(v -> {
            //lanjut ke menu
            Intent intent0 =new Intent(Content_Activity.this, Frag_Frame.class);
            startActivity(intent0);
            finish();
        });

        back.setOnClickListener(v -> {
            //kembali ke activity login
            Intent intent1 = new Intent(Content_Activity.this, MainActivity.class);
            startActivity(intent1);
            finish();
        });

    }
}