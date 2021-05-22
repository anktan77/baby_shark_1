package com.example.baby_shark;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.baby_shark.SendNotificationPack.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ListOwnerFragment extends Fragment {
    ListView lvNTFOwner;
    ArrayList<InforBookStadium> inforBookStadiumArrayList;
    NotificationOwnerAdapter adapter;
    Button btnAccept, btnCall;

    //data
    DatabaseReference reference;
    FirebaseUser user;
    String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_owner,container,false);

        //ánh xạ
        lvNTFOwner = (ListView) view.findViewById(R.id.listviewNTFOwner);

        //data
        reference = FirebaseDatabase.getInstance().getReference("AccountOwnerStadium");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        //array
        inforBookStadiumArrayList = new ArrayList<>();
        adapter = new NotificationOwnerAdapter(getActivity(),R.layout.list_notification_owner_book_stadium,inforBookStadiumArrayList);

        lvNTFOwner.setAdapter(adapter);

        //sự kiện với data
        reference.child(userID).child("-waitingInForBookStadium").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String name = snapshot.child("namePlay").getValue(String.class);
                String day = snapshot.child("dayPlay").getValue(String.class);
                String phone = snapshot.child("phonePlay").getValue(String.class);
                String describe = snapshot.child("describePlay").getValue(String.class);
                String timeS = snapshot.child("timeStart").getValue(String.class);
                String timeE = snapshot.child("timeEnd").getValue(String.class);
                String email = snapshot.child("emailPlay").getValue(String.class);
                Log.d(TAG, "onChildAdded: "+ name);
                inforBookStadiumArrayList.add(new InforBookStadium(day, timeS, timeE, name, phone, describe,email));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        UpdateToken();
        return view;
    }

    private void UpdateToken(){

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        firebaseUser.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();
                            Token token = new Token(idToken);
                            FirebaseDatabase.getInstance().getReference("AccountOwnerStadium").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Tokens").setValue(token);
                        } else {
                            // Handle error -> task.getException();
                        }
                    }
                });

    }

}