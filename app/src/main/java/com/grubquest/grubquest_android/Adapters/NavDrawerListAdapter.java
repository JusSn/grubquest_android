package com.grubquest.grubquest_android.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.grubquest.grubquest_android.Models.NavDrawerItem;
import com.grubquest.grubquest_android.R;

import java.util.ArrayList;

public class NavDrawerListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<NavDrawerItem> navDrawerItems;

    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems){
        this.context = context;
        this.navDrawerItems = navDrawerItems;
    }

    @Override
    public int getCount() { return navDrawerItems.size(); }

    @Override
    public Object getItem(int position) { return navDrawerItems.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }

        ImageView img = (ImageView) convertView.findViewById(R.id.list_item_imageview);
        TextView text = (TextView) convertView.findViewById(R.id.list_item_text);

        img.setImageResource(navDrawerItems.get(position).getIcon());
        text.setText(navDrawerItems.get(position).getName());

        return convertView;
    }
}