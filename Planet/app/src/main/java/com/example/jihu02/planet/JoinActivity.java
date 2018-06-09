package com.example.jihu02.planet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JoinActivity extends Activity {

    EditText name,id,password,myPlanet;
    //private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    //private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_join);
        name=findViewById(R.id.name);
        id=findViewById(R.id.id);
        password=findViewById(R.id.password);
        myPlanet=findViewById(R.id.myPlanet);
    }

    public void onJoin2Listener(View view) {
        SharedPreferences pref = getSharedPreferences("회원정보", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("이름", name.getText().toString()+"");
        editor.putString("아이디", id.getText().toString()+"");
        editor.putString("비밀번호", password.getText().toString()+"");
        editor.putString("내행성", myPlanet.getText().toString()+"");
        editor.commit();

        /*databaseReference.child("이름").push().setValue(name.getText().toString());
        databaseReference.child("아이디").push().setValue(id.getText().toString());
        databaseReference.child("비밀번호").push().setValue(password.getText().toString());
        databaseReference.child("내행성").push().setValue(myPlanet.getText().toString());*/

        finish();
    }
}
