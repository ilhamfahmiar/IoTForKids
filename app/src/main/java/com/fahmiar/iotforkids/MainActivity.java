package com.fahmiar.iotforkids;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    public String user_db, pass_db;

    private final String KEY_USER = "username";
    private final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText user = findViewById(R.id.username);
        EditText pas = findViewById(R.id.password);
        Button login_btn = findViewById(R.id.login_button);

        login_btn.setOnClickListener(v -> {
            try{
                user_db = user.getText().toString();
                pass_db = pas.getText().toString();
                if (!user_db.equals("") && !pass_db.equals("")){
                    if(user_db.equals("ilhamfahmi") && pass_db.equals("12345")) {
                        Intent i = new Intent(MainActivity.this, Content_Activity.class);
                        i.putExtra(KEY_USER, user_db);
                        i.putExtra(KEY_PASSWORD, pass_db);
                        startActivity(i);
                    }
                } else {
                    Toast.makeText(getApplication(), "Masukkan Nama dulu",Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getApplication(), "Error, Coba Lagi",Toast.LENGTH_SHORT).show();
            }
        });

    }
}