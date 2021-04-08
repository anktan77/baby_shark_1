package com.example.baby_shark;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class OwnerManageStadiumFragment extends Fragment {
    ListView lvBookStadium;
    ArrayList<BookStadium> arrayBookStadium;
    BookStadiumAdapter adapter;

    //time
    TextView txtDayReview;
    Calendar calendarDayReview;
    SimpleDateFormat simpleDateFormat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_owner_manage_stadium,container,false);

        //lấy ngày để xem thông tin
        txtDayReview = (TextView) view.findViewById(R.id.textViewDayReview);

        //khởi tạo format
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        //gán ngày hiện tại vào text
        calendarDayReview = Calendar.getInstance();

        txtDayReview.setText(simpleDateFormat.format(calendarDayReview.getTime()));
        //sự kiện chọn review day
        txtDayReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickDay();
            }
        });


        //list
        lvBookStadium = (ListView) view.findViewById(R.id.listviewBookStadium);
        AnhXa();
        adapter = new BookStadiumAdapter(getActivity(),R.layout.list_bookstadium,arrayBookStadium);
        lvBookStadium.setAdapter(adapter);
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

    private void AnhXa() {

        arrayBookStadium = new ArrayList<>();
        //thêm vào mảng Trai cay
        arrayBookStadium.add(new BookStadium("16h - 17h30","Nguyễn Đào Duy Tân","0389643578","đá cáp"));
        arrayBookStadium.add(new BookStadium("17h30 - 19h00","Biện Trung Nguyên","0346589621","đá riêng"));
        arrayBookStadium.add(new BookStadium("19h30 - 21h00","Đặng Trần Thiên Đạt","0389654265","đá cáp"));
        arrayBookStadium.add(new BookStadium("21h - 22h30","Nguyễn Trí Dũng","0393654254","đá riêng"));
        arrayBookStadium.add(new BookStadium("22h30 - 00h00","Lê Tuấn Anh","0365423658","đá cáp"));
    }
}