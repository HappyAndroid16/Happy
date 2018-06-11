package com.example.jihu02.planet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class JoinActivity extends Activity {

    EditText name,id,password,myPlanet;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    String sname, sid,spassword,splanet;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_join);

        firebaseAuth = FirebaseAuth.getInstance();

        name=findViewById(R.id.name);
        id=findViewById(R.id.id);
        password=findViewById(R.id.password);
        myPlanet=findViewById(R.id.myPlanet);
    }

    public void onJoin2Listener(View view) {

        sname = name.getText().toString();
        splanet=myPlanet.getText().toString();

        //databaseReference.child("이름").push().setValue(sname);
        //databaseReference.child("아이디").push().setValue(sid);
        databaseReference.child("비밀번호").push().setValue(spassword);
        databaseReference.child("내행성").push().setValue(splanet); //데이터 저장


        sid=id.getText().toString().trim();
        spassword=password.getText().toString().trim();

        firebaseAuth.createUserWithEmailAndPassword(sid, spassword)
                .addOnCompleteListener(JoinActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            Toast.makeText(JoinActivity.this, "등록 에러", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });

    }
}
