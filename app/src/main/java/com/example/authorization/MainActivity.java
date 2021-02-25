package com.example.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button next;
    private Button next1;
    private Button back1;
    private Button next2;
    private Button back2;
    private Button next3;
    private Button back3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        Button next = findViewById(R.id.next_btn);
        Button next1= findViewById(R.id.next_btn1);
        Button back1 = findViewById(R.id.back_btn1);
        Button next2= findViewById(R.id.next_btn2);
        Button back2 = findViewById(R.id.back_btn2);
        Button next3= findViewById(R.id.next_btn3);
        Button back3 = findViewById(R.id.back_btn3);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.auth2);
            }
        });

        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.auth3);
            }
        });
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_main);
            }
        });

        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.auth4);
            }
        });
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.auth2);
            }
        });


        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.auth3);
            }
        });

    }
}