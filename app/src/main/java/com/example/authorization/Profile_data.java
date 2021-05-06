package com.example.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Profile_data extends AppCompatActivity {

    private String number;
    private EditText name;
    private EditText surname;
    private EditText middlename;
    private TextView date;
    private Button save;
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_data);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        number = (String) intent.getExtras().get("number");

        name = findViewById(R.id.Name);
        surname = findViewById(R.id.Surname);
        middlename = findViewById(R.id.middle_name);
        date = findViewById(R.id.date);
        save = findViewById(R.id.Save_data);
        back = findViewById(R.id.back_btn5);

        SQLiteDatabase usersDataBase = openOrCreateDatabase("users", MODE_PRIVATE, null);
        usersDataBase.execSQL("create table if not exists users\n" +
                "(\n" +
                "\tUserPhone varchar(1000), \n" +
                "\tUserDopInfo text, \n" +
                "\tUserName text, \n" +
                "\tUserSurname text, \n" +
                "\tUserBirthday varchar(1000), \n" +
                "\tUserPolis varchar(1000) \n" +
                ");");

        Cursor cper = usersDataBase.rawQuery("select * from users where UserPhone=?", new String[]{number});
        cper.moveToFirst();

        int UserNameIndex = cper.getColumnIndex("UserName");
        int UserBirthdayIndex = cper.getColumnIndex("UserBirthday");
        int UserSurnameIndex = cper.getColumnIndex("UserSurname");

        name.setText(cper.getString(UserNameIndex));
        surname.setText(cper.getString(UserSurnameIndex));
        date.setText(cper.getString(UserBirthdayIndex));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usersDataBase.execSQL("insert into users(UserName, UserSurname, UserBirthday) values ('"+name.getText()+"', '"+surname.getText()+"', '"+date.getText()+"')");
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
}