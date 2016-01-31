package com.grubquest.grubquest_android;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Derek on 1/30/2016.
 */
public class GrubQuest extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
