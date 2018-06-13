package com.example.jihu02.planet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

/**
 * Created by jihu0 on 2018-06-13.
 */

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReferece;
    private ChildEventListener mChildEventListener;
    private ChatAdapter mAdapter;
    private ListView mListView;
    private EditText mEditMessage;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_screen);
        initView();
        initFirebaseDatabase();
        initValues();
    }
    private void initValues(){
        userName = "Guest"+new Random().nextInt(5000);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new ChatAdapter(this, R.layout.listitem_chat);
        mListView.setAdapter(mAdapter);
        mEditMessage = (EditText) findViewById(R.id.textInput);
        findViewById(R.id.btnSend).setOnClickListener(this);
    }

    private void initFirebaseDatabase() {
        mFirebaseDatabase = mFirebaseDatabase.getInstance();
        mDatabaseReferece = mFirebaseDatabase.getReference("message");
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatData chatData = dataSnapshot.getValue(ChatData.class);
                chatData.firebaseKey = dataSnapshot.getKey();
                mAdapter.add(chatData);
                mListView.smoothScrollToPosition(mAdapter.getCount());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String firebaseKey = dataSnapshot.getKey();
                int count = mAdapter.getCount();
                for(int i=0; i< count; i++){
                    if(mAdapter.getItem(i).firebaseKey.equals(firebaseKey)){
                        mAdapter.remove(mAdapter.getItem(i));
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabaseReferece.addChildEventListener(mChildEventListener);
    }

    protected void onDestroy() {
        super.onDestroy();
        mDatabaseReferece.removeEventListener(mChildEventListener);
    }

    @Override
    public void onClick(View v) {
        String message = mEditMessage.getText().toString();
        if (!TextUtils.isEmpty(message)) {
            mEditMessage.setText("");
            ChatData chatData = new ChatData();
            chatData.userName = userName;
            chatData.message = message;
            chatData.time = System.currentTimeMillis();
            mDatabaseReferece.push().setValue(chatData);
        }
    }
}

