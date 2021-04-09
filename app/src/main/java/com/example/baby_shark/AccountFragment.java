package com.example.baby_shark;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AccountFragment extends Fragment {
    Button btnLogout;
    TextView txtCreateOwnerStadium;

    ImageView imgUser;
    TextView txtNameUser, txtPhoneUser, txtEmailUser;

    //firebase
    FirebaseUser user;
    DatabaseReference reference;

    String userID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container,false);

        //ánh xạ
        btnLogout = (Button) view.findViewById(R.id.buttonLogoutUser) ;
        txtCreateOwnerStadium = (TextView) view.findViewById(R.id.textviewCreateOwnerStadium);
        imgUser = (ImageView) view.findViewById(R.id.imageViewUser);
        txtNameUser = (TextView) view.findViewById(R.id.textViewNameUser);
        txtPhoneUser = (TextView) view.findViewById(R.id.textviewPhoneUser);
        txtEmailUser = (TextView) view.findViewById(R.id.textViewEmailUser);

        //lấy dữ liệu theo ID
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("AccountBookStadium");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AccountBookStadium accountProfile = snapshot.getValue(AccountBookStadium.class);
                if (accountProfile != null){
                    String name = accountProfile.getName();
                    String email = accountProfile.getEmail();
                    String phone = accountProfile.getPhone();

                    //gán vào
                    txtNameUser.setText(name);
                    txtPhoneUser.setText(phone);
                    txtEmailUser.setText(email);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
            }
        });
        //tạo owner
        txtCreateOwnerStadium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),RegisterOwnerStadium.class));
            }
        });

        //log out
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(),Login.class));
                getActivity().finish();
            }
        });
        return view;
    }
}