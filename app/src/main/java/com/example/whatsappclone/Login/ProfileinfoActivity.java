package com.example.whatsappclone.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.whatsappclone.MainActivity;
import com.example.whatsappclone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileinfoActivity extends AppCompatActivity {
    EditText edtUsername;
    Button btnFinalNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileinfo);
        edtUsername = findViewById(R.id.edtUsername);
        btnFinalNext = findViewById(R.id.btnFinalNext);


        btnFinalNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = getIntent().getStringExtra("mobile");
                if(edtUsername.getText().toString().isEmpty()){
                    edtUsername.setError("Please set an error");
                    edtUsername.requestFocus();
                    return;
                }

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(edtUsername.getText().toString()).build();
                user.updateProfile(profileUpdates);

                FirebaseDatabase.getInstance().getReference().child("ChatUsers")
                        .child("UserDetails").child(user.getUid()).child("Name").setValue(edtUsername.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("ChatUsers")
                        .child("UserDetails").child(user.getUid()).child("PhoneNumber").setValue(mobile);

                Intent intent = new Intent(ProfileinfoActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}