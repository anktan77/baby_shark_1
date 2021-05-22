package com.example.baby_shark;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baby_shark.Adapter.RecycleViewNTFAccount;
import com.example.baby_shark.OOP.InForResponseStadium;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;


public class NotificationFragment extends Fragment {
    RecycleViewNTFAccount adapter;
    RecyclerView rycNTF;
    ArrayList<InForResponseStadium> inForResponseStadiumArrayList;
    DatabaseReference reference;
    FirebaseUser user;
    String userID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        //
        rycNTF = (RecyclerView) view.findViewById(R.id.recycleviewNTFAccount);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rycNTF.setLayoutManager(linearLayoutManager);
        rycNTF.setHasFixedSize(true);
        inForResponseStadiumArrayList = new ArrayList<>();
        adapter = new RecycleViewNTFAccount(getActivity(),inForResponseStadiumArrayList);

        //

        reference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        reference.child("AccountBookStadium").child(userID).child("-inForBookStadium").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {
                InForResponseStadium inForResponseStadium = snapshot.getValue(InForResponseStadium.class);
                String name = inForResponseStadium.getNameStadium();
                String day = inForResponseStadium.getDayPlay();
                String describe = inForResponseStadium.getDescribePlay();
                String timeS = inForResponseStadium.getTimeS();
                String timeE = inForResponseStadium.getTimeE();
                String pic = inForResponseStadium.getPicture();
                inForResponseStadiumArrayList.add(new InForResponseStadium(name,day,describe,timeS,timeE,pic));
                Collections.reverse(inForResponseStadiumArrayList);
                adapter.notifyDataSetChanged();
                rycNTF.setAdapter(adapter);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(R.drawable.chamthang)
                        .setContentTitle("đặt sân " + name +" thành công")
                        .setAutoCancel(true);
                NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0,builder.build());
            }

            @Override
            public void onChildChanged(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull  DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull  DataSnapshot snapshot,  String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

        return view;
    }
}