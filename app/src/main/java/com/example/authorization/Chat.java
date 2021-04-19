package com.example.authorization;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
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
    public String number;
    public String user;
    Bitmap bitmap = null;
    MessageAdapter adapter = new MessageAdapter (this);


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
        search = findViewById(R.id.search);
        search_open = findViewById(R.id.search_cs_chat_open);
        layout = findViewById(R.id.clicker);
        delete_chat = findViewById(R.id.delete_chat_cs);
        vlojenia = findViewById(R.id.vlojenia);
        add_to_contacts = findViewById(R.id.add_to_contacts_cs);




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

        SQLiteDatabase VisibleMessagesDataBase = openOrCreateDatabase("VisibleMessagess", MODE_PRIVATE, null);
        VisibleMessagesDataBase.execSQL("create table if not exists VisibleMessagess\n" +
                "(\n" +
                "\tID INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "\tmessageUser varchar(100), \n" +
                "\tmessageSender varchar(10), \n" +
                "\tmessageText varchar(3000), \n" +
                "\tmessageTaker varchar(1000), \n" +
                "\tmessageTime varchar(100) \n" +
                ");");

        SQLiteDatabase allmessagesDataBase = openOrCreateDatabase("AllMessages", MODE_PRIVATE, null);
        allmessagesDataBase.execSQL("create table if not exists AllMessages\n" +
                "(\n" +
                "\tID INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "\tmessageUser varchar(10), \n" +
                "\tmessageText varchar(3000), \n" +
                "\tmessageTaker varchar(1000), \n" +
                "\tmessageTime varchar(100) \n" +
                ");");

        Cursor c = VisibleMessagesDataBase.rawQuery("select * from VisibleMessagess where messageUser = ? and ((messageTaker=? and messageSender=?) or (messageSender=? and messageTaker=?))", new String[] {user, taker, number, taker, number});
        c.moveToFirst();

        while (!c.isAfterLast()) {

            int messageTextIndex = c.getColumnIndex("messageText");
            int messageTimeIndex = c.getColumnIndex("messageTime");
            int messageSenderIndex = c.getColumnIndex("messageSender");
            int messageIDIndex = c.getColumnIndex("ID");



            String timeText = c.getString(messageTimeIndex);
            Message message = new Message();

            if ((c.getString(messageSenderIndex)).equals(number)) {
                message.setMessageId(c.getLong(messageIDIndex));
                message.setMessageUser("You");
                message.setMessageType(1);
                message.setMessageText(c.getString(messageTextIndex));
                message.setMessageTime(timeText);


                messageType=1;
                if(c.getLong(messageIDIndex)==id){
                    n=i;
                }
                i++;

                adapter.add(message);

            }
            else{
                messageType=2;
                message.setMessageId(c.getLong(messageIDIndex));
                message.setMessageUser(person.getName());
                message.setMessageText(c.getString(messageTextIndex));
                message.setMessageTime(timeText);
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


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (count%2==1) {
                    count++;
                    cs.setVisibility(View.INVISIBLE);
                }
            }
        });

        delete_chat.setOnTouchListener(new View.OnTouchListener(){

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                        delete_chat.setBackground(getDrawable(R.drawable.rectangular_flow_shape));
                        break;
                    case MotionEvent.ACTION_MOVE: // движение
                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                        delete_chat.setBackground(getDrawable(R.drawable.rectangular_white));
                        adapter.clear();
                        VisibleMessagesDataBase.execSQL( "delete from VisibleMessagess where (messageUser = ? and ((messageTaker=? and messageSender=?) or (messageSender=? and messageTaker=?))) ", new String[] {user, taker, user, taker, user});
                    case MotionEvent.ACTION_CANCEL:
                        break;
                }
                return true;
            }
        });

        vlojenia.setOnTouchListener(new View.OnTouchListener(){

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                        vlojenia.setBackground(getDrawable(R.drawable.rectangular_flow_shape));
                        break;
                    case MotionEvent.ACTION_MOVE: // движение
                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                        vlojenia.setBackground(getDrawable(R.drawable.rectangular_white));
                    case MotionEvent.ACTION_CANCEL:
                        break;
                }
                return true;
            }
        });

        add_to_contacts.setOnTouchListener(new View.OnTouchListener(){

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                        add_to_contacts.setBackground(getDrawable(R.drawable.rectangular_flow_shape));
                        break;
                    case MotionEvent.ACTION_MOVE: // движение
                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                        add_to_contacts.setBackground(getDrawable(R.drawable.rectangular_white));
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
                }
                else{
                    int margin62inDp = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 62, getResources().getDisplayMetrics());
                    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams
                            (ConstraintLayout.LayoutParams.MATCH_PARENT, margin62inDp);
                    layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
                    bot.setLayoutParams(layoutParams);
                    details.setVisibility(View.INVISIBLE);
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

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                        search_open.setBackground(getDrawable(R.drawable.rectangular_flow_shape));
                        break;
                    case MotionEvent.ACTION_MOVE: // движение
                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                        search_open.setBackground(getDrawable(R.drawable.rectangular_white));
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


        search_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                cs.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(getApplicationContext(), Search.class);
                intent.putExtra("person", person);
                intent.putExtra("number", number);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
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

                    DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                    String timeText = timeFormat.format(currentDate);

                    String text = input.getText().toString();

                    input.getText().clear();


                    VisibleMessagesDataBase.execSQL("insert into VisibleMessagess(messageUser, messageSender,messageText, messageTaker, messageTime) values('"+user+"','"+number+"','"+text+"','"+taker+"','"+timeText+"')");
                    VisibleMessagesDataBase.execSQL("insert into VisibleMessagess(messageUser, messageSender,messageText, messageTaker, messageTime) values('"+taker+"','"+number+"','"+text+"','"+taker+"','"+timeText+"')");


                    Cursor cmes = VisibleMessagesDataBase.rawQuery("select * from VisibleMessagess", null);
                    cmes.moveToLast();

                    allmessagesDataBase.execSQL("insert into AllMessages(messageUser,messageText, messageTaker, messageTime) values('"+number+"','"+text+"','"+taker+"','"+timeText+"')");

                    int messageIDIndex = cmes.getColumnIndex("ID");


                    messageType=1;
                    Message message = new Message();
                    message.setMessageId(cmes.getLong(messageIDIndex));
                    message.setMessageUser("You");
                    message.setMessageText(text);
                    message.setMessageType(1);
                    message.setMessageTime(timeText);
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

    public int getViewType(int messageType) {
        if (messageType==1) {
            return 1;
        } else{
            return 2;
        }
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
                        Date currentDate = new Date();

                        DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                        String timeText = timeFormat.format(currentDate);

                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);

                        Message message = new Message();
                        message.setMessageUser("You");
                        message.setMessageType(3);
                        message.setImage(bitmap);
                        message.setMessageTime(timeText);
                        adapter.add(message);
                        listView.setSelection(listView.getCount() - 1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
}