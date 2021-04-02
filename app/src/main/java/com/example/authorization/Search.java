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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {

    private ImageView back;
    private EditText search;
    private ListView listView;
    Cursor c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        back = findViewById(R.id.back_im_from_search);
        search = findViewById(R.id.search_input_chat);
        listView = findViewById(R.id.list_of_messages_in_search);

        Intent intent = getIntent();
        String taker = (String) intent.getExtras().get("person");

        List<Message> messages = new ArrayList<>();

        MessageAdapter adapter = new MessageAdapter(getApplicationContext(), R.layout.message, messages);

        listView.setAdapter(adapter);

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("messages", MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("create table if not exists messages\n" +
                "(\n" +
                "\tmessageUser varchar(1000), \n" +
                "\tmessageText varchar(3000), \n" +
                "\tmessageTaker varchar(1000), \n" +
                "\tmessageTime varchar(100) \n" +
                ");");






        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search.super.finish();
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

                    if(!taker.equals("0")) {

                        c = sqLiteDatabase.rawQuery("select messageUser, messageText, messageTaker, messageTime from messages where messageText like '%' || ? || '%' and messageTaker=?", new String[]{text, taker});
                    }
                    else{
                        c = sqLiteDatabase.rawQuery("select messageUser, messageText, messageTaker, messageTime from messages where messageText like '%' || ? || '%' ", new String[]{text});

                    }
                    c.moveToFirst();


                    int messageUserIndex = c.getColumnIndex("messageUser");
                    int messageTextIndex = c.getColumnIndex("messageText");
                    int messageTimeIndex = c.getColumnIndex("messageTime");

                    while (!c.isAfterLast()) {
                        Message message = new Message();
                        message.setMessageUser(c.getString(messageUserIndex));
                        message.setMessageText(c.getString(messageTextIndex));
                        message.setMessageTime(c.getString(messageTimeIndex));
                        adapter.add(message);
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


    }

    private static class MessageAdapter extends ArrayAdapter<Message> {

        public MessageAdapter(@NonNull Context context, int resource, @NonNull List<Message> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Message message = getItem(position);
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.my_message, null);


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