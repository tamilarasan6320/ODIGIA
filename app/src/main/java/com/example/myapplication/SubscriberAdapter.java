package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.User;

import java.util.ArrayList;

public class SubscriberAdapter extends RecyclerView.Adapter<SubscriberAdapter.ViewHoldeer> {

    Activity activity;
    ArrayList<User> arrayList;


    public SubscriberAdapter(Activity activity, ArrayList<User> arrayList){
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

        User model =arrayList.get(position);

        holder.tvname.setText(model.getFirst_name());
        holder.tvnumber.setText(model.getMobile());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                model.setIs_Selected(isChecked);
                if(isChecked){

                    Toast.makeText(activity, String.valueOf(model.getId()), Toast.LENGTH_SHORT).show();

                }

            }
        });


    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public class ViewHoldeer extends RecyclerView.ViewHolder {

        TextView tvname,tvnumber;
        CheckBox checkBox;


        public ViewHoldeer(@NonNull View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.tv_name);
            tvnumber = itemView.findViewById(R.id.tv_number);
            checkBox = itemView.findViewById(R.id.checkboxcontact);
        }
    }
}
