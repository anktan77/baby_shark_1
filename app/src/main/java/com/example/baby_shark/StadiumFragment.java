package com.example.baby_shark;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.ListFragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class StadiumFragment extends ListFragment implements OnMapReadyCallback {


    ListView lsStadium;
    SearchView sv_search;

    String[] arrayStadium = {"Sân bóng Football Arena","Sân bóng Quyết Tâm 2","Sân bóng Thanh Niên","Sân bóng Đường 79"};
    ArrayAdapter adapter;
    private GoogleMap mMap;
    //
    Button btnBookStadium;
    //giả lập
    Button btnQuanLySan;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stadium,container,false);
        //gọi map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapStadium);
        mapFragment.getMapAsync(this);

        //search
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        lsStadium = (ListView) view.findViewById(android.R.id.list);
        sv_search = (androidx.appcompat.widget.SearchView) view.findViewById(R.id.navSearch);
//        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,arrayStadium);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,arrayStadium){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Cast the list view each item as text view
                TextView item = (TextView) super.getView(position,convertView,parent);

                // Set the typeface/font for the current item
//                item.setTypeface();

                // Set the list view item's text color
                item.setTextColor(Color.parseColor("black"));

                // Set the item text style to bold
                item.setTypeface(item.getTypeface(), Typeface.BOLD);

                // Change the item text size
                item.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18);

                // return the view
                return item;
            }
        };
        lsStadium.setAdapter(adapter);
        //sự kiện click vào list sân để đặt sân



        lsStadium.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),BoookStadiumActivity.class);
                startActivity(intent);
                return false;
            }
        });

//        //book sân
//        btnBookStadium = (Button) view.findViewById(R.id.buttonBookStadium);
//        btnBookStadium.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),BoookStadiumActivity.class);
//                startActivity(intent);
//            }
//        });

        //quản lý sân
        btnQuanLySan = (Button) view.findViewById(R.id.buttonQuanLySan);
        btnQuanLySan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),OwnerStadiumActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }



    //search
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.nav_search, menu);
        MenuItem item = menu.findItem(R.id.navSearch);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Tìm kiếm sân");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return true;
            }

        });
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng latLng = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(latLng.latitude + ":" + latLng.longitude));
        mMap.clear();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

    }
}