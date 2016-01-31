package com.grubquest.grubquest_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class LoginActivity extends AppCompatActivity {
    private AuthData authData;
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

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebase.authWithPassword(email, pass, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        System.out.println("Signed in");
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        System.out.println("Error");
                    }
                });
            }
        });
    }
}
