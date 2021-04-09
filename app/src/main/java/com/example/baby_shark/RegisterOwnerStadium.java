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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterOwnerStadium extends AppCompatActivity {
    EditText edtName, edtEmail, edtpassword, edtPhone;
    Button btnConfirm;
    ProgressBar progressBar;
    //firebase
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_owner_stadium);

        //ánh xạ
        edtName = (EditText) findViewById(R.id.edittextFullNameRegisterOwnerStadium);
        edtEmail = (EditText) findViewById(R.id.edittextEmailRegisterOwnerStadium);
        edtpassword = (EditText) findViewById(R.id.edittextPasswordRegisterOwnerStadium);
        edtPhone = (EditText) findViewById(R.id.edittextPhoneRegisterOwnerStadium);
        btnConfirm = (Button) findViewById(R.id.buttonConfirmRegisterOwnerStadium);
        progressBar = (ProgressBar) findViewById(R.id.progressbarRegisterOwnerStadium);
        //
        fAuth = FirebaseAuth.getInstance();

        //sự kiện đăng ký
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lấy dữ liệu
                String name = edtName.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String password = edtpassword.getText().toString().trim()+"ow";
                String phone = edtPhone.getText().toString().trim();
                //điều kiện
                if (TextUtils.isEmpty(name)) {
                    edtName.setError("Hãy nhập Họ và Tên");
                    return;
                }
                if (name.length() < 5) {
                    edtName.setError("Lỗi độ dài");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    edtEmail.setError("Hãy nhập Email");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    edtpassword.setError("Hãy nhập mật khẩu");
                    return;
                }
                if (password.length() < 6) {
                    edtpassword.setError("Lỗi độ dài mật khẩu không được quá 6");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    edtPhone.setError("Hãy nhập số điện thoại");
                    return;
                }
                if (phone.length() < 10) {
                    edtPhone.setError("quá 11 số!!!");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //tạo user firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //kiểm tra có thành công không
                        if (task.isSuccessful()){
                            AccountOwnerStadium accountOwnerStadium = new AccountOwnerStadium(name,email,phone);
                            FirebaseDatabase.getInstance().getReference("AccountOwnerStadium")
                                    .child(FirebaseAuth.getInstance().getUid()).setValue(accountOwnerStadium)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegisterOwnerStadium.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                                startActivity(new Intent(RegisterOwnerStadium.this,OwnerStadiumActivity.class));
                                            }
                                            else {
                                                Toast.makeText(RegisterOwnerStadium.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });
            }
        });
    }
}