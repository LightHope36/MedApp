package com.example.authorization;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
        int viewType;
        if(message.getMessageUser().equals("You")) {
            viewType = 1;
        }
        else{
            viewType = 2;
        }

        switch(viewType){
            case 1:
                convertView = inflater.inflate(R.layout.my_message, null);

                holder.UserName = convertView.findViewById(R.id.message_user);
                holder.UserText = convertView.findViewById(R.id.message_text);
                holder.Time = convertView.findViewById(R.id.message_time);

                holder.UserName.setText(message.getMessageUser());
                holder.UserText.setText(message.getMessageText());
                holder.Time.setText(message.getMessageTime());
                convertView.setTag(holder);
                break;
            case 2:
                convertView = inflater.inflate(R.layout.message, null);
                holder.UserName = convertView.findViewById(R.id.message_user);
                holder.UserText = convertView.findViewById(R.id.message_text);
                holder.Time = convertView.findViewById(R.id.message_time);

                holder.UserName.setText(message.getMessageUser());
                holder.UserText.setText(message.getMessageText());
                holder.Time.setText(message.getMessageTime());
                convertView.setTag(holder);
                break;
        }

        return convertView;
    }

    private static class MessageHolder {
        public TextView UserName;
        public TextView UserText;
        public TextView Time;
    }
}


