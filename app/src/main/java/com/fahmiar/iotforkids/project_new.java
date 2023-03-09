package com.fahmiar.iotforkids;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.fahmiar.iotforkids.Adapter.MyAdapter;
import com.fahmiar.iotforkids.model.Item;
import com.google.android.material.navigation.NavigationView;


import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import helpers.MySwipeHelper;
import helpers.DataHelper;
import helpers.MqttHelper;

public class project_new extends AppCompatActivity implements MyAdapter.OnItemListener, MyAdapter.OffItemListener {

    MqttHelper mqttHelper;
    Vibrator vibrator;
    String sw0 = "OFF";
    String sw1 = "OFF";
    String sw2 = "OFF";
    String swImg0 = "https://i.ibb.co/BgwQGQZ/button-off.png";
    String swImg1 = "https://i.ibb.co/BgwQGQZ/button-off.png";
    String swImg2 = "https://i.ibb.co/BgwQGQZ/button-off.png";

    String[] topicId;
    String[] komId;
    String[] Ondata;
    String[] Offdata;
    Integer[] dataon;
    DataHelper dbcenter;
    Cursor cursor;
    RecyclerView recyclerView;
    MyAdapter adapter;
    LinearLayoutManager layoutManager;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView backk;
    String[] numbId;
    List<Item> itemList = new ArrayList<>();
    Integer staru = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_new);

        backk = (ImageView) findViewById(R.id.backk);

        recyclerView = (RecyclerView)findViewById(R.id.recyle_tes);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerlayout);

        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        findViewById(R.id.menu_nav).setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
        findViewById(R.id.ruun).setOnClickListener(view -> StartMqtt());

        navigationView = (NavigationView)findViewById(R.id.nav_View);
        navigationView.setItemIconTintList(null);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        dbcenter = new DataHelper(this);

        MySwipeHelper swipeHelper = new MySwipeHelper(this, recyclerView, 200)
        {

            @Override
            public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<MySwipeHelper.MyButton> buffer) {
                buffer.add(new MyButton(project_new.this,
                        "Delete",
                        40,
                        0,
                        Color.parseColor("#FF3C30"),
                        pos -> {
                            itemList.remove(0);
                            generateitem();
                            Toast.makeText(project_new.this, "Delete", Toast.LENGTH_SHORT).show();
                        }));

                buffer.add(new MyButton(project_new.this,
                        "Edit",
                        40,
                        0,
                        Color.parseColor("#FF9502"),
                        pos -> {
                            Intent intent2 = new Intent(getApplicationContext(), PopActivity.class);
                            Toast.makeText(project_new.this, "Edit", Toast.LENGTH_SHORT).show();
                            startActivity(intent2);
                            RefreshList();
                        }));

            }
        };

        generateitem();

        backk.setOnClickListener(v -> {
            //kembali ke activity home
            Intent intent1 = new Intent(project_new.this, Frag_Frame.class);
            startActivity(intent1);
            dbcenter.onReset();
            finish();
        });
    }

    /*------------------------------------------Subscribe-----------------------------------------*/

    private void StartMqtt() {
        mqttHelper = new MqttHelper(getApplicationContext());
        mqttHelper.mqttAndroidClient.setCallback(new MqttCallbackExtended() {

            @Override
            public void connectComplete(boolean b, String s) {
                Toast.makeText(project_new.this, "Connected", Toast.LENGTH_SHORT).show();
                try{
                    //mqttHelper.mqttAndroidClient.subscribe("kontroler/data", 0);
                    for (String value : topicId) {
                        if (!value.equals("SWITCH")) {
                            mqttHelper.mqttAndroidClient.subscribe(value, 0);
                        }
                    }
                }catch (MqttException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void connectionLost(Throwable throwable) {
                Toast.makeText(project_new.this, "Failure", Toast.LENGTH_SHORT).show();
                vibrator.vibrate(500);
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug data  ", mqttMessage.toString());
                Log.w("topic  ", topic);
                Log.w("jumlah  ", String.valueOf(topicId.length));

                for (int up = 0; up < topicId.length; up++){
                    if(komId[up].equals("DISPLAY")) {
                        String updateImg;
                        if(topicId[up].equals(topic)){
                            String data = mqttMessage.toString();
                            int data1 = Integer.parseInt(data);
                            if(data1 <= 30){
                                updateImg = "https://i.ibb.co/92k3jF3/display-norm.png";
                            }else if(data1 <= 60){
                                updateImg = "https://i.ibb.co/gW0LzDJ/display-set.png";
                            }else {
                                updateImg = "https://i.ibb.co/JrNyNjf/display-danger.png";
                            }
                            itemList.set(up, new Item(numbId[up],
                                    komId[up],
                                    data,
                                    updateImg,
                                    "ON",
                                    "OFF"));
                        }
                    }
                    if(komId[up].equals("LED")) {
                        String updateId, updateImg;
                        if(topicId[up].equals(topic)){
                            String data = mqttMessage.toString();
                            if(data.equals("1")){
                                updateId = "NYALA";
                                updateImg = "https://lh3.googleusercontent.com/sUKbk1_Soq-gGPNjqgMUSMmqFAXWxcWIlxu1ZlwjSlnptUkbEE08XRof4HqVAcVw3A";
                            }else {
                                updateId = "MATI";
                                updateImg = "https://i.ibb.co/r2Qsxf4/led-mati.png";
                            }
                            itemList.set(up, new Item(numbId[up],
                                    komId[up],
                                    updateId,
                                    updateImg,
                                    "ON",
                                    "OFF"));
                        }
                    }
                    if(komId[up].equals("SWITCH")) {
                        if(dataon[0].equals(up)){
                            itemList.set(up, new Item(numbId[up],
                                    komId[up],
                                    sw0,
                                    swImg0,
                                    "ON",
                                    "OFF"));
                        }
                        else if(dataon[1].equals(up)){
                            itemList.set(up, new Item(numbId[up],
                                    komId[up],
                                    sw1,
                                    swImg1,
                                    "ON",
                                    "OFF"));
                        }
                        else if(dataon[2].equals(up)){
                            itemList.set(up, new Item(numbId[up],
                                    komId[up],
                                    sw2,
                                    swImg2,
                                    "ON",
                                    "OFF"));
                        }
                    }
                }
                generateitem();
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }


    private void generateitem(){
        for(int i = 0; i < itemList.size(); i++)
            itemList.get(i);

        adapter = new MyAdapter(this,itemList, this, this);
        recyclerView.setAdapter(adapter);
    }

    public void ledOnClick(MenuItem item) {
        staru = 1;
        SQLiteDatabase db = dbcenter.getWritableDatabase();
        db.execSQL("insert into data(komponen,topic,payload1,payload2) values('LED','kontroler/led','null','null')");


        RefreshList();
    }

    public void displayOnClick(MenuItem item) {
        staru = 1;
        SQLiteDatabase db = dbcenter.getWritableDatabase();
        db.execSQL("insert into data(komponen,topic,payload1,payload2) values('DISPLAY','kontroler/data','null','null')");


        RefreshList();
    }

    public void btnOnClick(MenuItem item) {
        staru = 1;
        SQLiteDatabase db = dbcenter.getWritableDatabase();
        db.execSQL("insert into data(komponen,topic,payload1,payload2) values('SWITCH','kontroler/send','null','null')");


        RefreshList();
    }

    public void RefreshList(){
        int ab = 0;
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM data",null);
        komId = new String[cursor.getCount()];
        topicId = new String[cursor.getCount()];
        numbId = new String[cursor.getCount()];
        Offdata = new String[cursor.getCount()];
        Ondata = new  String[cursor.getCount()];
        dataon = new Integer[cursor.getCount()];
        cursor.moveToFirst();
        itemList.clear();

        for (int cc=0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            numbId[cc] = cursor.getString(0);
            komId[cc] = cursor.getString(1);
            topicId[cc] = cursor.getString(2);
            Ondata[cc] = cursor.getString(3);
            Offdata[cc] = cursor.getString(4);

            if(komId[cc].equals("DISPLAY")) {

                itemList.add(new Item(numbId[cc],
                        komId[cc],
                        "NULL",
                        "https://i.ibb.co/xD5kS4f/display-off.png",
                        "ON",
                        "OFF"));
            }
            if(komId[cc].equals("LED")){
                itemList.add(new Item(numbId[cc],
                        komId[cc],
                        "NULL",
                        "https://i.ibb.co/r2Qsxf4/led-mati.png",
                        "ON",
                        "OFF"));
            }
            if(komId[cc].equals("SWITCH")) {
                Log.d("data switch ", String.valueOf(ab));
                dataon[ab] = cc;
                itemList.add(new Item(numbId[cc],
                        komId[cc],
                        "NULL",
                        "https://i.ibb.co/BgwQGQZ/button-off.png",
                        "ON",
                        "OFF"));

                ab = ab + 1;
            }
        }
        generateitem();
    }

    /*------------------------------------PUBLISH------------------------------------------------*/
    @Override
    public void onItemClick(int position) {
        for (int d = 0; d < komId.length; d++) {
            if (komId[d].equals("SWITCH")) {
                    if (position == dataon[0]) {
                        Log.d("tag", "Position Click SW1 ON " + position);
                        sw0 = "ON";
                        swImg0 = "https://i.ibb.co/3TZ9HDW/button-on.png";
                        String topic = topicId[d];
                        String payload = Ondata[d];
                        //byte[] encodedPayload;

                        try {
                            //encodedPayload = payload.getBytes(StandardCharsets.UTF_8);
                            //MqttMessage message = new MqttMessage(encodedPayload);
                            mqttHelper.mqttAndroidClient.publish(topic,payload.getBytes(),0,false);
                        }catch (MqttException e) {
                            e.printStackTrace();
                        }
                    }
                else if (position == dataon[1]) {
                    Log.d("tag", "Position Click SW1 ON " + position);
                    sw1 = "ON";
                    swImg1 = "https://i.ibb.co/3TZ9HDW/button-on.png";
                    String topic = topicId[d];
                    String payload = Ondata[d];
                    byte[] encodedPayload;

                    try {
                        encodedPayload = payload.getBytes(StandardCharsets.UTF_8);
                        MqttMessage message = new MqttMessage(encodedPayload);
                        mqttHelper.mqttAndroidClient.publish(topic,message);
                    }catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
                else if (position == dataon[2]) {
                    Log.d("tag", "Position Click SW1 ON " + position);
                    sw2 = "ON";
                    swImg2 = "https://i.ibb.co/3TZ9HDW/button-on.png";
                    String topic = topicId[d];
                    String payload = Ondata[d];
                    byte[] encodedPayload;

                    try {
                        encodedPayload = payload.getBytes(StandardCharsets.UTF_8);
                        MqttMessage message = new MqttMessage(encodedPayload);
                        mqttHelper.mqttAndroidClient.publish(topic,message);
                    }catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Log.d("tag", "Position Click " + position);
            }
        }
    }

    @Override
    public void offItemClick(int position) {
        for (int d = 0; d < komId.length; d++) {
            if (komId[d].equals("SWITCH")) {
                if (position == dataon[0]) {
                    Log.d("tag", "Position Click SW1 OFF " + position);
                    sw0 = "OFF";
                    swImg0 = "https://i.ibb.co/BgwQGQZ/button-off.png";
                    String topic = topicId[d];
                    String payload = Offdata[d];
                    //byte[] encodedPayload;

                    try {
                        //encodedPayload = payload.getBytes(StandardCharsets.UTF_8);
                        //MqttMessage message = new MqttMessage(encodedPayload);
                        mqttHelper.mqttAndroidClient.publish(topic,payload.getBytes(),0,false);
                    }catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
                else if (position == dataon[1]) {
                    Log.d("tag", "Position Click SW1 OFF " + position);
                    sw1 = "OFF";
                    swImg1 = "https://i.ibb.co/BgwQGQZ/button-off.png";
                    String topic = topicId[d];
                    String payload = Offdata[d];
                    byte[] encodedPayload;

                    try {
                        encodedPayload = payload.getBytes(StandardCharsets.UTF_8);
                        MqttMessage message = new MqttMessage(encodedPayload);
                        mqttHelper.mqttAndroidClient.publish(topic,message);
                    }catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
                else if (position == dataon[2]) {
                    Log.d("tag", "Position Click SW1 OFF " + position);
                    sw2 = "OFF";
                    swImg2 = "https://i.ibb.co/BgwQGQZ/button-off.png";
                    String topic = topicId[d];
                    String payload = Offdata[d];
                    byte[] encodedPayload;

                    try {
                        encodedPayload = payload.getBytes(StandardCharsets.UTF_8);
                        MqttMessage message = new MqttMessage(encodedPayload);
                        mqttHelper.mqttAndroidClient.publish(topic,message);
                    }catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Log.d("tag", "Position Click " + position);
            }
        }
    }
}