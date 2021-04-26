package com.example.authorization;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import android.widget.ImageView;
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

        listView = findViewById(R.id.list_of_professions);
        filter = findViewById(R.id.filter);
        top = findViewById(R.id.top_doctors);
        professions = findViewById(R.id.professions_text);
        doctors_tv = findViewById(R.id.doctors_text);

        List<Doctor> doctors = new ArrayList<>();
        List<Person> persons = new ArrayList<>();

        DoctorAdapter docadapter = new DoctorAdapter(getApplicationContext(), R.layout.profession_card, doctors);
        PersonAdapter personadapter = new PersonAdapter(getApplicationContext(), R.layout.person, persons);

        listView.setAdapter(docadapter);

        for (int i=0; i<20; i++){
            Doctor doctor = new Doctor();
            doctor.setText("Специальность " + (i+1));
            docadapter.add(doctor);
        }

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                count++;
                if(count==1) {
                    listView.setAdapter(personadapter);
                    for (int i = 0; i < 5; i++) {
                        Person person = new Person();
                        person.setName("Имя Фамилия " + (i+1));
                        person.setAvatar("ic_profile_1");
                        person.setDopinfo("This is dop info");
                        person.setPrice("price");
                        personadapter.add(person);
                    }
                }
                else{

                }
            }
        });

        professions.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                professions.setBackground(getDrawable(R.drawable.back_text));
                doctors_tv.setBackground(getDrawable(R.drawable.flow_shape_white));
                docadapter.clear();
                listView.setAdapter(docadapter);
                for (int i=0; i<20; i++){
                    Doctor doctor = new Doctor();
                    doctor.setText("Специальность " + (i+1));
                    docadapter.add(doctor);
                }
            }
        });

        doctors_tv.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                doctors_tv.setBackground(getDrawable(R.drawable.back_text));
                professions.setBackground(getDrawable(R.drawable.flow_shape_white));
                personadapter.clear();
                listView.setAdapter(personadapter);
                for (int i = 0; i < 5; i++) {
                    Person person = new Person();
                    person.setName("Имя Фамилия " + (i+1));
                    person.setAvatar("ic_profile_1");
                    person.setDopinfo("This is dop info");
                    person.setPrice("price");
                    personadapter.add(person);
                }
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
                }
                else{
                    int margin112inDp = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 112, getResources().getDisplayMetrics());
                    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams
                            (ConstraintLayout.LayoutParams.MATCH_PARENT, margin112inDp);
                    layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
                    top.setLayoutParams(layoutParams);
                }
            }
        });
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

    private static class PersonAdapter extends ArrayAdapter<Person> {

        public PersonAdapter(@NonNull Context context, int resource, @NonNull List<Person> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Person person = getItem(position);
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.person, null);



            PersonHolder holder = new PersonHolder();
            holder.UserName = convertView.findViewById(R.id.user);
            holder.UserText = convertView.findViewById(R.id.dopinfo_text);
            holder.imageView = convertView.findViewById(R.id.profile);
            holder.LastmessageTime = convertView.findViewById(R.id.last_message_time);

            int imageId = getContext().getResources().getIdentifier(person.getAvatar(), "drawable", getContext().getPackageName());

            holder.imageView.setImageResource(imageId);
            holder.UserName.setText(person.getName());
            holder.UserText.setText(person.getDopinfo());
            holder.LastmessageTime.setText(person.getMessageTime());

            convertView.setTag(holder);


            return convertView;
        }
    }

    private static class PersonHolder {
        public TextView UserName;
        public TextView UserText;
        public ImageView imageView;
        public TextView LastmessageTime;
    }

}