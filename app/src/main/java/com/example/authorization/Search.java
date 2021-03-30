package com.example.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Search extends AppCompatActivity {

    private ImageView back;
    String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        back = findViewById(R.id.back_im_from_search);




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search.super.finish();
            }
        });
    }
}