package com.example.jihu02.planet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by jihu0 on 2018-06-13.
 */

public class ChatAdapter extends ArrayAdapter<ChatData>{
    private final SimpleDateFormat mSimpleDataFormat = new SimpleDateFormat("a h:mm", Locale.getDefault());

    public ChatAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listitem_chat, null);

            viewHolder = new ViewHolder();
            viewHolder.mTextUserName = (TextView) convertView.findViewById(R.id.txt_userName);
            viewHolder.mTextMessage = (TextView) convertView.findViewById(R.id.txt_message);
            viewHolder.mTextTime = (TextView) convertView.findViewById(R.id.txt_time);

            convertView.setTag(viewHolder);


        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ChatData chatData = getItem(position);
        viewHolder.mTextUserName.setText(chatData.userName);
        viewHolder.mTextMessage.setText(chatData.message);
        viewHolder.mTextTime.setText(mSimpleDataFormat.format(chatData.time));

        return convertView;
    }
    private class ViewHolder {
        private TextView mTextUserName;
        private TextView mTextMessage;
        private TextView mTextTime;
    }
}
