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
import com.example.myapplication.model.Chat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    final Activity activity;
    final ArrayList<Chat> chats;

    public ChatAdapter(Activity activity, ArrayList<Chat> chats) {
        this.activity = activity;
        this.chats = chats;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.view_chat_layout, parent, false);
        return new ChatItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ChatItemHolder holder = (ChatItemHolder) holderParent;
        final Chat chat = chats.get(position);
        holder.channelName.setText(chat.getChannel_name() + chats.size());
        holder.description.setText(chat.getChannel_description());
        Picasso.get().load(chat.getChannel_image()).fit().centerInside()
                .placeholder(R.drawable.ic_profile_placeholder)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    static class ChatItemHolder extends RecyclerView.ViewHolder {

        final TextView channelName;
        final TextView description;
        final ImageView image;

        public ChatItemHolder(@NonNull View itemView) {
            super(itemView);
            channelName = itemView.findViewById(R.id.channel_name);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.image);
        }
    }
}
