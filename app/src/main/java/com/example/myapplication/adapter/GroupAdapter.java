package com.example.myapplication.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Group;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    final Activity activity;
    final ArrayList<Group> groups ;

    public GroupAdapter(Activity activity, ArrayList<Group> groups) {
        this.activity = activity;
        this.groups = groups;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.view_chat_layout, parent, false);
        return new GroupItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final GroupItemHolder holder = (GroupItemHolder) holderParent;
        final Group group = groups.get(position);
        holder.groupName.setText(group.getGroup_name());
        holder.description.setText(group.getGroup_description());
        Picasso.get().load(group.getGroup_image()).fit().centerInside()
                .placeholder(R.drawable.ic_profile_placeholder)
                .into(holder.image);
    }

    @Override
    public int getItemCount() { return groups.size(); }

    static class GroupItemHolder extends RecyclerView.ViewHolder {

        final TextView groupName;
        final TextView description;
        final ImageView image;

        public GroupItemHolder(@NonNull View itemView) {
            super(itemView);
            groupName = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.image);
        }
    }
}
