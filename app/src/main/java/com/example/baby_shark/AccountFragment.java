package com.example.baby_shark;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class AccountFragment extends Fragment {

    TextView txtCreateOwnerStadium;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container,false);

        txtCreateOwnerStadium = (TextView) view.findViewById(R.id.textviewCreateOwnerStadium);
        txtCreateOwnerStadium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),RegisterOwnerStadium.class));
            }
        });
        return view;
    }
}