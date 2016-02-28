package com.grubquest.grubquest_android.Adapters;

import android.content.Context;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grubquest.grubquest_android.Models.Quest;
import com.grubquest.grubquest_android.R;

import java.util.ArrayList;

/**
 * Created by Derek on 2/27/2016.
 */
public class QuestRecyclerAdapter
        extends RecyclerView.Adapter<QuestRecyclerAdapter.QuestViewHolder> {
    ArrayList<Quest> quests;
    Context context;
    LayoutInflater inflater;

    public QuestRecyclerAdapter(Context context, ArrayList<Quest> quests) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return 4;
        //return quests.size();
    }

    @Override
    public void onBindViewHolder(QuestViewHolder holder, int position) {
        holder.company_text.setText("Hello, World!");
    }

    @Override
    public QuestRecyclerAdapter.QuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View quest_view = inflater.inflate(R.layout.layout_quest, parent, false);
        return new QuestViewHolder(quest_view);
    }

    public static class QuestViewHolder extends RecyclerView.ViewHolder {
        public QuestViewHolder(View view) {

        }
    }
}
