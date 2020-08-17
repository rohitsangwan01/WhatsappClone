package com.example.whatsappclone.ViewHolder;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclone.R;

public class ChatViewHolder extends RecyclerView.ViewHolder{
    TextView textUsername;
    ConstraintLayout parentLayout;
    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        textUsername = itemView.findViewById(R.id.textUsername);
        parentLayout = itemView.findViewById(R.id.parentLayout);

        textUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public TextView getTextUsername() {
        return textUsername;
    }

    public ConstraintLayout getParentLayout() {
        return parentLayout;
    }
}
