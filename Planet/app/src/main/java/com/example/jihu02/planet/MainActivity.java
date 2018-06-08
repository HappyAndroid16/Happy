package com.example.jihu02.planet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mFirebaseAuthListener;

    LoginButton mSigninFacebookButton;
    CallbackManager mFacebookCallbackManager;

    EditText inputId;
    EditText inputPassword;
    String name;
    String planet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputId=findViewById(R.id.inputId);
        inputPassword=findViewById(R.id.inputPassword);
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
                    finish();
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
        startActivity(intent);
    }

    public void onLoginListener(View view) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        SharedPreferences pref = getSharedPreferences("회원정보", MODE_PRIVATE);

        name=pref.getString("이름","");
        planet=pref.getString("내행성","");

        if(inputId.getText().toString().equals(pref.getString("아이디", ""))){
            if(inputPassword.getText().toString().equals(pref.getString("비밀번호", ""))){
                Toast.makeText(getApplicationContext(),name+"님 "+planet+"에 오신걸 환영합니다",Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        }


            }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);

        }
}