package com.example.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.ui.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import io.fabric.sdk.android.services.concurrency.AsyncTask;

public class Reg extends AppCompatActivity {

    private Button next;
    private Button back;
    private TextView birth;
    private EditText name;
    private EditText surname;
    private EditText polis;
    public String Username;
    public String Usersurname;
    private Date Userbithday;
    private long Userpolis=123123;
    public String Usermiddlename;
    private TextView error;
    private EditText middlename;
    private String from;
    private String number;

    Calendar dateAndTime=Calendar.getInstance();

    Date currentDate = new Date();

    private int cYear=dateAndTime.get(Calendar.YEAR);
    private int cMonth=dateAndTime.get(Calendar.MONTH);
    private int cDay=dateAndTime.get(Calendar.DAY_OF_MONTH);

    private int Year=dateAndTime.get(Calendar.YEAR);
    private int Month=dateAndTime.get(Calendar.MONTH);
    private int Day=dateAndTime.get(Calendar.DAY_OF_MONTH);

    String url = "jdbc:mysql://server23.hosting.reg.ru/u0597423_medclick.kvantorium69?characterEncoding=utf-8";
    String username = "u0597423_medclic";
    String password = "kvantoriummagda";

    java.sql.Timestamp sqlDate = new java.sql.Timestamp(dateAndTime.getTime().getTime());
    DateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());


    private String OpenTable = ("create table if not exists client (\n" +
            "\tclientid INT PRIMARY KEY AUTO_INCREMENT, \n" +
            "\tname varchar(15), \n" +
            "\tsurname varchar(15), \n" +
            "\tpatronymic varchar(15), \n" +
            "\tmedical_policy int, \n" +
            "\tPhone_number int, \n" +
            "\tsnils int, \n" +
            "\tEmail varchar(45), \n" +
            "\tdate_of_birth datetime, \n" +
            "\tmedical_history int, \n" +
            "\tcompanies_providing_medical_insurance int )\n");

    private String CreateUser="insert into client(Phone_number, name, surname, date_of_birth, medical_policy, patronymic, snils, Email) values('" + number + "', '" + Username + "','" + Usersurname + "','" + sqlDate + "','" + Userpolis + "', '" + Usermiddlename + "', '12345', 'email@mail.ru' )";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        next = findViewById(R.id.next_btn5);
        back = findViewById(R.id.back_btn5);
        birth = findViewById(R.id.birthday);
        name = findViewById(R.id.Name);
        surname = findViewById(R.id.Surname);
        polis = findViewById(R.id.Polis);
        error = findViewById(R.id.error);
        middlename = findViewById(R.id.middle_name);

        Intent intent = getIntent();
        number = (String) intent.getExtras().get("number");
        from = (String) intent.getExtras().get("from");

        try{
            Log.e("number", number);
        }
        catch(Exception e){
            Log.e("error", e.getMessage());
        }

//        SQLiteDatabase usersDataBase = openOrCreateDatabase("users", MODE_PRIVATE, null);
//        usersDataBase.execSQL("create table if not exists users\n" +
//                "(\n" +
//                "\tUserPhone varchar(10), \n" +
//                "\tUserMiddlename text, \n" +
//                "\tUserDopInfo text, \n" +
//                "\tUserName text, \n" +
//                "\tUserSurname text, \n" +
//                "\tUserBirthday varchar(1000), \n" +
//                "\tUserPolis varchar(1000) \n" +
//                ");");
//
//        Cursor cper = usersDataBase.rawQuery("select * from users where UserPhone=?", new String[]{number});
//        cper.moveToFirst();

        if(from.equals("auth4")) {

            next.setText("Далее");
            next.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_next, 0);

            SQLiteDatabase lastuser = openOrCreateDatabase("lastuser", MODE_PRIVATE, null);
            lastuser.execSQL("create table if not exists lastuser\n" +
                    "(\n" +
                    "\tUserPhone varchar(10) \n" +
                    ");");

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(cYear-Year>18 || (cYear-Year==18 && (cMonth-Month>0 || (cMonth-Month==0 && cDay-Day>=0))))){
                        Toast.makeText(getApplicationContext(), "Вам должно быть 18+ лет", Toast.LENGTH_LONG).show();
                        System.out.println(Year);
                        System.out.println(cYear);
                        System.out.println(cYear-Year);
                        Year=dateAndTime.get(Calendar.YEAR);
                        Month=dateAndTime.get(Calendar.MONTH);
                        Day=dateAndTime.get(Calendar.DAY_OF_MONTH);
                    }
                    else if (!name.getText().toString().equals("") && !surname.getText().toString().equals("") && !birth.getText().toString().equals("") && !polis.getText().toString().equals("") && !middlename.getText().toString().equals("")) {
                        Username = name.getText().toString();
                        Usersurname = surname.getText().toString();
                        try {
                            Userbithday = dateAndTime.getTime();
                            sqlDate = new java.sql.Timestamp(dateAndTime.getTime().getTime());
                            Userpolis = Long.parseLong(polis.getText().toString());
                            Log.e("date", String.valueOf(sqlDate));
                            Log.e("polis", String.valueOf(Userpolis));
                        } catch (Exception e) {
                            Log.e("error", e.getMessage());
                        }
                        Usermiddlename = middlename.getText().toString();


                        new CreateUser().execute();
                        lastuser.execSQL("insert into lastuser (UserPhone) values ('" + number + "')");

                        Intent intent = new Intent(getApplicationContext(), MainPage2.class);

                        intent.putExtra("number", number);
                        intent.putExtra("Username", Username);
                        intent.putExtra("Usersurname", Usersurname);
                        intent.putExtra("Userbithday", Userbithday);
                        intent.putExtra("Userpolis", Userpolis);

                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    } else if (!name.getText().toString().equals("") && !surname.getText().toString().equals("") && !birth.getText().toString().equals("") && !middlename.getText().toString().equals("")) {

                        Username = name.getText().toString();
                        Usersurname = surname.getText().toString();
                        Userbithday = (Date) birth.getText();
                        Usermiddlename = middlename.getText().toString();
                        String zero = "";

//                        usersDataBase.execSQL("insert into users(UserPhone, UserName,UserSurname, UserBirthday, UserPolis, UserMiddlename) values('" + number + "', '" + Username + "','" + Usersurname + "','" + Userbithday + "', null, '" + Usermiddlename + "')");
                        lastuser.execSQL("insert into lastuser (UserPhone) values ('" + number + "')");

                        Intent intent = new Intent(getApplicationContext(), MainPage2.class);

                        intent.putExtra("number", number);

                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    } else {
                        error.setText(getString(R.string.Не_все_поля));
                        //Toast.makeText(getApplicationContext(), "Введены не все обязательные данные", Toast.LENGTH_LONG).show();
                    }
                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), auth3.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            });

        } else {

            next.setText("Сохранить");
            next.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);

//            try {
//                int UserNameIndex = cper.getColumnIndex("UserName");
//                int UserBirthdayIndex = cper.getColumnIndex("UserBirthday");
//                int UserSurnameIndex = cper.getColumnIndex("UserSurname");
//                int UserMiddlenameIndex = cper.getColumnIndex("UserMiddlename");
//                int UserPolisIndex = cper.getColumnIndex("UserPolis");
//
//                name.setText(cper.getString(UserNameIndex));
//                surname.setText(cper.getString(UserSurnameIndex));
//                birth.setText(cper.getString(UserBirthdayIndex));
//                middlename.setText(cper.getString(UserMiddlenameIndex));
//                polis.setText(cper.getString(UserPolisIndex));
//            } catch (Exception e){
//                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
//
//            }

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(cYear-Year>18 || (cYear-Year==18 && (cMonth-Month>0 || (cMonth-Month==0 && cDay-Day>=0))))){
                        Toast.makeText(getApplicationContext(), "Вам должно быть 18+ лет", Toast.LENGTH_LONG).show();
                        System.out.println(Year);
                        System.out.println(cYear);
                        System.out.println(cYear-Year);
                        Year=dateAndTime.get(Calendar.YEAR);
                        Month=dateAndTime.get(Calendar.MONTH);
                        Day=dateAndTime.get(Calendar.DAY_OF_MONTH);
                    }
                    else if( !name.getText().toString().equals("") && !surname.getText().toString().equals("") && !birth.getText().toString().equals("") && !middlename.getText().toString().equals("")) {
                        ContentValues values = new ContentValues();
                        values.put("UserName", name.getText().toString());
                        values.put("UserSurname", surname.getText().toString());
                        values.put("UserBirthday", birth.getText().toString());
                        values.put("UserMiddlename", middlename.getText().toString());
                        values.put("UserPolis", polis.getText().toString());
//                        usersDataBase.update("users", values, "UserPhone=?", new String[]{number});

                        Intent intent = new Intent(getApplicationContext(), Profile.class);
                        intent.putExtra("number", number);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    }
                    else{
                        error.setText(getString(R.string.Не_все_поля));
                    }
                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Profile.class);
                    intent.putExtra("number", number);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            });

        }

        birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(birth);
            }
        });

    }

    public void setDate(View v) {
        if(Year==cYear) {
            new DatePickerDialog(this, d,
                    Year-18,
                    Month,
                    Day)
                    .show();
        }else{
            new DatePickerDialog(this, d,
                    Year,
                    Month,
                    Day)
                    .show();
        }
    }

    private void setInitialDateTime() {
        birth.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            Year=year;
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            Month=monthOfYear;
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Day=dayOfMonth;
            dateAndTime= new GregorianCalendar(year, monthOfYear, dayOfMonth);
            setInitialDateTime();
        }
    };
    public void onBackPressed(){
        if(from.equals("auth4")) {
            Intent intent = new Intent(getApplicationContext(), auth3.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            overridePendingTransition(0, 0);
        } else{
            Intent intent = new Intent(getApplicationContext(), Profile.class);
            intent.putExtra("number", number);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }


    class CreateUser extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try{
                try (Connection conn = DriverManager.getConnection(url, username, password)){
                    Statement statement = conn.createStatement();
                    // создание таблицы
                    final String name = Username;
                    String CreateUser="insert into client(Phone_number, name, surname, date_of_birth, medical_policy, patronymic, snils, Email) values('" + number + "', '" + name + "','" + Usersurname + "','" + sqlDate + "','" + Userpolis + "', '" + Usermiddlename + "', '12345', 'email@mail.ru')";
                    statement.executeUpdate(OpenTable);
                    int call = statement.executeUpdate(CreateUser);
                    Log.e("Connection", "Created");
                    Log.e("call", "" + call + "");

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
}