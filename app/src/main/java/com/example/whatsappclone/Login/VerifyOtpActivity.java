package com.example.whatsappclone.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.whatsappclone.MainActivity;
import com.example.whatsappclone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOtpActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText editTextCode;
    String mVerificationId;
    String mobile;
    ProgressBar pgbar;
    Button btnFinalNext;
    Boolean login = false;
    String code,cde;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        mAuth = FirebaseAuth.getInstance();





        editTextCode = findViewById(R.id.editTextCode);
        btnFinalNext = findViewById(R.id.btnFinalNext);
        pgbar = findViewById(R.id.pgBar);

        pgbar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        mobile = intent.getStringExtra("mobile");

//        if(mobile.equals("8529151020")){
//            FirebaseAuthSettings firebaseAuthSettings = mAuth.getFirebaseAuthSettings();
//            firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber("+918529151020", "123456");
//            PhoneAuthProvider phoneAuthProvider = PhoneAuthProvider.getInstance();
//            phoneAuthProvider.verifyPhoneNumber(
//                    "+918529151020",
//                    60L,
//                    TimeUnit.SECONDS,
//                    this, /* activity */
//                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                        @Override
//                        public void onVerificationCompleted(PhoneAuthCredential credential) {
//                            login = true;
//                        }
//
//                        @Override
//                        public void onVerificationFailed(FirebaseException e) {
//
//                        }
//
//                        // ...
//                    });
//        }else{
//            sendVerificationCode(mobile);
//        }
        sendVerificationCode(mobile);

        btnFinalNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextCode.getText().toString().isEmpty() || editTextCode.getText().toString().length()<6){
                    editTextCode.setError("Please Enter 6-digit Code");
                    return;
                }
                String cd = editTextCode.getText().toString();
                verifyVerificationCode(cd);

            }
        });
    }

    private void sendVerificationCode(String code){

        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+ code,60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD
        ,mCallbacks);

    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                //Toast.makeText(VerifyOtpActivity.this, mVerificationId+"", Toast.LENGTH_SHORT).show();
                editTextCode.setText(code);
                verifyVerificationCode(code);
            }


        }
        @Override
        public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(VerifyOtpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                pgbar.setVisibility(View.GONE);
        }
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
            pgbar.setVisibility(View.GONE);
            PhoneAuthProvider.ForceResendingToken mResendToken = forceResendingToken;


        }
    };

//    public void passer(){
//        PhoneAuthCredential credential  = PhoneAuthProvider.getCredential(mVerificationId, cde);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(VerifyOtpActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            login = true;
//                            pgbar.setVisibility(View.GONE);
//
//                        } else {
//                            String message = "Somthing is wrong, we will fix it soon...";
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                message = "Invalid code entered...";
//                            }
//                            Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
//                            snackbar.setAction("Dismiss", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                }
//                            });
//                            snackbar.show();
//                            pgbar.setVisibility(View.GONE);
//                        }
//                    }
//                });
//    }



    public void verifyVerificationCode(String otp){
        PhoneAuthCredential credential  = PhoneAuthProvider.getCredential(mVerificationId, otp);
        signInWithPhoneAuthCredential(credential);

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyOtpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent intent = new Intent(VerifyOtpActivity.this, ProfileinfoActivity.class);
                            intent.putExtra("mobile",mobile);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {
                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                            Toast.makeText(VerifyOtpActivity.this, message, Toast.LENGTH_SHORT).show();
//
                        }
                    }
                });
    }
}