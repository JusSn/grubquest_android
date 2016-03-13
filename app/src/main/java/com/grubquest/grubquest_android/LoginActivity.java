package com.grubquest.grubquest_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.grubquest.grubquest_android.Data.GQConstants;
import com.grubquest.grubquest_android.Models.User;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Firebase firebase = new Firebase(GQConstants.DATABASE);

        //automatically goes to HomeActivity if signed in before
        if (firebase.getAuth() != null) {
            Intent next = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(next);
        }

        final Button login_button = (Button) findViewById(R.id.login_button);
        final EditText password = (EditText) findViewById(R.id.password_edittext);
        final EditText username = (EditText) findViewById(R.id.username_edittext);
        final RelativeLayout incorrect = (RelativeLayout) findViewById(R.id.invalid_username_pass);


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = username.getText().toString();
                final String pass = password.getText().toString();

                firebase.authWithPassword(email, pass, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Intent next = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(next);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Log.d("GrubQuest", firebaseError.getMessage());
                        Log.d("GrubQuest", firebaseError.getDetails());
                        incorrect.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }
}
