package com.example.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class Profile extends AppCompatActivity {
    String number;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_card);
        getSupportActionBar().hide();
        Intent intent = getIntent();

        Doctor doctor = (Doctor) intent.getExtras().get("doctor");
        number = (String) intent.getExtras().get("number");

        TextView textViewName = findViewById(R.id.doctor_card_name);
        textViewName.setText(doctor.getName());
        TextView textViewProf = findViewById(R.id.doctor_card_proffessions);
        textViewProf.setText(doctor.getProffession());
    }
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), DoctorsList.class);
        intent.putExtra("number", number);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
