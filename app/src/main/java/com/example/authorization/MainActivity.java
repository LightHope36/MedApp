package com.example.authorization;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;


public class MainActivity extends AppCompatActivity {

    private Button next;
    private ImageView sogl;
    private ConstraintLayout cs;
    public List<Map.Entry<String, String>> list;

    String url = "jdbc:mysql://server23.hosting.reg.ru/u0597423_medclick.kvantorium69";
    String username = "u0597423_medclic";
    String password = "kvantoriummagda";

    private String OpenTable = ("create table if not exists client (\n" +
            "\tclientid INT PRIMARY KEY AUTO_INCREMENT, \n" +
            "\tname varchar(15), \n" +
            "\tsurname varchar(15), \n" +
            "\tpatronymic varchar(15), \n" +
            "\tmedical_policy varchar(16), \n" +
            "\tPhone_number int, \n" +
            "\tsnils int, \n" +
            "\tdate_of_birth datetime, \n" +
            "\tmedical_history int, \n" +
            "\tcompanies_providing_medical_insurance int )\n");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        next = findViewById(R.id.next_btn);
        sogl = findViewById(R.id.sogl);
        cs = findViewById(R.id.sogl_cs);

        list = new LinkedList<>();


        SQLiteDatabase lastuser = openOrCreateDatabase("lastuser", MODE_PRIVATE, null);
        lastuser.execSQL("create table if not exists lastuser\n" +
                "(\n" +
                "\tUserPhone varchar(10) \n" +
                ");");

        Cursor c = lastuser.rawQuery("select * from lastuser", null);
        int id = c.getColumnIndex("UserPhone");
        if(c.moveToFirst()){
            Intent intent_dial = new Intent(getApplicationContext(), MainPage2.class);
            intent_dial.putExtra("number", c.getString(id));
            intent_dial.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent_dial);
            overridePendingTransition(0, 0);
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), auth2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Soglashenie.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

    }

}