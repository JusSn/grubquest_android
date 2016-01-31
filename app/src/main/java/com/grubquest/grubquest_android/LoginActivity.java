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
    private Button login_button;
    private EditText password;
    private EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Firebase firebase = new Firebase(GQConstants.DATABASE);

        login_button = (Button) findViewById(R.id.login_button);
        password = (EditText) findViewById(R.id.password_edittext);
        username = (EditText) findViewById(R.id.username_edittext);

        final String email = username.getText().toString();
        final String pass = password.getText().toString();

        firebase.createUser("cuche@purdue.edu", "chukwudi", new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                System.out.println("Yes");
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                System.out.println(firebaseError.getDetails());
                System.out.println(firebaseError.getMessage());
                System.out.println("No");
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
