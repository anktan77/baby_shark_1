package com.example.baby_shark;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OwnerStadiumActivity extends AppCompatActivity {
//    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_stadium);
//        toolbar = getSupportActionBar();

        //load fragment
        loadFragment (new OwnerManageStadiumFragment());

        //ánh xạ button navigation
        BottomNavigationView navigationItemView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.navigation_owner_stadium:
                        fragment = new OwnerManageStadiumFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_owner_notification:
                        fragment = new StadiumFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_owner_account:
                        fragment = new NotificationFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;

            }
        });
    }

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            Fragment fragment;
//            switch (item.getItemId()) {
//                case R.id.navigation_owner_stadium:
//                    toolbar.setTitle("Quản lý sân");
//                    fragment = new OwnerManageStadiumFragment();
//                    loadFragment(fragment);
//                    return true;
//                case R.id.navigation_owner_notification:
//                    toolbar.setTitle("Danh sách đặt sân");
//                    fragment = new StadiumFragment();
//                    loadFragment(fragment);
//                    return true;
//                case R.id.navigation_owner_account:
//                    toolbar.setTitle("Tài khoản");
//                    fragment = new NotificationFragment();
//                    loadFragment(fragment);
//                    return true;
//            }
//            return false;
//        }
//    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_Owner, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}