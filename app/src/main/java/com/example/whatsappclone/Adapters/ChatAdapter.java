package com.example.whatsappclone.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclone.ChatActivity;
import com.example.whatsappclone.R;
import com.example.whatsappclone.ViewHolder.ChatViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    ArrayList NumberOfUsers;
    Context mContext;
    int b;
    ArrayList names;

    public ChatAdapter(int b,Context mContext) {
        this.b = b;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View vw = layoutInflater.inflate(R.layout.chat_item,parent,false);
        ChatViewHolder ch = new ChatViewHolder(vw);
        return ch;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChatViewHolder holder, final int position) {
        NumberOfUsers = new ArrayList();
        names = new ArrayList();
        try{
            FirebaseDatabase.getInstance().getReference().child("ChatUsers")
                    .child("UserDetails").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    NumberOfUsers.add(dataSnapshot.getKey());

                    names.add(dataSnapshot.child("Name").getValue());
                    holder.getTextUsername().setText(names.get(position)+"");


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

        }catch (Exception e){
            e.printStackTrace();
        }
        holder.getParentLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(view.getContext(), position+"", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(mContext, ChatActivity.class);
                intent.putExtra("name",holder.getTextUsername().getText().toString());
                intent.putExtra("userId",NumberOfUsers.get(position)+"");

                mContext.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return b;
    }
}
