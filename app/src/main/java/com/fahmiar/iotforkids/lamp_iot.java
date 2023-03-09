package com.fahmiar.iotforkids;

import helpers.MqttHelper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class lamp_iot extends AppCompatActivity {

    MqttHelper mqttHelper;
    int data;
    TextView dataclaim;
    ImageButton backk1;
    ImageView change;
    int red = Color.parseColor("#F53241");
    int green = Color.parseColor("#FF00FF0A");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp_iot);

        dataclaim = findViewById(R.id.dataclaim);
        backk1 = findViewById(R.id.backk1);
        change = findViewById(R.id.change);

        backk1.setOnClickListener(v -> {
            //kembali ke activity awal
            Intent intent1 = new Intent(lamp_iot.this, Frag_Frame.class);
            startActivity(intent1);
            finish();
        });

        startMqtt();
    }

    private void startMqtt() {
        mqttHelper = new MqttHelper(getApplicationContext());
        mqttHelper.mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.w("Debug","Connected");
            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug servo", mqttMessage.toString());
                Log.w("topic = ", topic);
                data = Integer.parseInt(mqttMessage.toString());
                dataclaim.setText(String.valueOf(data));
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }
    /*-------------------------------------------publish-------------------------------------------------*/
    public void on(View v) {
        String topic = "kontroler/trima";
        String payload = "2";
        byte[] encodedPayload = new byte[0];
        change.setColorFilter(green);

        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            mqttHelper.mqttAndroidClient.publish(topic,message);
        }catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }

    public void off(View v) {
        String topic = "kontroler/trima";
        String payload = "3";
        byte[] encodedPayload = new byte[0];
        change.setColorFilter(red);

        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            mqttHelper.mqttAndroidClient.publish(topic,message);
        }catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }

}