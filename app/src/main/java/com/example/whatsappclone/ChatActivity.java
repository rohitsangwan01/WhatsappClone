package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatsappclone.Adapters.ChattingAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {
    TextView chatUsername;
    RecyclerView recyclerView;
    String message;
    ArrayList<ChatModel> mChat;
    FloatingActionButton sendButton;
    EditText editText;
    ChattingAdapter chattingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_chat);

        chatUsername= findViewById(R.id.chatUsername);
        sendButton = findViewById(R.id.sendButton);
        editText = findViewById(R.id.editText);
        final String name= getIntent().getStringExtra("name");
        final String userId = getIntent().getStringExtra("userId");
        chatUsername.setText(name);


//        // Populate dummy messages in List, you can implement your code here
//        ArrayList<MessageModel> messagesList = new ArrayList<>();
//        for (int i=0;i<10;i++) {
//            int b = i % 2 == 0 ? ChattingAdapter.MESSAGE_TYPE_IN : ChattingAdapter.MESSAGE_TYPE_OUT;
//            if(b==ChattingAdapter.MESSAGE_TYPE_IN){
//                message = "Hey "+name;
//            }else{
//                message = "yes "+ FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
//            }
//            messagesList.add(new MessageModel(message, b));
//        }
        //ChattingAdapter adapter = new ChattingAdapter(this, messagesList);
        //recyclerView.setAdapter(adapter);


        recyclerView = findViewById(R.id.recycler_chat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        readMessages(FirebaseAuth.getInstance().getUid(),userId);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().equals("")){
                    editText.setError("Please write a message First");
                    editText.requestFocus();
                }else{
                    HashMap<String,Object> mesg = new HashMap<>();
                    mesg.put("sender",FirebaseAuth.getInstance().getCurrentUser().getUid());
                    mesg.put("reciever",userId);
                    mesg.put("message",editText.getText().toString());
                   // mesg.put("messageType",ChattingAdapter.MESSAGE_TYPE_IN);
                    mesg.put("ChatWith",FirebaseAuth.getInstance().getCurrentUser().getDisplayName()+"/"+name);
                    FirebaseDatabase.getInstance().getReference().child("Chats").push().setValue(mesg);
                    editText.setText("");
                }
            }
        });

    }

    public void readMessages(final String myid, final String userid){
        mChat = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    ChatModel chat = snapshot.getValue(ChatModel.class);

                    if(chat.getReciever().equals(myid) && chat.getSender().equals(userid) ||
                    chat.getReciever().equals(userid) && chat.getSender().equals(myid)){

                        mChat.add(chat);
                    }
                    chattingAdapter = new ChattingAdapter(ChatActivity.this,mChat);
                    recyclerView.setAdapter(chattingAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}