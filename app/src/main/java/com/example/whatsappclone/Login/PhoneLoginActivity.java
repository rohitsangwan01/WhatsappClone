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

public class PhoneLoginActivity extends AppCompatActivity {
    EditText edtPhoneNumber;
    Button btnPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        edtPhoneNumber = findViewById(R.id.edtPhonrNumber);
        btnPhoneNumber = findViewById(R.id.btnPhoneNumber);



        btnPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = edtPhoneNumber.getText().toString().trim();

                if(mobile.isEmpty() || mobile.length()<10 || mobile.length()>10){
                    edtPhoneNumber.setError("Please Type a Valid Phone Number");
                    edtPhoneNumber.requestFocus();
                    return;
                }

                Intent intent = new Intent(PhoneLoginActivity.this,VerifyOtpActivity.class);
                intent.putExtra("mobile",mobile);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent intent = new Intent(PhoneLoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }
}