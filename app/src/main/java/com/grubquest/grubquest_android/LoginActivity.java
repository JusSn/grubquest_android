package com.grubquest.grubquest_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Firebase firebase = new Firebase(GQConstants.DATABASE);

        final Button login_button = (Button) findViewById(R.id.login_button);
        final EditText password = (EditText) findViewById(R.id.password_edittext);
        final EditText username = (EditText) findViewById(R.id.username_edittext);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = username.getText().toString();
                String pass = password.getText().toString();

                firebase.authWithPassword(email, pass, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        //send to next activity with AuthData in Bundle
                        Intent next = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(next);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Log.d("GrubQuest", firebaseError.getMessage());
                        Log.d("GrubQuest", firebaseError.getDetails());
                        Toast.makeText(LoginActivity.this, "Something fucked up", Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
        });
    }
}
