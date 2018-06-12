package com.example.jihu02.planet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private DatabaseReference mReference2;
    private DatabaseReference mReference3;
    private DatabaseReference mReference4;
    private ChildEventListener mChild;

    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mFirebaseAuthListener;

    LoginButton mSigninFacebookButton;
    CallbackManager mFacebookCallbackManager;

    EditText inputId;
    EditText inputPassword;
    String name;
    String planet;

    String sid, spassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputId=findViewById(R.id.inputId);
        inputPassword=findViewById(R.id.inputPassword);


        mDatabase= FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("아이디");
        mReference2 = mDatabase.getReference("비밀번호");
        mReference3 = mDatabase.getReference("이름");
        mReference4 = mDatabase.getReference("행성");

        /*
         * Firebase
         */

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if ( user != null ) {
                    //Log.d(TAG, "sign in");

                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                else {
                    //Log.d(TAG, "sign out");
                }

            }
        };

        /*
         * Facebook Login
         */
        mFacebookCallbackManager = CallbackManager.Factory.create();

        mSigninFacebookButton = (LoginButton) findViewById(R.id.button_facebook_login);
        mSigninFacebookButton.setReadPermissions("email", "public_profile");
        mSigninFacebookButton.registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AuthCredential credential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
                mFirebaseAuth.signInWithCredential(credential);
                Toast.makeText(getApplicationContext(),"페북연동성공",Toast.LENGTH_LONG).show();
                Log.d("TAG","Facebook login success");
            }

            @Override
            public void onCancel() {
                Log.d("TAG", "Facebook login canceled.");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("TAG", "Facebook Login Error", error);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mFirebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if ( mFirebaseAuthListener != null )
            mFirebaseAuth.removeAuthStateListener(mFirebaseAuthListener);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    public void onJoinListener(View view) {

        Intent intent = new Intent(getApplicationContext(),JoinActivity.class);
        startActivityForResult(intent,200);

    }

    public void onLoginListener(View view) {


        SharedPreferences pref = getSharedPreferences("Join", MODE_PRIVATE);
        if(inputId.getText().toString().equals(pref.getString("아이디",""))){
            if(inputPassword.getText().toString().equals(pref.getString("비밀번호",""))){
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivityForResult(intent,101);
            }
        }
        else
            Toast.makeText(this,"아이디와 비밀번호가 일치하지 않습니다",Toast.LENGTH_LONG).show();

        //sid= inputId.getText().toString().trim();
        //spassword = inputPassword.getText().toString().trim();

        /*mFirebaseAuth.signInWithEmailAndPassword(sid, spassword)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "로그인 오류", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/

    }
       /* mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();

                while(child.hasNext())
                {
                    //찾고자 하는 ID값은 key로 존재하는 값
                    if(child.next().getKey().equals(inputId.getText().toString()))
                    {
                        Toast.makeText(getApplicationContext(),"로그인!",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }
                    Toast.makeText(getApplicationContext(),"존재하지 않는 아이디입니다.",Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);

        }
}