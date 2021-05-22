package com.example.baby_shark;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.baby_shark.OOP.InForResponseStadium;
import com.example.baby_shark.SendNotificationPack.APIService;
import com.example.baby_shark.SendNotificationPack.Client;
import com.example.baby_shark.SendNotificationPack.Data;
import com.example.baby_shark.SendNotificationPack.MyResponse;
import com.example.baby_shark.SendNotificationPack.NotificationSender;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static android.content.ContentValues.TAG;

public class NotificationOwnerAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<InforBookStadium> inforBookStadiumList;

    public NotificationOwnerAdapter(Context context, int layout, List<InforBookStadium> inforBookStadiumList) {
        this.context = context;
        this.layout = layout;
        this.inforBookStadiumList = inforBookStadiumList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public List<InforBookStadium> getInforBookStadiumList() {
        return inforBookStadiumList;
    }

    public void setInforBookStadiumList(List<InforBookStadium> inforBookStadiumList) {
        this.inforBookStadiumList = inforBookStadiumList;
    }

    @Override
    public int getCount() {
        return inforBookStadiumList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{//sử dụng holder để tối ưu cho listview
        TextView txtName,txtDescribe,txtPhone,txtTimeS,txtTimeE,txtDay;
        Button btnAccept, btnCall, btnCancel;
    }
    DatabaseReference reference;
    FirebaseUser user;
    String userID;
    APIService apiService;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        reference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        ViewHolder holder;
        FirebaseMessaging.getInstance().subscribeToTopic("new");
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        if (convertView == null){
            //lấy phần context nào
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder = new ViewHolder();
            //ánh xạ view
            holder.txtDay = (TextView) convertView.findViewById(R.id.textViewNTFDay);
            holder.txtName = (TextView) convertView.findViewById(R.id.textViewNTFName);
            holder.txtDescribe = (TextView) convertView.findViewById(R.id.textViewNTFDescribe);
            holder.txtPhone = (TextView) convertView.findViewById(R.id.textViewNTFPhone);
            holder.txtTimeS = (TextView) convertView.findViewById(R.id.textViewNTFTimeStart) ;
            holder.txtTimeE = (TextView) convertView.findViewById(R.id.textViewNTFTimeEnd);
            holder.btnAccept = (Button) convertView.findViewById(R.id.buttonNTFConfirm);
            holder.btnCall = (Button) convertView.findViewById(R.id.buttonNTFCall);
            holder.btnCancel = (Button) convertView.findViewById(R.id.buttonNTFCancel);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        //gán giá trị
        InforBookStadium inforBookStadium = inforBookStadiumList.get(position);
        holder.txtDay.setText(inforBookStadium.getDayPlay());
        holder.txtName.setText(inforBookStadium.getNamePlay());
        holder.txtDescribe.setText(inforBookStadium.getDescribePlay());
        holder.txtPhone.setText(inforBookStadium.getPhonePlay());
        holder.txtTimeS.setText(inforBookStadium.getTimeStart());
        holder.txtTimeE.setText(inforBookStadium.getTimeEnd());
        String phone = (String) holder.txtPhone.getText();
        String day = (String) holder.txtDay.getText();
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child("AccountOwnerStadium").child(userID).child("-waitingInForBookStadium").orderByChild("phonePlay").equalTo(phone).addChildEventListener(new ChildEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String name = snapshot.child("namePlay").getValue(String.class);
                        String day = snapshot.child("dayPlay").getValue(String.class);
                        String phone = snapshot.child("phonePlay").getValue(String.class);
                        String describe = snapshot.child("describePlay").getValue(String.class);
                        String timeS = snapshot.child("timeStart").getValue(String.class);
                        String timeE = snapshot.child("timeEnd").getValue(String.class);
                        String email = snapshot.child("emailPlay").getValue(String.class);
                        InforBookStadium inforBookStadium = new InforBookStadium(day,timeS,timeE,name,phone,describe,email);
                        reference.child("AccountOwnerStadium").child(userID).child("-inForBookStadium").push().setValue(inforBookStadium);
                        /////


                        //
                        reference.child("AccountOwnerStadium").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange( DataSnapshot snapshot) {
                                AccountOwnerStadium accountOwnerStadium = snapshot.getValue(AccountOwnerStadium.class);
                                String name = accountOwnerStadium.getName();
                                String picture = accountOwnerStadium.getPicture();
                                Log.d(TAG,"clmcsacsacsac: " + picture);
                                reference.child("AccountBookStadium").orderByChild("email").equalTo(email).addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {
                                        String key= snapshot.getKey();
                                        InForResponseStadium inForResponseStadium = new InForResponseStadium(name,day,describe,timeS,timeE,picture);
                                        reference.child("AccountBookStadium").child(key).child("-inForBookStadium").push().setValue(inForResponseStadium);
                                        reference.child("AccountBookStadium").child(key).child("Tokens").child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                                String usertoken=snapshot.getValue(String.class);
                                                sendNotifications(usertoken,name,"đã chấp nhận");
                                            }

                                            @Override
                                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onChildChanged(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {

                                    }

                                    @Override
                                    public void onChildRemoved(@NonNull  DataSnapshot snapshot) {

                                    }

                                    @Override
                                    public void onChildMoved(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {

                                    }

                                    @Override
                                    public void onCancelled(@NonNull  DatabaseError error) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled( DatabaseError error) {

                            }
                        });

                        holder.btnAccept.setVisibility(View.INVISIBLE);
                        snapshot.getRef().removeValue();
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
            }
        });
        int permisson_callphone = ContextCompat.checkSelfPermission(convertView.getContext(), Manifest.permission.CALL_PHONE);
        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+ phone));
                if (permisson_callphone != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE},1);
                }
                else {
                    context.startActivity(intent);
                }

            }
        });

        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child("AccountOwnerStadium").child(userID).child("-waitingInForBookStadium").orderByChild("phonePlay").equalTo(phone).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                        String key = snapshot.getKey();
                        Log.d(TAG,"keyy " + key);
                        reference.child("AccountOwnerStadium").child(userID).child("-waitingInForBookStadium").child(key).removeValue();
                        Toast.makeText(context, "Hủy thành công", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });
        return convertView;
    }

    public void sendNotifications(String usertoken, String title, String message) {

        Data data = new Data(title, message);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, retrofit2.Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(getContext(), "Failed ", Toast.LENGTH_LONG);
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }

}
