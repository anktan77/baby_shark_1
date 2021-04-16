package com.example.baby_shark;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BoookStadiumActivity extends AppCompatActivity {
    TextView txtDay,txtTimeStart,txtTimeEnd,txtTitleStadium,txtKeyStadium;
    EditText edtNameBook,edtPhoneBook, edtDescribeBook;
    Button btnMap,btnCall,btnConfirm;

    //khai báo để lấy date hiện tại
    Calendar calendarDay,calendarTimeEnd,calendarTimeStart;

    //khai báo kiểu định dạng cho date
    SimpleDateFormat simpleDateFormat;

    //
    DatabaseReference reference;
    ArrayList<String> arrayName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boook_stadium);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        AnhXa();
        //lấy dữ liệu phía stadium
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        String nameStadium = bundle.getString("namestadium");
        txtTitleStadium.setText(nameStadium);
            //
        reference = FirebaseDatabase.getInstance().getReference("AccountOwnerStadium");
        reference.orderByChild("name").equalTo(nameStadium).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String id = snapshot.getKey();
                txtKeyStadium.setText(id);
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

        //khởi tạo format
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //lấy ngày
        txtDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lấy hàm chọn ngày đá
                txtDay.setBackgroundColor(getColor(R.color.teal_200));
                txtDay.setTextColor(Color.WHITE);
                txtDay.setTextSize(20);
                PickDay();
            }
        });

        //lấy giờ bắt đầu
        txtTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lấy func chọn giờ bắt đầu
                txtTimeStart.setBackgroundColor(getColor(R.color.teal_200));
                txtTimeStart.setTextColor(Color.WHITE);
                txtTimeStart.setTextSize(20);
                PickTimeStart();
            }
        });

        //lấy giờ kết thúc
        txtTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lấy func giờ kết thúc
                txtTimeEnd.setBackgroundColor(getColor(R.color.teal_200));
                txtTimeEnd.setTextColor(Color.WHITE);
                txtTimeEnd.setTextSize(20);
                PickTimeEnd();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lấy dữ liệu
                String day = txtDay.getText().toString();
                String timeS = txtTimeStart.getText().toString();
                String timeE = txtTimeEnd.getText().toString();
                String name = edtNameBook.getText().toString();
                String phone = edtPhoneBook.getText().toString();
                String describe = edtDescribeBook.getText().toString();

                reference.orderByChild("name").equalTo(nameStadium).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String id = snapshot.getKey();
                        InforBookStadium inforBookStadium = new InforBookStadium(day,timeS,timeE,name,phone,describe);
                        reference.child(id).child("-inForBookStadium").push().setValue(inforBookStadium);
                        Toast.makeText(BoookStadiumActivity.this, "Đặt sân thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BoookStadiumActivity.this,MainActivity.class));
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
    }

    private void PickTimeEnd() {
        calendarTimeEnd = Calendar.getInstance();
        int gio = calendarTimeEnd.get(Calendar.HOUR_OF_DAY);
        int phut = calendarTimeEnd.get((Calendar.MINUTE)) ;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                calendarTimeEnd.set(0,0,0,hourOfDay,minute);
                txtTimeEnd.setText(simpleDateFormat.format(calendarTimeEnd.getTime()));

            }
        },gio,phut,true);
        timePickerDialog.show();
    }

    private void PickTimeStart() {
        calendarTimeStart = Calendar.getInstance();
        int gio = calendarTimeStart.get(Calendar.HOUR_OF_DAY);
        int phut = calendarTimeStart.get((Calendar.MINUTE)) ;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                calendarTimeStart.set(0,0,0,hourOfDay,minute);
                txtTimeStart.setText(simpleDateFormat.format(calendarTimeStart.getTime()));

            }
        },gio,phut,true);
        timePickerDialog.show();
    }

    private void PickDay() {
        calendarDay = Calendar.getInstance();
        int ngay = calendarDay.get(Calendar.DATE);
        int thang = calendarDay.get(Calendar.MONTH);
        int nam = calendarDay.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //truyền ngày tháng năm khi click vào calender
                calendarDay.set(year,month,dayOfMonth);
                txtDay.setText(simpleDateFormat.format(calendarDay.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }


    private void AnhXa() {
        txtDay = (TextView) findViewById(R.id.textviewDay);
        txtTimeStart = (TextView) findViewById(R.id.textviewTimeStart);
        txtTimeEnd = (TextView) findViewById(R.id.textviewTimeEnd);
        edtNameBook = (EditText) findViewById(R.id.editTextNameBook);
        edtPhoneBook = (EditText) findViewById(R.id.editTextPhoneBook);
        btnCall = (Button) findViewById(R.id.buttonCall);
        btnMap = (Button) findViewById(R.id.buttonMap);
        btnConfirm = (Button) findViewById(R.id.buttonConfirm);
        txtTitleStadium = (TextView) findViewById(R.id.textViewTitleNameBookStadium);
        txtKeyStadium = (TextView) findViewById(R.id.textviewTitleKeyNameBookStadium);
        edtDescribeBook = (EditText) findViewById(R.id.editTextDescribe);
    }
}