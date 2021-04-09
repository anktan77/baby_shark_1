package com.example.baby_shark;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginOwnerStadium extends AppCompatActivity {
    EditText edtEmail, edtPassword;
    Button btnConfirm;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_owner_stadium);
        //ánh xạ
        edtEmail = (EditText) findViewById(R.id.edittextLoginEmailOwnerStadium);
        edtPassword = (EditText) findViewById(R.id.edittextLoginPasswordOwnerStadium);
        btnConfirm = (Button) findViewById(R.id.buttonConfirmLoginOwnerStadium);
        progressBar = (ProgressBar) findViewById(R.id.progressbarloginOwnerStadium) ;
        fAuth = FirebaseAuth.getInstance();

        //sự kiện
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim()+"ow";
                if (TextUtils.isEmpty(email)) {
                    edtEmail.setError("Hãy nhập Email");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    edtPassword.setError("Hãy nhập mật khẩu");
                    return;
                }
                if (password.length() < 6) {
                    edtPassword.setError("Lỗi độ dài mật khẩu không được quá 6");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginOwnerStadium.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), OwnerStadiumActivity.class));
                        } else {
                            Toast.makeText(LoginOwnerStadium.this, "Sai email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
}