package com.example.baby_shark.Account.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.baby_shark.Account.OOP.AccountBookStadium;
import com.example.baby_shark.Account.Activity.Login;
import com.example.baby_shark.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class AccountFragment extends Fragment {
    Button btnLogout,btnUpdateAccount;
    ImageView imgUser;
    TextView txtNameUser, txtPhoneUser, txtEmailUser;
    CardView crvInfor;
    EditText edtUpdateName,edtUpdatePhone,edtUpdateEmail;

    //firebase
    FirebaseUser user;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    String userID;

    //load ảnh
    Uri imageUri;
    FirebaseStorage storage ;
    StorageReference storageReference;

    //
    String name1;
    String phone1 ;
    String email1 ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container,false);

        //ánh xạ
        btnLogout = (Button) view.findViewById(R.id.buttonLogoutUser) ;
        imgUser = (ImageView) view.findViewById(R.id.imageViewUser);
        txtNameUser = (TextView) view.findViewById(R.id.textViewNameUser);
        txtPhoneUser = (TextView) view.findViewById(R.id.textviewPhoneUser);
        txtEmailUser = (TextView) view.findViewById(R.id.textViewEmailUser);
        crvInfor = (CardView) view.findViewById(R.id.cardViewInforAccount);

        //lấy dữ liệu theo ID
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("AccountBookStadium");
        userID = user.getUid();
        //lấy dữ liệu ảnh
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AccountBookStadium accountProfile = snapshot.getValue(AccountBookStadium.class);
                if (accountProfile != null){
                    String name = accountProfile.getName();
                    String email = accountProfile.getEmail();
                    String phone = accountProfile.getPhone();
                    String picture = accountProfile.getPicture();

                    //gán vào
                    txtNameUser.setText(name);
                    txtPhoneUser.setText(phone);
                    txtEmailUser.setText(email);
                    Picasso.with(getActivity()).load(picture).into(imgUser);

                    name1 = name;
                    phone1 = phone;
                    email1 = email;

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
            }
        });

        //log out
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().finish();
            }
        });

        //sự kiện chọn hình
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chọn ảnh
                ChoosePicture();

            }
        });
        //
        crvInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_update_infor_account);
                dialog.show();
                //
                edtUpdateName = (EditText) dialog.findViewById(R.id.edittextUpdateNameAccount);
                edtUpdatePhone = (EditText) dialog.findViewById(R.id.edittextUpdatePhoneAccount);
                edtUpdateEmail = (EditText) dialog.findViewById(R.id.edittextUpdateEmailAccount);
                btnUpdateAccount = (Button) dialog.findViewById(R.id.buttonUpdateAccount) ;
                //
                edtUpdateName.setText(name1);
                edtUpdatePhone.setText(phone1);
                edtUpdateEmail.setText(email1);

                //
                btnUpdateAccount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String updName = edtUpdateName.getText().toString();
                        String updPhone = edtUpdatePhone.getText().toString();
                        String updEmail = edtUpdateEmail.getText().toString();

                        reference.child(userID).child("name").setValue(updName);
                        reference.child(userID).child("phone").setValue(updPhone);
                        reference.child(userID).child("email").setValue(updEmail);

                        user.updateEmail(updEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getActivity(), "cập nhật thành công", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }
                                else {
                                    Toast.makeText(getActivity(), "không thành công", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });
        return view;
    }
    //hàm lấy ảnh
    private void ChoosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    //hàm kiểm tra lấy ảnh thành công hay không
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            imgUser.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("upload ảnh");
        progressDialog.show();
        String randomkey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/" + randomkey);
        riversRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "upload thành công", Toast.LENGTH_SHORT).show();
//                String url = taskSnapshot.getStorage().getDownloadUrl().toString();
                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        reference.child(userID).child("picture").setValue(uri.toString());
                    }
                });

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "upload thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                        progressDialog.setMessage("Percentage" + (int) progressPercent + "%");
                    }
                });

    }
}