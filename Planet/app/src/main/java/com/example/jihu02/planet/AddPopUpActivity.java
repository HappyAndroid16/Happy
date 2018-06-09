package com.example.jihu02.planet;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPopUpActivity extends Activity {

    EditText addTextMission;
    Button addButton;
    public String str;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_pop_up);
        addTextMission = findViewById(R.id.textAdd);
        addButton=findViewById(R.id.addButton);
    }

    public void onPushClicked(View view) {
        str = addTextMission.getText().toString();
        databaseReference.child("mission").push().setValue(str);
        finish();
    }
}
