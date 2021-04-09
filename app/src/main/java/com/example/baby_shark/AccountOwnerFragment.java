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


public class AccountOwnerFragment extends Fragment {
    Button btnLogout;
    TextView txtCreateOwnerStadium;

    ImageView imgOwner;
    TextView txtNameOwner, txtPhoneOwner, txtEmailOwner;

    //firebase
    FirebaseUser user;
    DatabaseReference reference;

    String userID;

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

        //lấy dữ liệu theo ID
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("AccountOwnerStadium");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AccountOwnerStadium accountProfile = snapshot.getValue(AccountOwnerStadium.class);
                if (accountProfile != null){
                    String name = accountProfile.getName();
                    String email = accountProfile.getEmail();
                    String phone = accountProfile.getPhone();

                    //gán vào
                    txtNameOwner.setText(name);
                    txtPhoneOwner.setText(phone);
                    txtEmailOwner.setText(email);
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
                startActivity(new Intent(getActivity(),LoginOwnerStadium.class));
                getActivity().finish();
            }
        });
        return view;
    }
}