package com.example.authorization;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DoctorsList extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);
        getSupportActionBar().hide();

        listView = findViewById(R.id.list_of_professions);

        List<Doctor> doctors = new ArrayList<>();

        DoctorAdapter docadapter = new DoctorAdapter(getApplicationContext(), R.layout.profession_card, doctors);
        listView.setAdapter(docadapter);


    }

    private static class DoctorAdapter extends ArrayAdapter<Doctor> {

        public DoctorAdapter(@NonNull Context context, int resource, @NonNull List<Doctor> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Doctor doctor = getItem(position);
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.profession_card, null);


            DoctorHolder holder = new DoctorHolder();
            holder.text = convertView.findViewById(R.id.profession_text);

            holder.text.setText(doctor.getText());


            convertView.setTag(holder);


            return convertView;
        }
    }

    private static class DoctorHolder {
        public TextView text;
    }

}