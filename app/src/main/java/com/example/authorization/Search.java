package com.example.authorization;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import io.fabric.sdk.android.services.concurrency.AsyncTask;

public class Search extends AppCompatActivity {

    private ImageView back;
    private EditText search;
    private ListView listView;
    Cursor c;
    public int messageType;
    MessageAdapter adapter = new MessageAdapter(this);
    DateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    DateFormat dayAndMonthFormat = new SimpleDateFormat("d M", Locale.getDefault());
    DateFormat dayFormat = new SimpleDateFormat("d", Locale.getDefault());
    DateFormat monthFormat = new SimpleDateFormat("M", Locale.getDefault());
    String days[] = new String[]{"Января", "Февраля", "Марта", "Апреля", "Мая", "Июня", "Июля", "Августа", "Сентября", "Октября", "Ноября", "Декабря"};
    Date currentDate = new Date();
    String thisdate = dayAndMonthFormat.format(currentDate);
    String today = dayAndMonthFormat.format(currentDate);
    String text;

    private String number;


    private Connection conn;
    String url = "jdbc:mysql://server23.hosting.reg.ru/u0597423_medclick.kvantorium69?characterEncoding=utf-8";
    String username = "u0597423_medclic";
    String password = "kvantoriummagda";
    private List<Person> persons = new ArrayList<>();
    private PersonAdapter person_adapter;

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
        setContentView(R.layout.activity_search);

        back = findViewById(R.id.back_im_from_search);
        search = findViewById(R.id.search_input_chat);
        listView = findViewById(R.id.list_of_messages_in_search);
        new GetConnection().execute();

        Intent intent = getIntent();
        String type = (String) intent.getExtras().get("from");
        String number = (String) intent.getExtras().get("number");
        String user = number;
        List<Message> messages = new ArrayList<>();


        SQLiteDatabase VisibleMessagesDataBase = openOrCreateDatabase("VisibleMessagess", MODE_PRIVATE, null);
        VisibleMessagesDataBase.execSQL("create table if not exists VisibleMessagess\n" +
                "(\n" +
                "\tID INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "\tmessageUser varchar(100), \n" +
                "\tmessageSender varchar(10), \n" +
                "\tmessageText text, \n" +
                "\tmessageImage blob, \n" +
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

        Cursor cper = usersDataBase.rawQuery("select * from users where UserPhone!=?", new String[]{number});
        cper.moveToFirst();

        if(type.equals("chat")) {
            Person person = (Person) intent.getExtras().get("person");
            String taker = person.getNumber();
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), Chat.class);
                    intent.putExtra("messageId", messages.get(position).getMessageId());
                    intent.putExtra("person", person);
                    intent.putExtra("number", number);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            });

            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    String text = search.getText().toString();


                    if (!text.equals("")) {

                        adapter.clear();
                        messages.clear();

                        c = VisibleMessagesDataBase.rawQuery("select * from VisibleMessagess where messageUser=? and ((messageTaker=? and messageSender=?) or (messageSender=? and messageTaker=?)) and (messageText like '%' || ? || '%')", new String[]{user, taker, number, taker, number, text});

                        c.moveToFirst();

                        if(c.moveToFirst()) {
                            int messageTimeIndex = c.getColumnIndex("messageTime");
                            String timeText = c.getString(messageTimeIndex);
                            String dateText = "";
                            String day = "";
                            String month = "1";
                            String timeTextInMessage = "";
                            try {
                                Date timeTextDate = fullDateFormat.parse(timeText);
                                dateText = dayAndMonthFormat.format(timeTextDate);
                                day = dayFormat.format(timeTextDate);
                                month = monthFormat.format(timeTextDate);
                                timeTextInMessage = timeFormat.format(timeTextDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            Message message = new Message();
                            if (!dateText.equals(today)) {
                                day += " " + days[Integer.parseInt(month) - 1];
                                message.setMessageText(day);
                            } else {
                                message.setMessageText("Сегодня");
                            }
                            message.setMessageType(0);
                            adapter.add(message);
                            messages.add(message);
                        }

                        int messageSenderIndex = c.getColumnIndex("messageSender");
                        int messageTextIndex = c.getColumnIndex("messageText");
                        int messageTimeIndex = c.getColumnIndex("messageTime");
                        int messageIDIndex = c.getColumnIndex("ID");



                        while (!c.isAfterLast()) {
                            String timeText = c.getString(messageTimeIndex);
                            String dateText = "";
                            String day = "";
                            String month = "1";
                            String timeTextInMessage = "";
                            System.out.println(timeText);

                            try {
                                Date timeTextDate = fullDateFormat.parse(timeText);
                                dateText = dayAndMonthFormat.format(timeTextDate);
                                day = dayFormat.format(timeTextDate);
                                month = monthFormat.format(timeTextDate);
                                timeTextInMessage = timeFormat.format(timeTextDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if(!dateText.equals(thisdate)){
                                Message message = new Message();
                                if(!dateText.equals(today)) {
                                    day += " " + days[Integer.parseInt(month) - 1];
                                    message.setMessageText(day);
                                } else{
                                    message.setMessageText("Сегодня");
                                }
                                message.setMessageType(0);
                                adapter.add(message);
                                messages.add(message);
                                thisdate = dateText;
                            }

                            Cursor cper = usersDataBase.rawQuery("select * from users where UserPhone=?", new String[]{c.getString(messageSenderIndex)});
                            cper.moveToFirst();

                            int UserNameIndex = cper.getColumnIndex("UserName");
                            int UserSurnameIndex = cper.getColumnIndex("UserSurname");
                            String taker_text = cper.getString(UserNameIndex) + " " + cper.getString(UserSurnameIndex);

                            Message message = new Message();



                            if(c.getString(messageSenderIndex).equals(user)){
                                messageType=1;
                                message.setMessageUser("You");
                            }
                            else {
                                messageType=2;
                                message.setMessageUser(taker_text);
                            }

                            timeText = c.getString(messageTimeIndex);
                            Date timeTextDate = null;
                            try {
                                timeTextDate = fullDateFormat.parse(timeText);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            timeTextInMessage = timeFormat.format(timeTextDate);

                            message.setMessageTime(timeTextInMessage);

                            message.setMessageId(c.getLong(messageIDIndex));
                            message.setMessageType(messageType);
                            message.setMessageText(c.getString(messageTextIndex));
                            adapter.add(message);
                            messages.add(message);
                            c.moveToNext();

                        }
                    }
                    else {
                        adapter.clear();
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        } else{

            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    person_adapter = new PersonAdapter(getApplicationContext(), R.layout.person, persons);
                    text = search.getText().toString();
                    listView.setAdapter(person_adapter);
                    persons.clear();

                    if (!text.equals("")) {

                        person_adapter.clear();
                        try {
                            persons = new GetUsers().execute().get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }


                        }
                    else {
                        person_adapter.clear();
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search.super.finish();
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
                String getUsers = "select * from client where Phone_number!=" + number+ " and ((name like '%' || " + text + " || '%') or  (surname like '%' || " + text + " || '%'))";
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

    private static class PersonHolder {
        public TextView UserName;
        public TextView UserText;
        public ImageView imageView;
        public TextView LastmessageTime;
    }


}