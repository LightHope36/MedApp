package com.example.authorization;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Chat extends AppCompatActivity {

    private ListView listView;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        back = findViewById(R.id.back_im);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), auth3.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        listView = findViewById(R.id.list_of_messages);
        List<Message> messages = new ArrayList<>();


        for (int i=1; i<10; i++){
            messages.add(new Message( "text" + i, "user"+i));
        }

        MessageAdapter adapter = new MessageAdapter (getApplicationContext(), R.layout.message, messages);
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