package com.example.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static android.widget.Toast.LENGTH_SHORT;


public class DoctorProfile extends AppCompatActivity {
    String number;
    private ImageView back;
    private boolean flag;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        back = findViewById(R.id.back_doctor_card);

        Doctor doctor = (Doctor) intent.getExtras().get("doctor");
        number = (String) intent.getExtras().get("number");
        flag = (boolean) intent.getExtras().get("flag");

        TextView textViewName = findViewById(R.id.doctor_card_name);
        textViewName.setText(doctor.getName());
        TextView textViewProf = findViewById(R.id.doctor_card_proffessions);
        textViewProf.setText(doctor.getProffession());
        Toast.makeText(getApplicationContext(), ""+flag, LENGTH_SHORT).show();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DoctorsList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("number", number);
                intent.putExtra("flag", flag);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }


    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), DoctorsList.class);
        intent.putExtra("number", number);
        intent.putExtra("flag", flag);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
