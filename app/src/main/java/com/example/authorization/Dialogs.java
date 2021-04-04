package com.example.authorization;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    private ImageView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogs);

        listView = findViewById(R.id.list_of_persons);
        search = findViewById(R.id.search_dial);

        Intent intent = getIntent();

        String number = (String) intent.getExtras().get("number");


        List<Person> persons = new ArrayList<>();

        PersonAdapter adapter = new PersonAdapter(getApplicationContext(), R.layout.person, persons);
        listView.setAdapter(adapter);


        SQLiteDatabase messagesDataBase = openOrCreateDatabase("messages", MODE_PRIVATE, null);
        messagesDataBase.execSQL("create table if not exists messages\n" +
                "(\n" +
                "\tmessageSender varchar(1000), \n" +
                "\tmessageUser varchar(1000), \n" +
                "\tmessageText varchar(3000), \n" +
                "\tmessageTaker varchar(1000), \n" +
                "\tmessageTime varchar(100) \n" +
                ");");

        SQLiteDatabase usersDataBase = openOrCreateDatabase("users", MODE_PRIVATE, null);
        usersDataBase.execSQL("create table if not exists users\n" +
                "(\n" +
                "\tUserPhone varchar(1000), \n" +
                "\tUserName varchar(1000), \n" +
                "\tUserSurname varchar(1000), \n" +
                "\tUserBirthday varchar(1000), \n" +
                "\tUserPolis varchar(1000) \n" +
                ");");

        Cursor cper = usersDataBase.rawQuery("select UserPhone, UserName, UserSurname, UserBirthday from users where UserPhone!=?", new String[]{number});
        cper.moveToFirst();

        while (!cper.isAfterLast()) {

            int UserNameIndex = cper.getColumnIndex("UserName");
            int UserSurnameIndex = cper.getColumnIndex("UserSurname");
            String taker = cper.getString(UserNameIndex) + " " + cper.getString(UserSurnameIndex);

            try {
                Cursor cmes = messagesDataBase.rawQuery("select messageUser, messageText, messageTaker, messageTime from messages where messageTaker=? and messageUser=?", new String[]{taker, number});
                cmes.moveToLast();


                int messageUserIndex = cmes.getColumnIndex("messageUser");
                int messageTextIndex = cmes.getColumnIndex("messageText");
                int messageTimeIndex = cmes.getColumnIndex("messageTime");

                Person person = new Person();
                person.setLastmessage(cmes.getString(messageTextIndex));
                person.setName(taker);
                person.setMessageTime(cmes.getString(messageTimeIndex));
                person.setAvatar("ic_profile_1");
                persons.add(person);
            }
            catch (Exception e){
                Person person = new Person();
                person.setLastmessage("Нет сообщений");
                person.setName(taker);
                person.setMessageTime("");
                person.setAvatar("ic_profile_1");
                persons.add(person);
            }
            cper.moveToNext();
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Search.class);
                intent.putExtra("person", "0");
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Chat.class);
                intent.putExtra("person", persons.get(position));
                intent.putExtra("number", number);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });




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
            holder.UserText = convertView.findViewById(R.id.last_message_text);
            holder.imageView = convertView.findViewById(R.id.profile);
            holder.LastmessageTime = convertView.findViewById(R.id.last_message_time);

            int imageId = getContext().getResources().getIdentifier(person.getAvatar(), "drawable", getContext().getPackageName());

            holder.imageView.setImageResource(imageId);
            holder.UserName.setText(person.getName());
            holder.UserText.setText(person.getLastmessage());
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