package com.example.authorization;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import java.util.ArrayList;

public class DoctorsList extends AppCompatActivity {
    public static String profsFilter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        String [] d =  getResources().getStringArray(R.array.proffessions_string_array);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);
        getSupportActionBar().hide();
        ListView listView = findViewById(R.id.list_of_doctors);
        ArrayList <Doctor> docs = new ArrayList<>();
        docs.add(new Doctor("dimka", d[0]));
        docs.add(new Doctor("stas", d[1]));
        ArrayList<String> docsNames = new ArrayList<>();

        for (int i = 0; i < docs.size(); i++){
            if(docs.get(i).getProffession().equals(profsFilter)){
                docsNames.add(docs.get(i).getName());
            }
        }
        ArrayAdapter<String> s = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1 , docsNames);
        listView.setAdapter(s);
    }
}
