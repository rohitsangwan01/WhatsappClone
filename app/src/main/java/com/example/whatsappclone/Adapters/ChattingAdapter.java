package com.example.whatsappclone.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclone.ChatModel;
import com.example.whatsappclone.MessageModel;
import com.example.whatsappclone.R;
import com.example.whatsappclone.ViewHolder.ChattingViewHolder;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChattingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context context;
    ArrayList<ChatModel> list;
    public static final int MESSAGE_TYPE_IN = 1;
    public static final int MESSAGE_TYPE_OUT = 2;
    int a;

    public ChattingAdapter(Context context, ArrayList<ChatModel> list) {
        this.context = context;
        this.list = list;
    }

    private class MessageInViewHolder extends RecyclerView.ViewHolder {

        TextView textMessage;
        MessageInViewHolder(@NonNull View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.textMessage);

        }
        void bind(int position) {
            ChatModel messageModel = list.get(position);
            textMessage.setText(messageModel.getMessage());
        }
    }

    private class MessageOutViewHolder extends RecyclerView.ViewHolder {

        TextView textMessage;
        MessageOutViewHolder(final View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.textMessage);

        }
        void bind(int position) {
            ChatModel messageModel = list.get(position);
            textMessage.setText(messageModel.getMessage());

        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MESSAGE_TYPE_IN) {
            return new MessageInViewHolder(LayoutInflater.from(context).inflate(R.layout.message_item_in, parent, false));
        }
        return new MessageOutViewHolder(LayoutInflater.from(context).inflate(R.layout.message_item_out, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        if (list.get(position).getMessageType() == MESSAGE_TYPE_IN) {
//            ((MessageInViewHolder) holder).bind(position);
//        } else {
//            ((MessageOutViewHolder) holder).bind(position);
//        }
        if(a == 1){
            ((MessageInViewHolder) holder).bind(position);
        }
        if(a == 2){
            ((MessageOutViewHolder) holder).bind(position);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).getSender().equals(FirebaseAuth.getInstance().getUid())){
            a = 1;
            return MESSAGE_TYPE_IN;
        }else{
            a = 2;
            return MESSAGE_TYPE_OUT;
        }

    }
}
