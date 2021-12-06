package com.example.authorization;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MessageAdapter extends BaseAdapter {

    private List<Message> messages = new ArrayList<>();
    private Context context;

    public MessageAdapter(Context context) {
        this.context = context;
    }

    public void add(Message message) {
        this.messages.add(message);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clear() {
        messages.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        MessageHolder holder = new MessageHolder();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Message message = messages.get(i);
        int viewType = message.getMessageType();

        switch(viewType){
            case 0:
                convertView = inflater.inflate(R.layout.date, null);
                holder.UserText = convertView.findViewById(R.id.datetext);
                holder.UserText.setText(message.getMessageText());
                convertView.setTag(holder);
                break;
            case 1:
                convertView = inflater.inflate(R.layout.my_message, null);

                holder.UserText = convertView.findViewById(R.id.message_text);
                holder.Time = convertView.findViewById(R.id.message_time);

                holder.UserText.setText(message.getMessageText());
                holder.Time.setText(message.getMessageTime());
                convertView.setTag(holder);
                break;
            case 2:
                convertView = inflater.inflate(R.layout.message, null);
                holder.UserText = convertView.findViewById(R.id.message_text);
                holder.Time = convertView.findViewById(R.id.message_time);

                holder.UserText.setText(message.getMessageText());
                holder.Time.setText(message.getMessageTime());
                convertView.setTag(holder);
                break;
            case 3:
                convertView = inflater.inflate(R.layout.my_message_image, null);

                holder.Time = convertView.findViewById(R.id.message_time);


                try {
                    ImageView picture = convertView.findViewById(R.id.user_image);
                    picture.setImageBitmap(message.getImage());
                }catch (Exception e){
                    Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                }

                holder.Time.setText(message.getMessageTime());

                convertView.setTag(holder);
                break;
            case 5:
                convertView = inflater.inflate(R.layout.my_voice_message, null);

                holder.UserText = convertView.findViewById(R.id.message_text);
                holder.Time = convertView.findViewById(R.id.message_time);
                holder.voicetime = convertView.findViewById(R.id.message_voicetime);

                holder.Time = convertView.findViewById(R.id.message_time);
                holder.UserText.setText("Голосовое сообщение");
                int seconds = message.getMessageVoicetime();
                String sec="";
                if (seconds % 10 == 1 && seconds %100/10!=1){
                    sec="секунда";
                }
                else if ((seconds % 10 == 2 || seconds % 10 == 3 || seconds % 10 == 4) && seconds %100/10!=1){
                    sec="секунды";
                }
                else{
                    sec="секунд";
                }
                holder.voicetime.setText(Integer.toString(seconds) + " " + sec);

                holder.Time.setText(message.getMessageTime());

                convertView.setTag(holder);
                break;
        }

        return convertView;
    }

    private static class MessageHolder {
        public TextView voicetime;
        public TextView UserName;
        public TextView UserText;
        public TextView Time;
    }
}


