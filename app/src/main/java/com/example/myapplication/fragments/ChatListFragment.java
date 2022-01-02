package com.example.myapplication.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ChannelAdapter;
import com.example.myapplication.adapter.GroupAdapter;
import com.example.myapplication.helper.ApiConfig;
import com.example.myapplication.helper.Constant;
import com.example.myapplication.helper.Session;
import com.example.myapplication.model.Channel;
import com.example.myapplication.model.Group;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatListFragment extends Fragment {



    public ChatListFragment() {
        // Required empty public constructor
    }
    View root;
    private Session session;
    public static Activity activity;
    public static RecyclerView recyclerView;
    public static ChannelAdapter channelAdapter;
    public static GroupAdapter groupAdapter;
    Chip chip1,chip2;
    ChipGroup chipgroup;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    boolean checkgroups = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_chat_list, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        activity = getActivity();
        session = new Session(activity);

        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipeLayout);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        chip1 = root.findViewById(R.id.chip1);
        chip2 = root.findViewById(R.id.chip2);
        chipgroup = root.findViewById(R.id.chipGroup);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (checkgroups){
                    chip1.setChecked(true);

                    GroupList();

                }
                else {
                    chip2.setChecked(true);
                    ChannelList();

                }


            }
        });

        GroupList();
        chipgroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                if (chip1.isChecked()) {
                    GroupList();
                }
                else if (chip2.isChecked()) {
                 ChannelList();

                }

            }
        });




        return root;
    }

    private void ChannelList()
    {
        Map<String, String> params = new HashMap<>();

        params.put(Constant.USER_ID, session.getData(Constant.ID));


        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();

                        ArrayList<Channel> channels = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {
                                Channel channel = g.fromJson(jsonObject1.toString(), Channel.class);
                                channels.add(channel);
                            } else {
                                break;
                            }
                        }
                        channelAdapter = new ChannelAdapter(activity, channels);
                        recyclerView.setAdapter(channelAdapter);
                        mSwipeRefreshLayout.setRefreshing(false);
                        checkgroups = false;


                    }
                    else {
                        Toast.makeText(getActivity(), ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        }, activity, Constant.CHANNEL_LIST_URL, params, true);
    }

    private void GroupList()
    {
        Map<String, String> params = new HashMap<>();

        params.put(Constant.USER_ID, session.getData(Constant.ID));


        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean(Constant.SUCCESS)) {

                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();
                        ArrayList<Group> groups = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {

                                Group group = g.fromJson(jsonObject1.toString(), Group.class);

                                groups.add(group);
                            } else {
                                break;
                            }
                        }
                        groupAdapter = new GroupAdapter(activity, groups);
                        recyclerView.setAdapter(groupAdapter);
                        mSwipeRefreshLayout.setRefreshing(false);
                        checkgroups = true;


                    }
                    else {
                        Toast.makeText(getActivity(), ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        }, activity, Constant.GROUPS_LIST_URL, params, true);
    }
}