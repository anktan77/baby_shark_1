package com.example.baby_shark;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


public class list_search_stadium extends ListFragment {


    String[] arryCity = {"cc","lmm"};
    ArrayAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,arryCity);
        setListAdapter(adapter);
        return inflater.inflate(R.layout.fragment_list_search_stadium, container, false);
    }
}