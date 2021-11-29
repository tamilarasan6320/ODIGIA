package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHoldeer> {

    Activity activity;
    ArrayList<ContactModal> arrayList;


    public MainAdapter(Activity activity,ArrayList<ContactModal> arrayList){
        this.activity =activity;
        this .arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHoldeer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact,parent,false);



        return new ViewHoldeer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoldeer holder, int position) {

        ContactModal model =arrayList.get(position);

        holder.tvname.setText(model.getName());
        holder.tvnumber.setText(model.getNumber());


    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public class ViewHoldeer extends RecyclerView.ViewHolder {

        TextView tvname,tvnumber;


        public ViewHoldeer(@NonNull View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.tv_name);
            tvnumber = itemView.findViewById(R.id.tv_number);
        }
    }
}
