package com.example.baby_shark;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

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
        Button btnAccept, btnCall;
    }
    DatabaseReference reference;
    FirebaseUser user;
    String userID;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        reference = FirebaseDatabase.getInstance().getReference("AccountOwnerStadium");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        ViewHolder holder;
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
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(userID).child("-waitingInForBookStadium").orderByChild("phonePlay").equalTo(phone).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String name = snapshot.child("namePlay").getValue(String.class);
                        String day = snapshot.child("dayPlay").getValue(String.class);
                        String phone = snapshot.child("phonePlay").getValue(String.class);
                        String describe = snapshot.child("describePlay").getValue(String.class);
                        String timeS = snapshot.child("timeStart").getValue(String.class);
                        String timeE = snapshot.child("timeEnd").getValue(String.class);
                        InforBookStadium inforBookStadium = new InforBookStadium(day,timeS,timeE,name,phone,describe);
                        reference.child(userID).child("-inForBookStadium").push().setValue(inforBookStadium);
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
        return convertView;
    }
}
