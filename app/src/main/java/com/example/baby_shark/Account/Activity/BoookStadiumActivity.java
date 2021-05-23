package com.example.baby_shark.Account.Activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baby_shark.Owner.OOP.InforBookStadium;
import com.example.baby_shark.R;
import com.example.baby_shark.Account.OOP.RecycleViewTimeBook;
import com.example.baby_shark.Account.Adapter.RecyleViewTimeBookAdapter;
import com.example.baby_shark.SlidePicture.SliderAdapter;
import com.example.baby_shark.SlidePicture.SliderItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;

public class BoookStadiumActivity extends AppCompatActivity {
    TextView txtDay,txtTimeStart,txtTimeEnd,txtTitleStadium,txtKeyStadium;
    EditText edtNameBook,edtPhoneBook, edtDescribeBook;
    Button btnMap,btnCall,btnConfirm,btnFindTimeBooked;
    RecyclerView rcvTimeBook;
    RecyleViewTimeBookAdapter adapter;
    ArrayList<RecycleViewTimeBook> recycleViewTimeBookArrayList;

    //khai báo để lấy date hiện tại
    Calendar calendarDay,calendarTimeEnd,calendarTimeStart;

    //khai báo kiểu định dạng cho date
    SimpleDateFormat simpleDateFormat;

    //
    DatabaseReference reference,reference1;
    FirebaseUser user;
    String userID;

    //slide picture
    SliderView sliderView;
    SliderAdapter silderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boook_stadium);
        AnhXa();

        //lấy dữ liệu phía stadium
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        String nameStadium = bundle.getString("namestadium");
        txtTitleStadium.setText(nameStadium);

        //tạo slide picture
        String nameS = txtTitleStadium.getText().toString();
        silderAdapter = new SliderAdapter(this);
        sliderView.setSliderAdapter(silderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        renewItems(nameS);
        removeLastItem();
        addNewItem();
        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());

            }
        });


        //tạo recycle nằm ngang
        LinearLayoutManager horizonRecycle = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rcvTimeBook.setLayoutManager(horizonRecycle);
        rcvTimeBook.setHasFixedSize(true);
        //array list time book;
        recycleViewTimeBookArrayList = new ArrayList<>();
        adapter = new RecyleViewTimeBookAdapter(this,recycleViewTimeBookArrayList);
        rcvTimeBook.setAdapter(adapter);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        //
        reference1 = FirebaseDatabase.getInstance().getReference("AccountBookStadium");
        reference1.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                Log.d(TAG, "IDcc: "+ userID);
                String name = snapshot.child("name").getValue(String.class);
                String phone = snapshot.child("phone").getValue(String.class);
                edtNameBook.setText(name+"");
                edtPhoneBook.setText(phone+"");
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        //kết nối firebase
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

        btnFindTimeBooked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycleViewTimeBookArrayList.clear();
                if (recycleViewTimeBookArrayList.size() == 0){
                    adapter.notifyDataSetChanged();
                }
                String day = txtDay.getText().toString();
                String key = txtKeyStadium.getText().toString();
                Log.d(TAG, "onChildAdded: "+ day);
                reference.child(key).child("-inForBookStadium").orderByChild("dayPlay").equalTo(day).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String timeS = snapshot.child("timeStart").getValue(String.class);
                        String timeE = snapshot.child("timeEnd").getValue(String.class);
                        recycleViewTimeBookArrayList.add(new RecycleViewTimeBook(timeS,timeE));
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
                        reference.child(id).child("-inForBookStadium").orderByChild("dayPlay").equalTo(day).addListenerForSingleValueEvent(new ValueEventListener() {
                            boolean f = true;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot find : snapshot.getChildren()) {
                                    String times = find.child("timeStart").getValue(String.class);
                                    String timee = find.child("timeEnd").getValue(String.class);
                                    if (timeS.compareTo(times) >= 0 && timeE.compareTo(timee) <= 0) {
//                                        Toast.makeText(BoookStadiumActivity.this, "đã có người đặt", Toast.LENGTH_SHORT).show();
                                        f= false;

                                    } else {
                                        if (timeS.compareTo(times) <= 0 && timeE.compareTo(timee) <= 0 && timeE.compareTo(times) >= 0) {
//                                            Toast.makeText(BoookStadiumActivity.this, "đã có người đặt", Toast.LENGTH_SHORT).show();
                                            f= false;
                                        } else {
                                            if (timeS.compareTo(times) >= 0 && timeS.compareTo(timee) <= 0 && timeE.compareTo(timee) >= 0) {
//                                                Toast.makeText(BoookStadiumActivity.this, "đã có người đặt", Toast.LENGTH_SHORT).show();
                                                f= false;
                                            }
                                        }
                                    }
                                }
                                if (f == true){
                                    String email = user.getEmail();
                                    InforBookStadium inforBookStadium = new InforBookStadium(day,timeS,timeE,name,phone,describe,email);
                                    reference.child(id).child("-waitingInForBookStadium").push().setValue(inforBookStadium);
                                    Toast.makeText(BoookStadiumActivity.this, "Đặt sân thành công", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(BoookStadiumActivity.this, MainActivity.class));
                                }
                                else {
                                    Toast.makeText(BoookStadiumActivity.this, "đã có người đặt", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
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

        //sự kiện map
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?q="+nameStadium));
                startActivity(intent);
            }
        });

        //sự kiện call
        int permisson_callphone = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = txtKeyStadium.getText().toString();
                reference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                        String phone = snapshot.child("phone").getValue(String.class);
                        Intent intent = new Intent();
                        intent.setAction(intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:"+ phone));
                        if (permisson_callphone != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(BoookStadiumActivity.this, new String[]{Manifest.permission.CALL_PHONE},1);
                        }
                        else {
                            startActivity(intent);
                        }
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
                simpleDateFormat = new SimpleDateFormat("HH:mm");
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
                simpleDateFormat = new SimpleDateFormat("HH:mm");
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

    //sliderpicture
    public void renewItems(String nameS) {
        Log.d(TAG,"cccccc" + nameS);
        List<SliderItem> sliderItemList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("AccountOwnerStadium");
        reference.orderByChild("name").equalTo(nameS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable  String previousChildName) {
                String key =snapshot.getKey();
                Log.d(TAG,"key nè con"+ key);
                reference.child(key).child("-folderPicture").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot find : snapshot.getChildren()){
                            SliderItem sliderItem = new SliderItem();
                            String picture = find.child("namePic").getValue(String.class);
                            Log.d(TAG,"hình nè con"+ picture);
                            sliderItem.setImageUrl(picture);
                            sliderItemList.add(sliderItem);;
                        }
                        silderAdapter.renewItems(sliderItemList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

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
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable  String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void removeLastItem() {
        if (silderAdapter.getCount() - 1 >= 0)
            silderAdapter.deleteItem(silderAdapter.getCount() - 1);
    }

    public void addNewItem() {
        SliderItem sliderItem = new SliderItem();
        sliderItem.setDescription("Slider Item Added Manually");
        sliderItem.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
        silderAdapter.addItem(sliderItem);
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
        btnFindTimeBooked = (Button) findViewById(R.id.buttonFindTimeBooked);
        rcvTimeBook = (RecyclerView) findViewById(R.id.recycleviewTimeBook);
        sliderView = findViewById(R.id.imageSlider);
    }
}