package com.example.authorization;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import io.fabric.sdk.android.services.concurrency.AsyncTask;

public class Dialogs extends AppCompatActivity {

    private ListView listView;
    private ImageView search;
    int i=0;
    private ImageView coffee;
    private TextView empty;
    private ConstraintLayout profile;
    DateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    DateFormat dayAndMonthFormat = new SimpleDateFormat("d M", Locale.getDefault());
    DateFormat dayFormat = new SimpleDateFormat("d", Locale.getDefault());
    DateFormat monthFormat = new SimpleDateFormat("M", Locale.getDefault());
    String days[] = new String[]{"Января", "Февраля", "Марта", "Апреля", "Мая", "Июня", "Июля", "Августа", "Сентября", "Октября", "Ноября", "Декабря"};
    Date currentDate = new Date();
    String thisdate = dayAndMonthFormat.format(currentDate);
    String today = dayAndMonthFormat.format(currentDate);
    private ConstraintLayout main;
    private TextView dialogs;
    private ConstraintLayout dial_cs;
    private String number;


    private Connection conn;
    String url = "jdbc:mysql://server23.hosting.reg.ru/u0597423_medclick.kvantorium69?characterEncoding=utf-8";
    String username = "u0597423_medclic";
    String password = "kvantoriummagda";
    private List<Person> persons = new ArrayList<>();
    private PersonAdapter adapter;

    private ResultSet cper;
    private Statement statement;
    private String OpenTable = ("create table if not exists client (\n" +
            "\tclientid INT PRIMARY KEY AUTO_INCREMENT, \n" +
            "\tname varchar(15), \n" +
            "\tsurname varchar(15), \n" +
            "\tpatronymic varchar(15), \n" +
            "\tmedical_policy varchar(16), \n" +
            "\tPhone_number int, \n" +
            "\tsnils int, \n" +
            "\tdate_of_birth datetime, \n" +
            "\tmedical_history int, \n" +
            "\tcompanies_providing_medical_insurance int )\n");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogs);

        new GetConnection().execute();
        new GetConnection().execute();
        listView = findViewById(R.id.list_of_persons);
        search = findViewById(R.id.search_dial);
        main = findViewById(R.id.main_cs_in_dial);
        coffee = findViewById(R.id.coffee_in_dial);
        empty = findViewById(R.id.empty_in_dial);
        profile = findViewById(R.id.profile_in_dialogs);
        dialogs = findViewById(R.id.textView23);
        dial_cs = findViewById(R.id.constraintLayout5);

        Intent intent = getIntent();

        number = (String) intent.getExtras().get("number");

        adapter = new PersonAdapter(getApplicationContext(), R.layout.person, persons);

//        getApplicationContext().deleteDatabase("users");
//        getApplicationContext().deleteDatabase("lastuser");
//        getApplicationContext().deleteDatabase("VisibleMessagess");
//        getApplicationContext().deleteDatabase("doctors");



        listView.setAdapter(adapter);

//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
//            Toast.makeText(getApplicationContext(), "Driver connected", Toast.LENGTH_SHORT).show();
//        }catch (Exception e ){
//            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//        }




        SQLiteDatabase VisibleMessagesDataBase = openOrCreateDatabase("VisibleMessagess", MODE_PRIVATE, null);
        VisibleMessagesDataBase.execSQL("create table if not exists VisibleMessagess\n" +
                "(\n" +
                "\tID INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "\tmessageUser varchar(100), \n" +
                "\tmessageSender varchar(10), \n" +
                "\tmessageText text, \n" +
                "\tmessageTaker varchar(10), \n" +
                "\tmessageTime varchar(100) \n" +
                ");");


        SQLiteDatabase usersDataBase = openOrCreateDatabase("users", MODE_PRIVATE, null);
        usersDataBase.execSQL("create table if not exists users\n" +
                "(\n" +
                "\tUserPhone varchar(10), \n" +
                "\tUserMiddlename text, \n" +
                "\tUserDopInfo text, \n" +
                "\tUserName text, \n" +
                "\tUserSurname text, \n" +
                "\tUserBirthday varchar(1000), \n" +
                "\tUserPolis varchar(1000) \n" +
                ");");


        try {
            persons = new GetUsers().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
//        Cursor cper = usersDataBase.rawQuery("select * from users where UserPhone!=?", new String[]{number});
//        cper.moveToFirst();

//        while (!cper.isAfterLast()) {
//
//            int UserNameIndex = cper.getColumnIndex("UserName");
//            int UserPhoneIndex = cper.getColumnIndex("UserPhone");
//            int UserSurnameIndex = cper.getColumnIndex("UserSurname");
//            String taker = cper.getString(UserPhoneIndex);
//            String taker_text = cper.getString(UserNameIndex) + " " + cper.getString(UserSurnameIndex);
//
//            try {
//                Cursor cmes = VisibleMessagesDataBase.rawQuery("select * from VisibleMessagess where messageUser = ? and (messageTaker=? and messageSender=?) or (messageSender=? and messageTaker=?)", new String[]{number, taker, number, taker, number});
//                cmes.moveToLast();
//
//                int messageUserIndex = cmes.getColumnIndex("messageUser");
//                int messageTextIndex = cmes.getColumnIndex("messageText");
//                int messageTimeIndex = cmes.getColumnIndex("messageTime");
//
//                Person person = new Person();
//                person.setNumber(cper.getString(UserPhoneIndex));
//                person.setDopinfo(cmes.getString(messageTextIndex));
//                person.setName(taker_text);
//                person.setMessageTime(cmes.getString(messageTimeIndex));
//                person.setAvatar("ic_profile_1");
//                persons.add(person);
//            }
//            catch (Exception e){
//                Person person = new Person();
//                person.setNumber(cper.getString(UserPhoneIndex));
//                person.setDopinfo("Нет сообщений");
//                person.setName(taker_text);
//                person.setMessageTime("");
//                person.setAvatar("ic_profile_1");
//                persons.add(person);
//            }
//            cper.moveToNext();
//        }
        if(adapter.isEmpty()){

            coffee.setVisibility(View.VISIBLE);
            empty.setVisibility(View.VISIBLE);
            search.setVisibility(View.INVISIBLE);
            dialogs.setVisibility(View.VISIBLE);
            dial_cs.setVisibility(View.INVISIBLE);
        }
        else{
            coffee.setVisibility(View.INVISIBLE);
            empty.setVisibility(View.INVISIBLE);
            search.setVisibility(View.VISIBLE);
            dialogs.setVisibility(View.INVISIBLE);
            dial_cs.setVisibility(View.VISIBLE);
        }

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainPage2.class);
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

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Search.class);
                intent.putExtra("number", number);
                intent.putExtra("from", "dialogs");
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


//        Collections.sort(persons, new SortPersons());
//        adapter.notifyDataSetChanged();
//        cper.moveToFirst();
//
//        while (!cper.isAfterLast()) {
//            try {
//
//                Person person = persons.get(i);
//
//                String timeText = person.getMessageTime();
//                Date timeTextDate = fullDateFormat.parse(timeText);
//
//                String day = dayFormat.format(timeTextDate);
//                String dateText = dayAndMonthFormat.format(timeTextDate);
//                String month = monthFormat.format(timeTextDate);
//
//                if(!dateText.equals(today)){
//                    day += " " + days[Integer.parseInt(month) - 1];
//                    person.setMessageTime(day);
//                }
//                else{
//                    String timeTextInMessage = timeFormat.format(timeTextDate);
//
//                    person.setMessageTime(timeTextInMessage);
//                }
//
//            }
//            catch (Exception e){
//            }
//            cper.moveToNext();
//            i++;
//        }

    }


    private class SortPersons implements Comparator<Person> {
        @Override
        public int compare(Person o1, Person o2) {
            Date timeTextDate1 = null;
            Date timeTextDate2 = null;
            long time1=1;
            long time2 =0;
            try {
                timeTextDate1 = fullDateFormat.parse(o1.getMessageTime());
                timeTextDate2 = fullDateFormat.parse(o2.getMessageTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try{
                time1 = Long.valueOf(new SimpleDateFormat("yMdHm").format(timeTextDate1));
                time2 = Long.valueOf(new SimpleDateFormat("yMdHm").format(timeTextDate2));
            }
            catch (Exception e){
            }
            return (int)(time2-time1);
        }
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

    class GetConnection extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try{
                try {
                    conn = DriverManager.getConnection(url, username, password);
                    statement = conn.createStatement();
                    // создание таблицы
                    statement.executeUpdate(OpenTable);
                    Log.e("Connection", "CONNECTED");

                }
                catch(Exception ex){
                    Log.e("error", ex.getMessage());
                }
            }
            catch(Exception e){
                Log.e("error", e.getMessage());
            }

            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            Toast.makeText(getApplicationContext(), answerHTTP, Toast.LENGTH_LONG).show();
        }
    }

    class GetUsers extends AsyncTask<String, String, List<Person>> {

        @Override
        protected List<Person> doInBackground(String... params) {
            try {
                // создание таблицы
                String getUsers = "select * from client where Phone_number!=" + number;
                cper = statement.executeQuery(getUsers);
                cper.next();
                try {
                    while (true) {

                        int UserNameIndex = cper.findColumn("name");
                        int UserSurnameIndex = cper.findColumn("surname");
                        int UserPhoneIndex = cper.findColumn("Phone_number");
                        String taker = cper.getString(UserPhoneIndex);
                        String taker_text = cper.getString(UserNameIndex) + " " + cper.getString(UserSurnameIndex);

                        try {
//                            Cursor cmes = VisibleMessagesDataBase.rawQuery("select * from VisibleMessagess where messageUser = ? and (messageTaker=? and messageSender=?) or (messageSender=? and messageTaker=?)", new String[]{number, taker, number, taker, number});
//                            cmes.moveToLast();
//
//                            int messageUserIndex = cmes.getColumnIndex("messageUser");
//                            int messageTextIndex = cmes.getColumnIndex("messageText");
//                            int messageTimeIndex = cmes.getColumnIndex("messageTime");
                            Log.e("fgfds", "dsdf");
                            Person person = new Person();
                            person.setNumber(cper.getString(UserPhoneIndex));
//                            person.setDopinfo(cmes.getString(messageTextIndex));
                            person.setName(taker_text);
//                            person.setMessageTime(cmes.getString(messageTimeIndex));
                            person.setAvatar("ic_profile_1");
                            persons.add(person);
                        }
                        catch (Exception e){
                            Person person = new Person();
                            person.setNumber(cper.getString(UserPhoneIndex));
                            person.setDopinfo("Нет сообщений");
                            person.setName(taker_text);
                            person.setMessageTime("");
                            person.setAvatar("ic_profile_1");
                            persons.add(person);
                        }
                        if (cper.isLast()){
                            break;
                        }
                        else {
                            cper.next();
                        }

                    }

                    } catch (Exception e){
                        Log.e("error", e.getMessage());

                    }




                } catch (Exception e){
                    Log.e("error", e.getMessage());

                }

            return persons;
        }
    }

}