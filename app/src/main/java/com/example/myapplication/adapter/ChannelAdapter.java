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
import com.example.myapplication.model.Channel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChannelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    final Activity activity;
    final ArrayList<Channel> channels;

    public ChannelAdapter(Activity activity, ArrayList<Channel> channels) {
        this.activity = activity;
        this.channels = channels;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.view_chat_layout, parent, false);
        return new ChannelItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ChannelItemHolder holder = (ChannelItemHolder) holderParent;
        final Channel channel = channels.get(position);
        holder.channelName.setText(channel.getChannel_name());
        holder.description.setText(channel.getChannel_description());
        Picasso.get().load(channel.getChannel_image()).fit().centerInside()
                .placeholder(R.drawable.ic_profile_placeholder)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    static class ChannelItemHolder extends RecyclerView.ViewHolder {

        final TextView channelName;
        final TextView description;
        final ImageView image;

        public ChannelItemHolder(@NonNull View itemView) {
            super(itemView);
            channelName = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.image);
        }
    }
}
