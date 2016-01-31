package com.grubquest.grubquest_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.firebase.client.Firebase;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Firebase firebase = new Firebase(GQConstants.DATABASE);

        TextView view = (TextView) findViewById(R.id.check_login_textview);

        if (firebase.getAuth() != null) {
            view.setText("Logged In");
        } else view.setText("Not logged in");
    }
}
