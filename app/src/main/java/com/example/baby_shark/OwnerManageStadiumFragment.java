package com.example.baby_shark;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.ContentValues.TAG;


public class OwnerManageStadiumFragment extends Fragment {
    ListView lvBookStadium;
    ArrayList<InforBookStadium> arrayBookStadium;
    BookStadiumAdapter bookStadiumAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    Button btnFindDay;
    //time
    TextView txtDayReview,txtKeyXam;
    Calendar calendarDayReview;
    SimpleDateFormat simpleDateFormat;

    //database
    DatabaseReference reference;
    FirebaseUser user;
    String userID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_owner_manage_stadium,container,false);

        //data
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("AccountOwnerStadium");

        //lấy ngày để xem thông tin
        btnFindDay = (Button) view.findViewById(R.id.buttonFindDay);
        txtDayReview = (TextView) view.findViewById(R.id.textViewDayReview);
        txtKeyXam = (TextView) view.findViewById(R.id.textViewTitleKeyXam);
        //khởi tạo format
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        //gán ngày hiện tại vào text
        calendarDayReview = Calendar.getInstance();

        //list
        lvBookStadium = (ListView) view.findViewById(R.id.listviewBookStadium);
        arrayBookStadium = new ArrayList<>();
        bookStadiumAdapter = new BookStadiumAdapter(getActivity(),R.layout.list_bookstadium,arrayBookStadium);
        lvBookStadium.setAdapter(bookStadiumAdapter);


        txtDayReview.setText(simpleDateFormat.format(calendarDayReview.getTime()));


        //sự kiện chọn review day
        txtDayReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickDay();
            }
        });


        //sự kiện firebase thêm vào danh sách lịch bóng đá
        String findDay = txtDayReview.getText().toString();
        reference.child(userID).child("-inForBookStadium").orderByChild("dayPlay").equalTo(findDay).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String name = snapshot.child("namePlay").getValue(String.class);
                String day = snapshot.child("dayPlay").getValue(String.class);
                String phone = snapshot.child("phonePlay").getValue(String.class);
                String describe = snapshot.child("describePlay").getValue(String.class);
                String timeS = snapshot.child("timeStart").getValue(String.class);
                String timeE = snapshot.child("timeEnd").getValue(String.class);
                Log.d(TAG, "onChildAdded: "+ day);
                arrayBookStadium.add(new InforBookStadium(day, timeS, timeE, name, phone, describe));
                bookStadiumAdapter.notifyDataSetChanged();
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

        btnFindDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String findDay = txtDayReview.getText().toString();
                Log.d(TAG, "onChildAdded: "+ findDay);
                arrayBookStadium.clear();
                reference.child(userID).child("-inForBookStadium").orderByChild("dayPlay").equalTo(findDay).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String name = snapshot.child("namePlay").getValue(String.class);
                        String day = snapshot.child("dayPlay").getValue(String.class);
                        String phone = snapshot.child("phonePlay").getValue(String.class);
                        String describe = snapshot.child("describePlay").getValue(String.class);
                        String timeS = snapshot.child("timeStart").getValue(String.class);
                        String timeE = snapshot.child("timeEnd").getValue(String.class);

                        arrayBookStadium.add(new InforBookStadium(day, timeS, timeE, name, phone, describe));
                        bookStadiumAdapter.notifyDataSetChanged();
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
        return  view;
    }


    private void PickDay() {
        calendarDayReview = Calendar.getInstance();
        int ngay = calendarDayReview.get(Calendar.DATE);
        int thang = calendarDayReview.get(Calendar.MONTH);
        int nam = calendarDayReview.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //truyền ngày tháng năm khi click vào calender
                calendarDayReview.set(year,month,dayOfMonth);
                txtDayReview.setText(simpleDateFormat.format(calendarDayReview.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }
}