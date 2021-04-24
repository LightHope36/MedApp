package com.example.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ProffessionsList extends AppCompatActivity {

    static String[] profs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String [] d =  getResources().getStringArray(R.array.proffessions_string_array);
        profs = d;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proffessions_list);
        getSupportActionBar().hide();


        ListView listView = findViewById(R.id.list_of_professions);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, profs);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
               TextView textView = (TextView) itemClicked;
                DoctorsList.profsFilter = textView.getText().toString();
                //System.out.println(DoctorsList.profsFilter);
                Intent intent = new Intent(getApplicationContext(), DoctorsList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });

       System.out.println(listView.getItemAtPosition(2));


    }
    

}