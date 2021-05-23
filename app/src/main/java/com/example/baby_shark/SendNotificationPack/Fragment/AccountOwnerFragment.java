package com.example.baby_shark.SendNotificationPack.Fragment;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baby_shark.Owner.OOP.AccountOwnerStadium;
import com.example.baby_shark.Owner.Adapter.ListPictureAdapter;
import com.example.baby_shark.Owner.Activity.LoginOwnerStadium;
import com.example.baby_shark.Owner.OOP.ListPicture;
import com.example.baby_shark.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
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

import java.util.ArrayList;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class AccountOwnerFragment extends Fragment implements ListPictureAdapter.OnNoteListener {
    Button btnLogout;
    TextView txtCreateOwnerStadium;

    ImageView imgOwner;
    TextView txtNameOwner, txtPhoneOwner, txtEmailOwner;

    //firebase
    FirebaseUser user;
    DatabaseReference reference;

    String userID;

    //load ảnh
    Uri imageUri,listImageUri;
    FirebaseStorage storage ;
    StorageReference storageReference;

    //card view
    CardView crvInfor,crvPicture;
    RecyclerView ryvListPicture;

    //update
    EditText edtNameStadium, edtPhone, edtAddress, edtEmail;
    Button btnUpdateInfor;
    String name1;
    String phone1 ;
    String address1;
    String email1;

    private static final int PICK_IMAGE_MULTIPLE = 2;
    ArrayList<String> imageList;
    ListPictureAdapter listPictureAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_owner,container,false);
        //ánh xạ
        btnLogout = (Button) view.findViewById(R.id.buttonLogoutOwner) ;
        imgOwner = (ImageView) view.findViewById(R.id.imageViewOwner);
        txtNameOwner = (TextView) view.findViewById(R.id.textViewNameOwner);
        txtPhoneOwner = (TextView) view.findViewById(R.id.textviewPhoneOwner);
        txtEmailOwner = (TextView) view.findViewById(R.id.textViewEmailOwner);
        crvInfor = (CardView) view.findViewById(R.id.cardViewInfor);
        crvPicture = (CardView) view.findViewById(R.id.cardViewPicture);
        ryvListPicture = (RecyclerView) view.findViewById(R.id.recycleviewListPicture);

        //lấy dữ liệu theo ID
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("AccountOwnerStadium");
        userID = user.getUid();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AccountOwnerStadium accountProfile = snapshot.getValue(AccountOwnerStadium.class);
                if (accountProfile != null){
                    String name = accountProfile.getName();
                    String email = accountProfile.getEmail();
                    String phone = accountProfile.getPhone();
                    String picture = accountProfile.getPicture();
                    String address = accountProfile.getAddress();

                    //gán vào
                    txtNameOwner.setText(name);
                    txtPhoneOwner.setText(phone);
                    txtEmailOwner.setText(email);
                    Picasso.with(getActivity()).load(picture).into(imgOwner);

                    //
                    name1 = name;
                    phone1 = phone;
                    address1 = address;
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
                startActivity(new Intent(getActivity(), LoginOwnerStadium.class));
                getActivity().finish();
            }
        });

        //chọn ảnh
        imgOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chọn ảnh
                ChoosePicture();
            }
        });

        crvPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectPicture();

            }
        });

        crvInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_update_infor_owner);
                dialog.show();
                //
                edtNameStadium = (EditText) dialog.findViewById(R.id.edittextUpdateNameOwner);
                edtPhone = (EditText) dialog.findViewById(R.id.edittextUpdatePhoneOwner);
                edtAddress = (EditText) dialog.findViewById(R.id.edittextUpdateAddressOwner);
                edtEmail = (EditText) dialog.findViewById(R.id.edittextUpdateEmailOwner);
                btnUpdateInfor = (Button) dialog.findViewById(R.id.buttonUpdateOwner);
                //

                edtNameStadium.setText(name1);
                edtPhone.setText(phone1);
                edtAddress.setText(address1);
                edtEmail.setText(email1);
                //
                btnUpdateInfor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String updName = edtNameStadium.getText().toString();
                        String updPhone = edtPhone.getText().toString();
                        String updAddress = edtAddress.getText().toString();
                        String updEmail = edtEmail.getText().toString();
                        //
                        reference.child(userID).child("name").setValue(updName);
                        reference.child(userID).child("phone").setValue(updPhone);
                        reference.child(userID).child("email").setValue(updEmail);
                        reference.child(userID).child("address").setValue(updAddress);

                        //
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

        ryvListPicture.setLayoutManager(new GridLayoutManager(getActivity(),3));
        imageList = new ArrayList<>();
        listPictureAdapter = new ListPictureAdapter(imageList, getActivity(),this);
        reference.child(userID).child("-folderPicture").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                for (DataSnapshot find : snapshot.getChildren()){
                    String picture = find.child("namePic").getValue(String.class);
                    imageList.add(picture);

                }
                listPictureAdapter.notifyDataSetChanged();
                ryvListPicture.setAdapter(listPictureAdapter);
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

        return view;
    }

    private void SelectPicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_MULTIPLE);
    }


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
            imgOwner.setImageURI(imageUri);
            uploadPicture();
        }
        if (requestCode == PICK_IMAGE_MULTIPLE & resultCode == RESULT_OK  && data.getClipData() != null){
            int countClipData = data.getClipData().getItemCount();
            int currentImageSelect = 0;
            while (currentImageSelect < countClipData){
                listImageUri = data.getClipData().getItemAt(currentImageSelect).getUri();
                uploadListPicture();
                currentImageSelect ++;
            }

        }
    }

    private void uploadListPicture() {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("upload ảnh");
        progressDialog.show();
        String randomkey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/" + randomkey);
        riversRef.putFile(listImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "upload thành công", Toast.LENGTH_SHORT).show();
                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ListPicture listPicture = new ListPicture(uri.toString());
                        reference.child(userID).child("-folderPicture").push().setValue(listPicture);
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

    @Override
    public void onNoteClick(int position) {
        String pic = imageList.get(position);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("AccountOwnerStadium");
        userID = user.getUid();
        reference.child(userID).child("-folderPicture").orderByChild("namePic").equalTo(pic).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded( DataSnapshot snapshot, String previousChildName) {
                String key = snapshot.getKey();

                reference.child(userID).child("-folderPicture").child(key).removeValue();
                Toast.makeText(getActivity(),"Xóa ảnh thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged( DataSnapshot snapshot,  String previousChildName) {

            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved( DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}