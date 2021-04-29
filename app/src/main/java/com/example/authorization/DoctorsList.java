package com.example.authorization;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DoctorsList extends AppCompatActivity {

    private ListView listView;
    int count = 0;
    int count2 = 0;
    private ImageView filter;
    private ConstraintLayout top;
    private TextView professions;
    private TextView doctors_tv;
    private ConstraintLayout dialogs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        String number = (String) intent.getExtras().get("number");

        ListView listView = findViewById(R.id.list_of_professions);

        String [] proffessions_array =  getResources().getStringArray(R.array.proffessions_string_array);
        ArrayList <Doctor> filtredDoctors = new ArrayList<>();
        ArrayList<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor("dima", proffessions_array[1]));
        doctors.add(new Doctor("sril", proffessions_array[2]));
        doctors.add(new Doctor("fhat", proffessions_array[2]));
        doctors.add(new Doctor("bmat", proffessions_array[2]));
        doctors.add(new Doctor("xlat", proffessions_array[0]));
        doctors.add(new Doctor("Lat", proffessions_array[0]));

        ProfAdapter adapterProfs = new ProfAdapter(this, R.layout.profession_card, proffessions_array);
        DoctorAdapter adapterDoctor = new DoctorAdapter(this, R.layout.person, filtredDoctors);

        listView.setAdapter(adapterProfs);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //int i = 1;
               System.out.println(adapterProfs.getItem(position));


               for (int d = 0; d < doctors.size();d++){
                   //System.out.println(doctors.get(d).getProffession() + "ehjfojsrhgferotjhkrjngkjurhfd");
                   if(doctors.get(d).getProffession().equals(adapterProfs.getItem(position))){
                       filtredDoctors.add(doctors.get(d));
                   }
               }

               listView.setAdapter(adapterDoctor);
               listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       Intent intent = new Intent(getApplicationContext(), Profile.class);

                   }
               });
            }
        });


        dialogs = findViewById(R.id.cs_to_dial);
        dialogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dialogs.class);
                intent.putExtra("number", number);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

    }
    class DoctorAdapter extends ArrayAdapter<Doctor> {
        public DoctorAdapter(@NonNull Context context, int resource, @NonNull List<Doctor> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Doctor doctor = getItem(position);
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.person, null);


            DoctorHolder holderName = new DoctorHolder();
            holderName.text = convertView.findViewById(R.id.user);
            holderName.text.setText(doctor.getName());

            DoctorHolder holderProf = new DoctorHolder();
            holderProf.text = convertView.findViewById(R.id.dopinfo_text);
            holderProf.text.setText(doctor.getProffession());

            convertView.setTag(holderProf);
            convertView.setTag(holderName);


            return convertView;
        }
    }
    class ProfAdapter extends ArrayAdapter<String> {

        public ProfAdapter(@NonNull Context context, int resource, @NonNull String[] str) {
            super(context, resource, str);
        }

        @NonNull
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            String string = getItem(position);

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.profession_card, null);

            DoctorHolder holder = new DoctorHolder();
            holder.text = convertView.findViewById(R.id.profession_text);
            holder.text.setText(string);

            convertView.setTag(holder);

            return convertView;
        }


    }
    private static class DoctorHolder {
        public TextView text;
    }

}

