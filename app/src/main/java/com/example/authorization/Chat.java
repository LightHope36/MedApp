package com.example.authorization;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Chat extends AppCompatActivity {

    private ListView listView;
    private ImageView back;
    private ImageView app;
    private ImageView menu;
    private ImageView avatar;
    private TextView name;
    private LinearLayout cs;
    private int count;
    public int count2=1;
    public int messageType;
    private ImageView send;
    private EditText input;
    private ImageView mic;
    private ConstraintLayout bot;
    private ConstraintLayout details;
    private ConstraintLayout delete_chat;
    private ImageView add_image;
    static final int GALLERY_REQUEST = 1;
    private ImageView search;
    private ConstraintLayout search_open;
    private ConstraintLayout layout;
    private ConstraintLayout vlojenia;
    private ConstraintLayout add_to_contacts;
    private long id=0;
    private int i=0;
    private int n=0;
    private String number;
    private String user;
    private ImageView coffee;
    private TextView empty;
    Bitmap bitmap = null;
    public String Signature;
    SQLiteDatabase VisibleMessagesDataBase;
    MessageAdapter adapter = new MessageAdapter (this);
    DateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    DateFormat dayAndMonthFormat = new SimpleDateFormat("d M", Locale.getDefault());
    DateFormat dayFormat = new SimpleDateFormat("d", Locale.getDefault());
    DateFormat monthFormat = new SimpleDateFormat("M", Locale.getDefault());
    String days[] = new String[]{"Января", "Февраля", "Марта", "Апреля", "Мая", "Июня", "Июля", "Августа", "Сентября", "Октября", "Ноября", "Декабря"};
  //  String url = "https://server23.hosting.reg.ru/phpmyadmin/db_structure.php?db=u0597423_medclick.kvantorium69";
    //String username = "u0597423_medclic";
   // String password = "kvantoriummagda";



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        app = findViewById(R.id.app);
        back = findViewById(R.id.back_im);
        menu = findViewById(R.id.menu);
        cs = findViewById(R.id.menu_chata);
        count = 0;
        count2 = 0;
        listView = findViewById(R.id.list_of_messages);
        avatar = findViewById(R.id.avatar);
        name = findViewById(R.id.UserName);
        send = findViewById(R.id.send);
        input = findViewById(R.id.input);
        mic = findViewById(R.id.mic);
        bot = findViewById(R.id.bot);
        details = findViewById(R.id.details);
        add_image = findViewById(R.id.add_image);
        search_open = findViewById(R.id.search_cs_chat_open);
        layout = findViewById(R.id.clicker);
        vlojenia = findViewById(R.id.vlojenia);
        coffee = findViewById(R.id.coffee_in_chat);
        empty = findViewById(R.id.empty_in_chat);
        String url = "jdbc:mysql://server23.hosting.reg.ru/phpmyadmin/u0597423_medclick.kvantorium69";
        String username = "u0597423_medclic";
        String password = "kvantoriummagda";
        //Connection conn;


            //conn = DriverManager.getConnection("https://server23.hosting.reg.ru/phpmyadmin/db_structure.php?db=u0597423_medclick.kvantorium69","u0597423_medclic","kvantoriummagda");
          //  Toast.makeText(getApplicationContext(), "Connection succesfull!", Toast.LENGTH_LONG).show();
//        try {
////            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
//            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
//            try (Connection conn = DriverManager.getConnection(url, username, password)) {
//                Toast.makeText(getApplicationContext(), "Connection succesfull!", Toast.LENGTH_LONG).show();
//            } catch (Exception e) {
////                Toast.makeText(getApplicationContext(), "Connection failed...", Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        }catch (Exception e){
//
//        }



        Intent intent = getIntent();
        Person person = (Person) intent.getExtras().get("person");
        number = (String) intent.getExtras().get("number");
        user = number;


        try{
            id = (long) intent.getExtras().get("messageId");

        } catch (Exception e){}

        cs.setVisibility(View.INVISIBLE);

        if(person != null){
            int imageId = getResources().getIdentifier(person.getAvatar(), "drawable", getPackageName());
            avatar.setImageResource(imageId);

            name.setText(person.getName());
        }


        List<Message> messages = new ArrayList<>();

        listView.setAdapter(adapter);

        String taker = person.getNumber();


        VisibleMessagesDataBase = openOrCreateDatabase("VisibleMessagess", MODE_PRIVATE, null);
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
//        getApplicationContext().deleteDatabase("VisibleMessagess");

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

        Cursor c = VisibleMessagesDataBase.rawQuery("select * from VisibleMessagess where messageUser = ? and ((messageTaker=? and messageSender=?) or (messageSender=? and messageTaker=?))", new String[] {user, taker, number, taker, number});
        c.moveToFirst();

        Date currentDate = new Date();
        String thisdate = dayAndMonthFormat.format(currentDate);
        String today = dayAndMonthFormat.format(currentDate);
        while (!c.isAfterLast()) {
            int messageTextIndex = c.getColumnIndex("messageText");
            int messageTimeIndex = c.getColumnIndex("messageTime");
            int messageSenderIndex = c.getColumnIndex("messageSender");
            int messageImageIndex = c.getColumnIndex("messageImage");
            int messageIDIndex = c.getColumnIndex("ID");

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
                thisdate = dateText;
            }

            Message message = new Message();

            if ((c.getString(messageSenderIndex)).equals(number)) {

                if (!(c.isNull(messageImageIndex))) {
                    byte[] bytesImage = c.getBlob(messageImageIndex);
                    Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);

                    message.setMessageUser("You");
                    message.setMessageType(3);
                    message.setImage(bitmapImage);
                    message.setMessageTime(timeText);
                    adapter.add(message);
                }else {
                    message.setMessageId(c.getLong(messageIDIndex));
                    message.setMessageUser("You");
                    message.setMessageType(1);
                    message.setMessageText(c.getString(messageTextIndex));
                    message.setMessageTime(timeTextInMessage);
                    adapter.add(message);
                }


                if(c.getLong(messageIDIndex)==id){
                    n=i;
                }
                i++;

            }
            else{
                message.setMessageId(c.getLong(messageIDIndex));
                message.setMessageUser(person.getName());
                message.setMessageText(c.getString(messageTextIndex));
                message.setMessageTime(timeTextInMessage);
                message.setMessageType(2);

                if(c.getLong(messageIDIndex)==id){
                    n=i;
                }
                i++;

                adapter.add(message);
            }
            c.moveToNext();
        }

        if (n == 0) {
            n=i;
        }
        listView.smoothScrollToPosition(n);
        if(adapter.isEmpty()){
            coffee.setVisibility(View.VISIBLE);
            empty.setVisibility(View.VISIBLE);
        }
        else{
            coffee.setVisibility(View.INVISIBLE);
            empty.setVisibility(View.INVISIBLE);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (count%2==1) {
                    count++;
                    cs.setVisibility(View.INVISIBLE);
                }
            }
        });

        vlojenia.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                        vlojenia.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.elm_view_grey));
                        break;
                    case MotionEvent.ACTION_MOVE: // движение
                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                        vlojenia.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.elm_view));
                    case MotionEvent.ACTION_CANCEL:
                        break;
                }
                return true;
            }
        });


        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count2++;
                if (count2%2==1) {
                    int margin135inDp = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 135, getResources().getDisplayMetrics());
                    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams
                            (ConstraintLayout.LayoutParams.MATCH_PARENT, margin135inDp);
                    layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
                    bot.setLayoutParams(layoutParams);
                    details.setVisibility(View.VISIBLE);
                    app.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_attachment_2));
                }
                else{
                    int margin62inDp = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 62, getResources().getDisplayMetrics());
                    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams
                            (ConstraintLayout.LayoutParams.MATCH_PARENT, margin62inDp);
                    layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
                    bot.setLayoutParams(layoutParams);
                    details.setVisibility(View.INVISIBLE);
                    app.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_attachment_1));
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dialogs.class);
                intent.putExtra("number", number);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if (count%2==1) {
                    cs.setVisibility(View.VISIBLE);
                }
                else{
                    cs.setVisibility(View.INVISIBLE);
                }
            }
        });


        search_open.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                        search_open.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.elm_view_grey));
                        break;
                    case MotionEvent.ACTION_MOVE: // движение
                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                        search_open.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.elm_view));
                        count++;
                        cs.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(getApplicationContext(), Search.class);
                        intent.putExtra("person", person);
                        intent.putExtra("number", number);
                        intent.putExtra("from", "chat");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    case MotionEvent.ACTION_CANCEL:
                        break;
                }
                return true;
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input.getText().toString().equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Вы ничего не ввели", Toast.LENGTH_LONG);
                    toast.show();
                }
                else{

                    Date currentDate = new Date();
                    String timeText = fullDateFormat.format(currentDate);
                    Date timeTextDate = null;
                    try {
                        timeTextDate = fullDateFormat.parse(timeText);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String timeTextInMessage = timeFormat.format(timeTextDate);


                    String text = input.getText().toString();

                    input.getText().clear();


                    VisibleMessagesDataBase.execSQL("insert into VisibleMessagess(messageUser, messageSender,messageText, messageTaker, messageTime) values('"+user+"','"+number+"','"+text+"','"+taker+"','"+timeText+"')");
                    VisibleMessagesDataBase.execSQL("insert into VisibleMessagess(messageUser, messageSender,messageText, messageTaker, messageTime) values('"+taker+"','"+number+"','"+text+"','"+taker+"','"+timeText+"')");


                    Cursor cmes = VisibleMessagesDataBase.rawQuery("select * from VisibleMessagess", null);
                    cmes.moveToLast();

                    int messageIDIndex = cmes.getColumnIndex("ID");

                    coffee.setVisibility(View.INVISIBLE);
                    empty.setVisibility(View.INVISIBLE);

                    messageType=1;
                    Message message = new Message();
                    message.setMessageId(cmes.getLong(messageIDIndex));
                    message.setMessageText(text);
                    message.setMessageType(1);
                    message.setMessageTime(timeTextInMessage);
                    adapter.add(message);
                    listView.setSelection(listView.getCount() - 1);


                }
                i++;
                n=i;
                listView.smoothScrollToPosition(n);
            }
        });

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(input.getText().toString().equals("")){
                    mic.setVisibility(View.VISIBLE);
                    send.setVisibility(View.INVISIBLE);
                }
                else{
                    mic.setVisibility(View.INVISIBLE);
                    send.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);



            }
        });


    }

    public boolean onTouchEvent(MotionEvent event) {
        if (count%2==1) {
            count++;
            cs.setVisibility(View.INVISIBLE);
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        Intent intent = getIntent();
                        Person person = (Person) intent.getExtras().get("person");
                        String taker = person.getNumber();

                        Date currentDate = new Date();
                        String timeText = fullDateFormat.format(currentDate);

                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);

                        Message message = new Message();
                        message.setMessageUser("You");
                        message.setMessageType(3);
                        message.setImage(bitmap);
                        message.setMessageTime(timeText);
                        adapter.add(message);
                        listView.setSelection(listView.getCount() - 1);

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
                        byte[] bytesImage = byteArrayOutputStream.toByteArray();

                        ContentValues contentValues = new ContentValues();
                        contentValues.put("messageUser", user);
                        contentValues.put("messageSender", number);
                        contentValues.put("messageImage", bytesImage);
                        contentValues.put("messageTaker", taker);
                        contentValues.put("messageTime", timeText);

                        try {
                            VisibleMessagesDataBase.insert("VisibleMessagess", null, contentValues);
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }


    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Dialogs.class);
        intent.putExtra("number", number);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}