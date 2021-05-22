package com.example.baby_shark;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.baby_shark.Adapter.BookStadiumAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.ContentValues.TAG;


public class OwnerManageStadiumFragment extends Fragment {
    ListView lvBookStadium;
    ArrayList<InforBookStadium> arrayBookStadium;
    BookStadiumAdapter bookStadiumAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    Button btnFindDay,btnAddList;
    //time
    TextView txtDayReview,txtKeyXam;
    Calendar calendarDayReview;
    SimpleDateFormat simpleDateFormat;

    //database
    DatabaseReference reference;
    FirebaseUser user;
    String userID;

    //dialog
    EditText edtName ;
    EditText edtPhone ;
    EditText edtDay ;
    EditText edtDescribe;
    EditText edtTimeS;
    EditText edtTimeE ;
    Button btnUpdate ;
    Button btnDelete ;
    Button btnCall;
    //dialog add:
    TextView txtAddDay,txtAddTimeS,txtAddTimeE;
    EditText edtAddName,edtAddPhone,edtAddDescribe;
    Button btnDialogAdd;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_owner_manage_stadium,container,false);
        Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.fragment_close_enter);
        //data
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("AccountOwnerStadium");

        //lấy ngày để xem thông tin
        btnFindDay = (Button) view.findViewById(R.id.buttonFindDay);
        btnFindDay.setAnimation(animation);
        txtDayReview = (TextView) view.findViewById(R.id.textViewDayReview);
        txtKeyXam = (TextView) view.findViewById(R.id.textViewTitleKeyXam);
        btnAddList = (Button) view.findViewById(R.id.buttonAddList);
        //khởi tạo format
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        //gán ngày hiện tại vào text
        calendarDayReview = Calendar.getInstance();

        //list
        lvBookStadium = (ListView) view.findViewById(R.id.listviewBookStadium);
        arrayBookStadium = new ArrayList<>();
        bookStadiumAdapter = new BookStadiumAdapter(getActivity(),R.layout.list_bookstadium,arrayBookStadium);
        lvBookStadium.setAdapter(bookStadiumAdapter);

        lvBookStadium.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.slide_in_left));
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
                String email = snapshot.child("emailPlay").getValue(String.class);
                Log.d(TAG, "onChildAdded: "+ day);
                arrayBookStadium.add(new InforBookStadium(day, timeS, timeE, name, phone, describe,email));
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
                v.startAnimation(animation);
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
                        String email = snapshot.child("emailPlay").getValue(String.class);
                        arrayBookStadium.add(new InforBookStadium(day, timeS, timeE, name, phone, describe,email));
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


        //ánh xạ cho dialog

        //sự kiện click view
        lvBookStadium.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_update_delete_owner_stadium);
                //ánh xạ với dialog
                edtName = (EditText) dialog.findViewById(R.id.edittextDALName);
                edtPhone = (EditText) dialog.findViewById(R.id.edittextDALPhone);
                edtDay = (EditText) dialog.findViewById(R.id.edittextDALDay);
                edtDescribe = (EditText) dialog.findViewById(R.id.edittextDALDescribe);
                edtTimeS = (EditText) dialog.findViewById(R.id.edittextDALTimeStart);
                edtTimeE = (EditText) dialog.findViewById(R.id.edittextDALTimeEnd);
                btnUpdate = (Button) dialog.findViewById(R.id.buttonDALUpdate);
                btnDelete = (Button) dialog.findViewById(R.id.buttonDALDelete);
                btnCall = (Button) dialog.findViewById(R.id.buttonDALCall) ;
                //
                edtName.setText(arrayBookStadium.get(position).getNamePlay());
                edtPhone.setText(arrayBookStadium.get(position).getPhonePlay());
                edtDay.setText(arrayBookStadium.get(position).getDayPlay());
                edtDescribe.setText(arrayBookStadium.get(position).getDescribePlay());
                edtTimeS.setText(arrayBookStadium.get(position).getTimeStart());
                edtTimeE.setText(arrayBookStadium.get(position).getTimeEnd());
                dialog.show();

                //sự kiện cập nhật

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String udname = edtName.getText().toString();
                        String upphone = edtPhone.getText().toString();
                        String upday = edtDay.getText().toString();
                        String updescribe = edtDescribe.getText().toString();
                        String uptimes = edtTimeS.getText().toString();
                        String uptimee = edtTimeE.getText().toString();
                        Query query = reference.child(userID).child("-inForBookStadium").orderByChild("phonePlay").equalTo(arrayBookStadium.get(position).getPhonePlay());
                        query.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                String key = snapshot.getKey();
                                reference.child(userID).child("-inForBookStadium").child(key).child("dayPlay").setValue(upday);
                                reference.child(userID).child("-inForBookStadium").child(key).child("describePlay").setValue(updescribe);
                                reference.child(userID).child("-inForBookStadium").child(key).child("namePlay").setValue(udname);
                                reference.child(userID).child("-inForBookStadium").child(key).child("phonePlay").setValue(upphone);
                                reference.child(userID).child("-inForBookStadium").child(key).child("timeStart").setValue(uptimes);
                                reference.child(userID).child("-inForBookStadium").child(key).child("timeEnd").setValue(uptimee);

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

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Query query = reference.child(userID).child("-inForBookStadium").orderByChild("phonePlay").equalTo(arrayBookStadium.get(position).getPhonePlay());
                        query.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                String key = snapshot.getKey();
                                reference.child(userID).child("-inForBookStadium").child(key).removeValue();
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
                int permisson_callphone = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);
                String phone = arrayBookStadium.get(position).getPhonePlay();
                btnCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:"+ phone));
                        if (permisson_callphone != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},1);
                        }
                        else {
                            getActivity().startActivity(intent);
                        }
                    }
                });
                return false;
            }
        });

        //
        btnAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_add_list_manage_owner);
                //
                txtAddDay = (TextView) dialog.findViewById(R.id.textviewDialogManageDay);
                txtAddTimeS = (TextView) dialog.findViewById(R.id.textviewDialogManageTimeS);
                txtAddTimeE = (TextView) dialog.findViewById(R.id.textviewDialogManageTimeE);
                edtAddName = (EditText) dialog.findViewById(R.id.editTextDialogManageName);
                edtAddPhone = (EditText) dialog.findViewById(R.id.editTextDialogManagePhone);
                edtAddDescribe = (EditText) dialog.findViewById(R.id.editTextDialogManageDescribe);
                btnDialogAdd = (Button) dialog.findViewById(R.id.buttonDialogManageAdd);
                dialog.show();
                //
                txtAddDay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PickDay1();
                    }
                });
                //
                txtAddTimeS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PickTimeStart();
                    }
                });
                //
                txtAddTimeE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PickTimeEnd();
                    }
                });
                //
                btnDialogAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String day = txtAddDay.getText().toString();
                        String timeS = txtAddTimeS.getText().toString();
                        String timeE = txtAddTimeE.getText().toString();
                        String name = edtAddName.getText().toString();
                        String phone = edtAddPhone.getText().toString();
                        String describe = edtAddDescribe.getText().toString();
                        InforBookStadium inforBookStadium = new InforBookStadium(day,timeS,timeE,name,phone,describe,"");
                        reference.child(userID).child("-inForBookStadium").push().setValue(inforBookStadium);
                        Toast.makeText(getActivity(), "Thêm thành công, bạn có thể kiểm tra ngay bây giờ!", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
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


    private void PickDay1() {
        calendarDayReview = Calendar.getInstance();
        int ngay = calendarDayReview.get(Calendar.DATE);
        int thang = calendarDayReview.get(Calendar.MONTH);
        int nam = calendarDayReview.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //truyền ngày tháng năm khi click vào calender
                calendarDayReview.set(year,month,dayOfMonth);
                txtAddDay.setText(simpleDateFormat.format(calendarDayReview.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }

    private void PickTimeEnd() {
        Calendar calendarTimeEnd = Calendar.getInstance();
        int gio = calendarTimeEnd.get(Calendar.HOUR_OF_DAY);
        int phut = calendarTimeEnd.get((Calendar.MINUTE)) ;

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                simpleDateFormat = new SimpleDateFormat("HH:mm");
                calendarTimeEnd.set(0,0,0,hourOfDay,minute);
                txtAddTimeE.setText(simpleDateFormat.format(calendarTimeEnd.getTime()));

            }
        },gio,phut,true);
        timePickerDialog.show();
    }

    private void PickTimeStart() {
        Calendar calendarTimeStart = Calendar.getInstance();
        int gio = calendarTimeStart.get(Calendar.HOUR_OF_DAY);
        int phut = calendarTimeStart.get((Calendar.MINUTE)) ;

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                simpleDateFormat = new SimpleDateFormat("HH:mm");
                calendarTimeStart.set(0,0,0,hourOfDay,minute);
                txtAddTimeS.setText(simpleDateFormat.format(calendarTimeStart.getTime()));

            }
        },gio,phut,true);
        timePickerDialog.show();
    }
}