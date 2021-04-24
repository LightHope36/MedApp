package com.example.authorization;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DoctorsList extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);
        getSupportActionBar().hide();
        ArrayList <Doctor> docs = new ArrayList<>();
        docs.add(new Doctor("stas", ProffessionsList.profs[0]));
    }
}
