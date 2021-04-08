package com.example.baby_shark;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    private ActionBar toolbar;

    //gọi login
public void logout(View view){
    FirebaseAuth.getInstance().signOut();
    startActivity(new Intent(getApplicationContext(),Login.class));
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();


        //load trang chính
        toolbar.setTitle("Đặt sân");

        loadFragment (new StadiumFragment());

        //ánh xạ button navigation
        BottomNavigationView navigationItemView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationItemView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_stadium:
                    toolbar.setTitle("Đặt sân");
                    fragment = new StadiumFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_ball:
                    toolbar.setTitle("Cáp kèo");
                    fragment = new BallFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_newpaper:
                    toolbar.setTitle("Tin tức");
                    fragment = new NewpaperFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_notification:
                    toolbar.setTitle("Thông báo");
                    fragment = new NotificationFragment();
                    loadFragment(fragment);
                    return true;
                case  R.id.navigation_account:
                    toolbar.setTitle("Tài khoản");
                    fragment = new AccountFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}