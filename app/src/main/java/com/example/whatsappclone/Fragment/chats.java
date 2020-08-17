package com.example.whatsappclone.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.whatsappclone.Adapters.ChatAdapter;
import com.example.whatsappclone.ContactsActivity;
import com.example.whatsappclone.Login.PhoneLoginActivity;
import com.example.whatsappclone.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class chats extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    FloatingActionButton buttonMessages;
    SwipeRefreshLayout swipeLayout;
    RecyclerView chatList;
    ArrayList<DataSnapshot> NumOfUser;
    int a;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    public chats() {
    }
    public static chats newInstance(String param1, String param2) {
        chats fragment = new chats();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_chat, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case (R.id.action_view_contact):
                Intent intent = new Intent(getContext(), ContactsActivity.class);
                startActivity(intent);
                break;
            case (R.id.btnLogout):
                FirebaseAuth.getInstance().signOut();
                Intent intent2 = new Intent(getContext(), PhoneLoginActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        View view =  inflater.inflate(R.layout.fragment_chats, container, false);

        buttonMessages = view.findViewById(R.id.buttonMessages);
        chatList = view.findViewById(R.id.chatList);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(android.R.color.darker_gray) ,
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_orange_dark));

        buttonMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ContactsActivity.class);
                startActivity(intent);
            }
        });



        NumOfUser = new ArrayList();
        FirebaseDatabase.getInstance().getReference().child("ChatUsers").child("UserDetails").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                NumOfUser.add(dataSnapshot);
                a = NumOfUser.size();
                chatList.setAdapter(new ChatAdapter(a,getContext()));
                chatList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                try{
                    int i = 0;
                    for(DataSnapshot snapshot:NumOfUser){
                        if(snapshot.getKey().equals(dataSnapshot.getKey())){
                            NumOfUser.remove(i);
                        }
                        i++;
                        a = NumOfUser.size();
                        chatList.setAdapter(new ChatAdapter(a,getContext()));
                        chatList.setLayoutManager(new LinearLayoutManager(getContext(),
                                LinearLayoutManager.VERTICAL,false));
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







        return view;
    }

    @Override
    public void onRefresh() {

        swipeLayout.setRefreshing(true);
        FirebaseDatabase.getInstance().getReference().child("ChatUsers").child("UserDetails").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot1, @Nullable String s) {
                ArrayList ex = new ArrayList<>();
                ex.add(dataSnapshot1);
                int b = ex.size();
                if(b>a){
                    chatList.setAdapter(new ChatAdapter(b,getContext()));
                    chatList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                    swipeLayout.setRefreshing(false);
                }else {
                    swipeLayout.setRefreshing(false);
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}