package com.example.baby_shark.SendNotificationPack;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseIdService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s)
    {
        super.onNewToken(s);
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
//        FirebaseInstallations.getInstance().getToken(true).addOnCompleteListener();
        if(firebaseUser!=null){
            updateToken(s);
        }
    }
    private void updateToken(String refreshToken){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("AccountOwnerStadium");
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String userID = firebaseUser.getUid();
        Token token1 = new Token(refreshToken);
//        firebaseUser.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token1);
        reference.child(userID).child("Tokens").setValue(token1);
    }
}
