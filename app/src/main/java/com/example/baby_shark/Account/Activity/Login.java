package com.example.baby_shark.Account.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baby_shark.Owner.Activity.LoginOwnerStadium;
import com.example.baby_shark.R;
import com.facebook.CallbackManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText mHoten,mSDT;
    EditText mEmail,mMatkhau;
    Button mLoginBtn;
    TextView mCreateBtn,mForget, mLoginOwnerStadium;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    Context context;
    CheckBox cbxRemember;

    CallbackManager callbackManager;
    //khai báo chức năng lưu
    SharedPreferences sharedPreferences;
    ImageView imgLoginFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        //ánh xạ
        mLoginOwnerStadium = (TextView) findViewById(R.id.textViewLoginOwnerStadium);
        mForget=(TextView) findViewById(R.id.quenmatkhau);
        mEmail=(EditText) findViewById(R.id.edittexttaikhoan);
        mMatkhau=(EditText) findViewById(R.id.edittextmatkhau);
        progressBar=(ProgressBar) findViewById(R.id.progressbarlogin);
        fAuth=FirebaseAuth.getInstance();
        mLoginBtn=(Button) findViewById(R.id.btnxacnhan);
        mCreateBtn = (TextView) findViewById(R.id.createtext);
        cbxRemember = (CheckBox) findViewById(R.id.checkBoxRememberAccount);

        //
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        sharedPreferences = getSharedPreferences("dataLogin",MODE_PRIVATE);
        //lấy giá trị sau khi đã lưu vào sharepreferences
        mEmail.setText(sharedPreferences.getString("taikhoan",""));
        mMatkhau.setText(sharedPreferences.getString("matkhau",""));
        cbxRemember.setChecked(sharedPreferences.getBoolean("checked",false));

        //tự động đăng nhập
        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        //sự kiện click đăng nhập
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = mEmail.getText().toString().trim();
                String password = mMatkhau.getText().toString().trim();
                if (TextUtils.isEmpty(Email)) {
                    mEmail.setError("Hãy nhập Email");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mMatkhau.setError("Hãy nhập mật khẩu");
                    return;
                }
                if (password.length() < 6) {
                    mMatkhau.setError("Lỗi độ dài mật khẩu không được quá 6");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                fAuth.signInWithEmailAndPassword(Email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (cbxRemember.isChecked()){
                                //mờ file lưu vào
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                //lưu vào editor
                                editor.putString("taikhoan",Email);
                                editor.putString("matkhau",password);
                                //lưu trạng thái checked là true mỗi lần sau
                                editor.putBoolean("checked",true);
                                editor.commit();
                            }
                            else {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove("taikhoan");
                                editor.remove("matkhau");
                                editor.remove("checked");
                                editor.commit();
                            }
                            Toast.makeText(Login.this, "Đăng nhập thành công!!!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(Login.this, "Sai email hoặc mật khẩu" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        //đăng ký tài khoản
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
        //quên tài khoản
        mForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetEmail = new EditText((v.getContext()));
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Resetpassword ??");
                passwordResetDialog.setMessage("Enter your Email to Recieved Reset Link ");
                passwordResetDialog.setView(resetEmail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      //add link
                        String mail = resetEmail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this,"Reset Link Send To Your Email",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this,"Erorr!!! Reset Link Is Not Sent "+ e.getMessage() ,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close
                    }
                });
                passwordResetDialog.create().show();
            }
        });

        //chủ sân đăng nhập
        mLoginOwnerStadium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginOwnerStadium.class));
            }
        });
    }
}