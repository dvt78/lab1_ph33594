package fpoply.thanhdvph33594.lab1_ph33594;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Activity extends AppCompatActivity {

     private EditText  edtUser,editPassWord;
     private Button btnLogin;
     private TextView txtSignUp;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth =FirebaseAuth.getInstance();
         edtUser =findViewById(R.id.edtUserName);
         editPassWord =findViewById(R.id.edtPassword);
         btnLogin = findViewById(R.id.btnLogin);
         txtSignUp=findViewById(R.id.txtSignUp);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerSignUp();
            }
        });
    }

    private void registerSignUp() {
        Intent intent = new Intent(Login_Activity.this, Register_Activity.class);
        startActivity(intent);
    }


    private void login() {
        String email,pass;
        email=edtUser.getText().toString();
        pass=editPassWord.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Vui lòng nhập Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Vui lòng nhập Pass",Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(Login_Activity.this, MainActivity.class);
                    startActivity(intent1);
                }else{
                    Toast.makeText(getApplicationContext(),"Đăng nhập không thành công",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}