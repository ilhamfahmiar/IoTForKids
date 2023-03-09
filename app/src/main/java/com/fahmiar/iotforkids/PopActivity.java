package com.fahmiar.iotforkids;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import helpers.DataHelper;

public class PopActivity extends AppCompatActivity {

    EditText topic, py1, py2, numb;
    //String numbdata,topicdata, py1data, py2data;
    Button sub;
    DataHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        topic = (EditText) findViewById(R.id.topic);
        py1 = (EditText) findViewById(R.id.py1);
        py2 = (EditText) findViewById(R.id.py2);
        numb = (EditText) findViewById(R.id.numbID);
        sub = (Button) findViewById(R.id.sub);

        dbHelper = new DataHelper(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.8));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;


        getWindow().setAttributes(params);

//        numbdata = numb.getText().toString();
//        topicdata = topic.getText().toString();
//        py1data = py1.getText().toString();
//        py2data = py2.getText().toString();

        // daftarkan even onClick pada btnSimpan
        sub.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("UPDATE data SET topic='"+
                    topic.getText().toString() +"', payload1='" +
                    py1.getText().toString()+"', payload2='"+
                    py2.getText().toString() +"' WHERE noId='" +
                    numb.getText().toString()+"'");
            Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
            finish();
        });

    }

//    public void updatebre(View v){
//        dbHelper.UpdateData(numbdata, topicdata, py1data, py2data);
//        project_new.pro.RefreshList();
//        finish();
//    }
}