package com.grubquest.grubquest_android.Models;

/**
 * Created by Derek on 2/27/2016.
 */
public class NavDrawerItem {
    private String name;
    private int icon;

    public NavDrawerItem(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public int getIcon(){ return this.icon; }
    public String getName(){ return this.name; }
}
