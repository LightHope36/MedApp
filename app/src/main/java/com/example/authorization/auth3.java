package com.example.authorization;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Random;

public class auth3 extends AppCompatActivity {

    private Button next2;
    private Button back2;
    private EditText reg;
    private EditText phone;
    // Идентификатор уведомления
    private static final int NOTIFY_ID = 10;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth3);

        Button next2= findViewById(R.id.next_btn2);
        Button back2 = findViewById(R.id.back_btn2);
        EditText reg = findViewById(R.id.region_input);
        EditText phone = findViewById(R.id.phone_input);


        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phone.length()==10){
                Intent intent = new Intent(getApplicationContext(), auth4.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
            }
        });
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), auth2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
    }
}