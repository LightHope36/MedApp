package com.example.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.ui.User;

import java.util.Calendar;
import java.util.Date;

public class Reg extends AppCompatActivity {

    private Button next;
    private Button back;
    private TextView birth;
    private EditText name;
    private EditText surname;
    private EditText polis;
    public String Username;
    public String Usersurname;
    public String Userbithday;
    public String Userpolis;
    private String Usermiddlename;
    public TextView error;
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

        Cursor cper = usersDataBase.rawQuery("select * from users where UserPhone=?", new String[]{number});
        cper.moveToFirst();

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
                        Userbithday = birth.getText().toString();
                        Userpolis = polis.getText().toString();
                        Usermiddlename = middlename.getText().toString();


                        usersDataBase.execSQL("insert into users(UserPhone, UserName,UserSurname, UserBirthday, UserPolis, UserMiddlename) values('" + number + "', '" + Username + "','" + Usersurname + "','" + Userbithday + "','" + Userpolis + "', '" + Usermiddlename + "')");
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
                        Userbithday = birth.getText().toString();
                        Usermiddlename = middlename.getText().toString();
                        String zero = "";

                        usersDataBase.execSQL("insert into users(UserPhone, UserName,UserSurname, UserBirthday, UserPolis, UserMiddlename) values('" + number + "', '" + Username + "','" + Usersurname + "','" + Userbithday + "', null, '" + Usermiddlename + "')");
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

            try {
                int UserNameIndex = cper.getColumnIndex("UserName");
                int UserBirthdayIndex = cper.getColumnIndex("UserBirthday");
                int UserSurnameIndex = cper.getColumnIndex("UserSurname");
                int UserMiddlenameIndex = cper.getColumnIndex("UserMiddlename");
                int UserPolisIndex = cper.getColumnIndex("UserPolis");

                name.setText(cper.getString(UserNameIndex));
                surname.setText(cper.getString(UserSurnameIndex));
                birth.setText(cper.getString(UserBirthdayIndex));
                middlename.setText(cper.getString(UserMiddlenameIndex));
                polis.setText(cper.getString(UserPolisIndex));
            } catch (Exception e){
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();

            }

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
                        usersDataBase.update("users", values, "UserPhone=?", new String[]{number});

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

}