package fpoply.thanhdvph33594.lab1_ph33594;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPphoneNumberActivity extends AppCompatActivity {
 private EditText edtPhoneNumber,edtOtp;
 private Button btnGetOtp,btnOTP;
 private FirebaseAuth mAuth;
 private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
 private String mVerificationId,numberPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpphone_number);
        mAuth =FirebaseAuth.getInstance();
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtOtp = findViewById(R.id.edtOTP);
        btnGetOtp=findViewById(R.id.btnGetOtp);
        btnOTP=findViewById(R.id.btnOtp);

        btnOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOTP(edtOtp.getText().toString());
            }
        });

        mCallbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                edtOtp.setText(phoneAuthCredential.getSmsCode());
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                mVerificationId=s;
            }
        };
        btnGetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberPhone=edtPhoneNumber.getText().toString();
                getOTP(numberPhone);

            }
        });
    }
    private void getOTP(String numberPhone){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84"+numberPhone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void verifyOTP(String code){
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mVerificationId,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(OTPphoneNumberActivity.this,"Đăng nhập thành công",Toast.LENGTH_LONG).show();
                    FirebaseUser user = task.getResult().getUser();
                    startActivity(new Intent(OTPphoneNumberActivity.this, MainActivity.class));


                }else{
                    Log.w(TAG, "signInWithCredential:failure",task.getException());
                    Toast.makeText(OTPphoneNumberActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}