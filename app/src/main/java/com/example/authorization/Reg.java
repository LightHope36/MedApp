package com.example.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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
    public TextView error;


    Calendar dateAndTime=Calendar.getInstance();



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

        Intent intent = getIntent();
        String number = (String) intent.getExtras().get("number");

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("users", MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("create table if not exists users\n" +
                "(\n" +
                "\tUserPhone varchar(10), \n" +
                "\tUserName varchar(1000), \n" +
                "\tUserSurname varchar(1000), \n" +
                "\tUserBirthday varchar(1000), \n" +
                "\tUserPolis varchar(1000) \n" +
                ");");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().equals("") && !surname.getText().toString().equals("") && !birth.getText().toString().equals("") && !polis.getText().toString().equals("")) {
                    Username = name.getText().toString();
                    Usersurname = surname.getText().toString();
                    Userbithday = birth.getText().toString();
                    Userpolis = polis.getText().toString();

                    sqLiteDatabase.execSQL("insert into users(UserPhone, UserName,UserSurname, UserBirthday, UserPolis) values('"+ number +"', '"+ Username +"','"+Usersurname+"','"+Userbithday+"','"+Userpolis+"')");


                    Intent intent = new Intent(getApplicationContext(), Dialogs.class);

                    intent.putExtra("number", number);
                    intent.putExtra("Username", Username);
                    intent.putExtra("Usersurname", Usersurname);
                    intent.putExtra("Userbithday", Userbithday);
                    intent.putExtra("Userpolis", Userpolis);

                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
                else if(!name.getText().toString().equals("") && !surname.getText().toString().equals("") && !birth.getText().toString().equals("")){

                    Username = name.getText().toString();
                    Usersurname = surname.getText().toString();
                    Userbithday = birth.getText().toString();

                    sqLiteDatabase.execSQL("insert into users(UserPhone, UserName,UserSurname, UserBirthday, UserPolis) values('"+ number +"', '"+ Username +"','"+Usersurname+"','"+Userbithday+"', null)");

                    Intent intent = new Intent(getApplicationContext(), Dialogs.class);

                    intent.putExtra("number", number);

                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
                else{
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
                overridePendingTransition(0,0);
            }
        });

        birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(birth);
            }
        });




    }

    public void setDate(View v) {
        new DatePickerDialog(this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void setInitialDateTime() {

        birth.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };


}