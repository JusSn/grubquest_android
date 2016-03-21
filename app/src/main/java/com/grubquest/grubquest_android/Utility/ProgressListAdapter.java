package com.grubquest.grubquest_android.Utility;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.grubquest.grubquest_android.Models.ProgressItem;
import com.grubquest.grubquest_android.R;

import java.util.ArrayList;

/**
 * Created by Justin on 3/20/16.
 */
public class ProgressListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ProgressItem> progressList;

    public ProgressListAdapter(Context c, ArrayList<ProgressItem> a) {
        context = c;
        progressList = a;
    }
    @Override
    public int getCount() {
        return progressList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.progress_list_item, null);
        }

        ProgressBar pB = (ProgressBar) convertView.findViewById(R.id.progress_bar);
        TextView text = (TextView) convertView.findViewById(R.id.progressbar_text);

        ProgressItem pI = progressList.get(position);

        pB.setProgress(Integer.parseInt(String.valueOf(pI.highest)));
        pB.setMax(Integer.parseInt(String.valueOf(pI.max)));

        text.setText(pI.stat);

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return progressList.get(position);
    }
}
