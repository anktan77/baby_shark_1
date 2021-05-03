package com.example.baby_shark;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {

    EditText mHovaten,mEmail,mMatkhau,mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    ProgressBar progressBar;
    //
    FirebaseAuth fAuth;
    DatabaseReference reference;
    FirebaseUser user;
    FirebaseFirestore fstore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //setvalue

        mHovaten = (EditText) findViewById(R.id.edittextdangkyhovaten);
        mEmail = (EditText)findViewById(R.id.edittextEmail);
        mMatkhau = (EditText)findViewById(R.id.edittextdangkymatkhau);
        mPhone = (EditText)findViewById(R.id.edittextphone);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        mRegisterBtn = (Button) findViewById(R.id.btnregisterxacnhan);
        mLoginBtn = (TextView) findViewById(R.id.createtext2);
        //
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        //
        user = FirebaseAuth.getInstance().getCurrentUser();
//        reference = FirebaseDatabase.getInstance().getReference("Users");
//        userID = user.getUid();

//        if (fAuth.getCurrentUser() != null) {
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            finish();
//        }
        mRegisterBtn.setOnClickListener((V) -> {
                    String Hovaten = mHovaten.getText().toString().trim();
                    String Email = mEmail.getText().toString().trim();
                    String matkhau = mMatkhau.getText().toString().trim();
                    String phone = mPhone.getText().toString().trim();
                    if (TextUtils.isEmpty(Hovaten)) {
                        mHovaten.setError("Hãy nhập Họ và Tên");
                        return;
                    }
                    if (Hovaten.length() < 5) {
                        mHovaten.setError("Lỗi độ dài");
                        return;
                    }
                    if (TextUtils.isEmpty(Email)) {
                        mEmail.setError("Hãy nhập Email");
                        return;
                    }
                    if (TextUtils.isEmpty(matkhau)) {
                        mMatkhau.setError("Hãy nhập mật khẩu");
                        return;
                    }
                    if (matkhau.length() < 6) {
                        mMatkhau.setError("Lỗi độ dài mật khẩu không được quá 6");
                        return;
                    }
                    if (TextUtils.isEmpty(phone)) {
                        mPhone.setError("Hãy nhập số điện thoại");
                        return;
                    }
                    if (phone.length() < 10) {
                        mPhone.setError("quá 11 số!!!");
                        return;
                    }
                    progressBar.setVisibility(View.VISIBLE);
                    fAuth.createUserWithEmailAndPassword(Email,matkhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                AccountBookStadium accountBookStadium = new AccountBookStadium(Hovaten,Email, phone,"");
                                reference = FirebaseDatabase.getInstance().getReference("AccountBookStadium");
                                        reference.child(FirebaseAuth.getInstance().getUid()).setValue(accountBookStadium)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(Register.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                        else {
                                            Toast.makeText(Register.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                            }
                            else {
                                Toast.makeText(Register.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
           });

            mLoginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
            });
        }

}