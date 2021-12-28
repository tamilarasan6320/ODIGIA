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

import com.example.myapplication.R;
import com.example.myapplication.adapter.ChatAdapter;
import com.example.myapplication.helper.ApiConfig;
import com.example.myapplication.helper.Constant;
import com.example.myapplication.helper.Session;
import com.example.myapplication.model.Chat;
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
    public static ChatAdapter chatAdapter;
    public static ArrayList<Chat> chats;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_chat_list, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        activity = getActivity();
        session = new Session(activity);
        chats = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);

        ChatList();

        return root;
    }

    private void ChatList()
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

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {
                                Chat chat = g.fromJson(jsonObject1.toString(), Chat.class);

                                chats.add(chat);
                            } else {
                                break;
                            }
                        }
                        chatAdapter = new ChatAdapter(activity, chats);
                        recyclerView.setAdapter(chatAdapter);


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
}