package com.example.authorization;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

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
    private ConstraintLayout cs;
    private int count;
    private int count3=1;
    public int count2=1;
    private ImageView send;
    private EditText input;
    private ImageView mic;
    private ConstraintLayout bot;
    private ConstraintLayout details;
    private ImageView add_image;
    static final int GALLERY_REQUEST = 1;
    private ImageView search;
    private ConstraintLayout search_open;


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
        ConstraintLayout cs = findViewById(R.id.message_cs);




        Intent intent = getIntent();
        Person person = (Person) intent.getExtras().get("person");
        String number = (String) intent.getExtras().get("number");
        if(person != null){
            int imageId = getResources().getIdentifier(person.getAvatar(), "drawable", getPackageName());
            avatar.setImageResource(imageId);

            name.setText(person.getName());
        }


        List<Message> messages = new ArrayList<>();

        MessageAdapter adapter = new MessageAdapter (getApplicationContext(), R.layout.message, messages);

        String taker = person.getNumber();

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("messages", MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("create table if not exists messages\n" +
                "(\n" +
                "\tmessageUser varchar(10), \n" +
                "\tmessageText varchar(3000), \n" +
                "\tmessageTaker varchar(1000), \n" +
                "\tmessageTime varchar(100) \n" +
                ");");

        Cursor c = sqLiteDatabase.rawQuery("select * from messages where ((messageTaker=? and messageUser=?) or (messageUser=? and messageTaker=?))", new String[] {taker, number, taker, number});
        c.moveToFirst();


        while (!c.isAfterLast()) {

            int messageTextIndex = c.getColumnIndex("messageText");
            int messageTimeIndex = c.getColumnIndex("messageTime");
            int messageUserIndex = c.getColumnIndex("messageUser");



            String timeText = c.getString(messageTimeIndex);
            Message message = new Message();

            if ((c.getString(messageUserIndex)).equals(number)) {


                message.setMessageUser("You");
                message.setMessageText(c.getString(messageTextIndex));
                message.setMessageTime(timeText);

                listView.setAdapter(adapter);
                adapter.add(message);
            }
            else{

                message.setMessageUser(person.getName());
                message.setMessageText(c.getString(messageTextIndex));
                message.setMessageTime(timeText);

                listView.setAdapter(adapter);
                adapter.add(message);
            }
            c.moveToNext();
        }


//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams
//                        (300, 300);
//                layoutParams.setMargins(0, eventY, 0, 0);
//                layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
//                layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
//                ll.setLayoutParams(layoutParams);
//                ll.setVisibility(View.VISIBLE);
//                return false;
//            }
//        });




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


        search_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Search.class);
                intent.putExtra("person", taker);
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

                    count3 = 1;
                    MessageAdapter adapter = new MessageAdapter (getApplicationContext(), R.layout.my_message, messages);


                    DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                    String timeText = timeFormat.format(currentDate);

                    String text = input.getText().toString();

                    input.getText().clear();


                    sqLiteDatabase.execSQL("insert into messages(messageUser,messageText, messageTaker, messageTime) values('"+number+"','"+text+"','"+taker+"','"+timeText+"')");

                    Message message = new Message();
                    message.setMessageUser("You");
                    message.setMessageText(text);
                    message.setMessageTime(timeText);
                    count2 = 1;
                    adapter.add(message);

                }
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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        Bitmap bitmap = null;
        //ImageView imageView = (ImageView) findViewById(R.id.imageView);

        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //imageView.setImageBitmap(bitmap);
                }
        }
    }


    private class MessageAdapter extends ArrayAdapter<Message> {


        public MessageAdapter(@NonNull Context context, int resource, @NonNull List<Message> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Message message = getItem(position);
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.message, null);


            MessageHolder holder = new MessageHolder();
            holder.UserName = convertView.findViewById(R.id.message_user);
            holder.UserText = convertView.findViewById(R.id.message_text);
            holder.Time = convertView.findViewById(R.id.message_time);


            holder.UserName.setText(message.getMessageUser());
            holder.UserText.setText(message.getMessageText());
            holder.Time.setText(message.getMessageTime());

            convertView.setTag(holder);

            return convertView;
        }
    }

    private static class MessageHolder {
        public TextView UserName;
        public TextView UserText;
        public TextView Time;
    }



}