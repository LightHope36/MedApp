package com.example.authorization;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailedChat2 extends AppCompatActivity {

    private ImageView add;
    private ImageView back;
    private ListView listView;
    private ImageView menu;
    private ConstraintLayout cs;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_chat2);

        add = findViewById(R.id.app_back);
        back = findViewById(R.id.back_im2);
        menu = findViewById(R.id.menu_det);
        cs = findViewById(R.id.menu_chata_det);
        count = 0;
        listView = findViewById(R.id.list_of_messages_det);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Chat.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
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


        List<Message> messages = new ArrayList<>();


        for (int i=1; i<10; i++){
            Message message = new Message();
            message.setMessageText("text"+i);
            message.setMessageUser("User"+i);
            message.setMessageTime(new Date().getTime());
            message.setAuthorAvatar("ic_profile_1");
            messages.add(message);
        }

        MessageAdapter adapter = new MessageAdapter(getApplicationContext(), R.layout.message, messages);
        listView.setAdapter(adapter);


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
            convertView = inflater.inflate(R.layout.message, null);

            ImageView imageView = convertView.findViewById(R.id.profile);
            int imageId = getContext().getResources().getIdentifier(message.getAuthorAvatar(), "drawable", getContext().getPackageName());
            imageView.setImageResource(imageId);

            MessageHolder holder = new MessageHolder();
            holder.UserName = convertView.findViewById(R.id.message_user);
            holder.UserText = convertView.findViewById(R.id.message_text);



            holder.UserName.setText(message.getMessageUser());
            holder.UserText.setText(message.getMessageText());

            convertView.setTag(holder);
            return convertView;
        }
    }

    private static class MessageHolder {
        public TextView UserName;
        public TextView UserText;
    }
}