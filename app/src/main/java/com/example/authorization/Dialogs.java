package com.example.authorization;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Dialogs extends AppCompatActivity {

    private ListView listView;
    private ImageView cancel;
    private ImageView search;
    private EditText input;
    private ConstraintLayout cs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogs);

        listView = findViewById(R.id.list_of_persons);
        search = findViewById(R.id.search_dial);
        cancel = findViewById(R.id.search_cancel_dialogs);
        cs = findViewById(R.id.search_cs_dialogs);


        List<Person> persons = new ArrayList<>();


        for (int i=1; i<10; i++){
            Person person = new Person();
            person.setLastmessage("text"+i);
            person.setName("User"+i);
            person.setMessageTime(new Date().getTime());
            person.setAvatar("ic_profile_1");
            persons.add(person);
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cs.setVisibility(View.VISIBLE);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cs.setVisibility(View.INVISIBLE);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Chat.class);
                intent.putExtra("person", persons.get(position));
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        PersonAdapter adapter = new PersonAdapter(getApplicationContext(), R.layout.person, persons);
        listView.setAdapter(adapter);


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

            ImageView imageView = convertView.findViewById(R.id.profile);
            int imageId = getContext().getResources().getIdentifier(person.getAvatar(), "drawable", getContext().getPackageName());
            imageView.setImageResource(imageId);

            PersonHolder holder = new PersonHolder();
            holder.UserName = convertView.findViewById(R.id.user);
            holder.UserText = convertView.findViewById(R.id.last_message_text);


            holder.UserName.setText(person.getName());
            holder.UserText.setText(person.getLastmessage());

            convertView.setTag(holder);


            return convertView;
        }
    }

    private static class PersonHolder {
        public TextView UserName;
        public TextView UserText;
    }
}