package com.example.authorization;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class DoctorsList extends AppCompatActivity {

    private ListView listView;
    int count = 0;
    int count2 = 0;
    private ImageView filter;
    private ConstraintLayout top;
    private TextView professions;
    private TextView doctors_tv;
    private ConstraintLayout dialogs;
    private boolean flag;
    private String number;
    private ConstraintLayout profile;
    private EditText input;
    private ArrayList<Doctor> doctors(){
        String [] proffessions_array =  getResources().getStringArray(R.array.proffessions_string_array);
        ArrayList <Doctor> doctorsT = new ArrayList<>();
        doctorsT.add(new Doctor("dima", proffessions_array[1]));
        doctorsT.add(new Doctor("sril", proffessions_array[2]));
        doctorsT.add(new Doctor("fhat", proffessions_array[2]));
        doctorsT.add(new Doctor("bmat", proffessions_array[2]));
        doctorsT.add(new Doctor("xlat", proffessions_array[0]));
        doctorsT.add(new Doctor("Lat", proffessions_array[0]));
        return doctorsT;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);
        getSupportActionBar().hide();


        listView = findViewById(R.id.list_of_professions);
        dialogs = findViewById(R.id.cs_to_dial);
        filter = findViewById(R.id.filter);
        top = findViewById(R.id.top_doctors);
        professions = findViewById(R.id.professions_text);
        doctors_tv = findViewById(R.id.doctors_text);
        profile = findViewById(R.id.profile_in_doctorlist);
        input = findViewById(R.id.input);

        Intent intent = getIntent();
        number = (String) intent.getExtras().get("number");
        flag = true;
        Doctor doctor = null;

        try{
            flag = (boolean) intent.getExtras().get("flag");
            doctor = (Doctor) intent.getExtras().get("doctor");
        } catch(Exception e){}

        ArrayList <Doctor> filtredDoctors = new ArrayList<>();
        String [] proffessions_array =  getResources().getStringArray(R.array.proffessions_string_array);

        ProfAdapter adapterProfs = new ProfAdapter(this, R.layout.profession_card, proffessions_array);
        DoctorAdapter adapterDoctor = new DoctorAdapter(this, R.layout.person, filtredDoctors);

        if(flag == true){
            listView.setAdapter(adapterProfs);
        }
        else{
            for (int d = 0; d < doctors().size(); d++) {
                if (doctors().get(d).getProffession().equals(doctor.getProffession())) {
                    filtredDoctors.add(doctors().get(d));
                }
            }
            listView.setAdapter(adapterDoctor);
            professions.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.flow_shape_white));
            doctors_tv.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.flow_shape_white));
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (flag == true) {
                    filtredDoctors.clear();
                    for (int d = 0; d < doctors().size(); d++) {
                        if (doctors().get(d).getProffession().equals(adapterProfs.getItem(position))) {
                            filtredDoctors.add(doctors().get(d));
                        }
                    }
                    professions.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.flow_shape_white));
                    doctors_tv.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.flow_shape_white));

                    listView.setAdapter(adapterDoctor);
                    flag = false;
                }else {
                    try {
                        Intent intent = new Intent(getApplicationContext(), DoctorProfile.class);
                        intent.putExtra("doctor", filtredDoctors.get(position));
                        intent.putExtra("number", number);
                        intent.putExtra("flag", flag);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    } catch (Exception e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), LENGTH_SHORT).show();
                    }
                    flag = true;
                }
            }
        });



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

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                intent.putExtra("number", number);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        professions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                professions.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_text));
                doctors_tv.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.flow_shape_white));
                listView.setAdapter(adapterProfs);
                flag = true;
                input.setHint("Поиск по специальностям");
            }
        });

        doctors_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtredDoctors.clear();
                doctors_tv.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_text));
                professions.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.flow_shape_white));
                for (int i=0; i<doctors().size(); i++) {
                    filtredDoctors.add(doctors().get(i));
                }
                listView.setAdapter(adapterDoctor);
                flag = false;
                input.setHint("Поиск среди врачей");
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count2++;
                if (count2%2==1) {
                    int margin186inDp = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 186, getResources().getDisplayMetrics());
                    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams
                            (ConstraintLayout.LayoutParams.MATCH_PARENT, margin186inDp);
                    layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
                    top.setLayoutParams(layoutParams);
                    filter.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_filter_used));
                }
                else{
                    int margin112inDp = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 112, getResources().getDisplayMetrics());
                    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams
                            (ConstraintLayout.LayoutParams.MATCH_PARENT, margin112inDp);
                    layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
                    top.setLayoutParams(layoutParams);
                    filter.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_filter));
                }
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

    public void onBackPressed(){
        if(flag==true){
            Intent intent = new Intent(getApplicationContext(), MainPage2.class);
            intent.putExtra("number", number);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }else {
            Intent intent1 = getIntent();
            Intent intent2 = new Intent(getApplicationContext(), DoctorsList.class);
            String number = (String) intent1.getExtras().get("number");
            intent2.putExtra("number", number);

            ListView listView = findViewById(R.id.list_of_professions);
            String [] proffessions_array =  getResources().getStringArray(R.array.proffessions_string_array);
            ArrayList<Doctor> doctors = new ArrayList<>();
            doctors.add(new Doctor("dima", proffessions_array[1]));
            doctors.add(new Doctor("sril", proffessions_array[2]));
            doctors.add(new Doctor("fhat", proffessions_array[2]));
            doctors.add(new Doctor("bmat", proffessions_array[2]));
            doctors.add(new Doctor("xlat", proffessions_array[0]));
            doctors.add(new Doctor("Lat", proffessions_array[0]));
            ProfAdapter adapterProfs = new ProfAdapter(this, R.layout.profession_card, proffessions_array);

            listView.setAdapter(adapterProfs);
            flag = true;
            professions.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_text));
            doctors_tv.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.flow_shape_white));
            /*Intent intent = new Intent(getApplicationContext(), MainPage2.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            overridePendingTransition(0, 0);*/
        }
    }

}

