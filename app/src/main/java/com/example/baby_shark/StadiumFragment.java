package com.example.baby_shark;

import android.content.Intent;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.ListFragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class StadiumFragment extends ListFragment implements OnMapReadyCallback {


    ListView lsStadium;
    SearchView sv_search;

    ArrayList<String> arrayStadium;
    ArrayAdapter adapter;

    //key
    ArrayList<String> arrayKey;
    ArrayAdapter adapterkey;
    private GoogleMap mMap;
    //
    Button btnBookStadium;
    //giả lập
    Button btnQuanLySan;
    //data
    DatabaseReference reference;
    FirebaseUser user;
    String userID;
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
        //data
//        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("AccountOwnerStadium");
        arrayStadium = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,arrayStadium);

        lsStadium.setAdapter(adapter);
        //sự kiện click vào list sân để đặt sân

        //list
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String accountOwnerStadium = snapshot.getValue(AccountOwnerStadium.class).getName();
                arrayStadium.add(accountOwnerStadium);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter.notifyDataSetChanged();
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

        //sự kiện click vào item
        arrayKey = new ArrayList<>();
        adapterkey = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,arrayKey);

        lsStadium.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),BoookStadiumActivity.class);
                Bundle bundle = new Bundle();
                String name = arrayStadium.get(position);
                ArrayList<String> arrayKey = new ArrayList<>();
                reference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String key = snapshot.getKey();
                        arrayKey.add(key);

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
                //tìm key phù hợp
                bundle.putString("namestadium",name);
                intent.putExtra("data",bundle);
                startActivity(intent);
                return false;
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