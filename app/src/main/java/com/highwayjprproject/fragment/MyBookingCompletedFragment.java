package com.highwayjprproject.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.highwayjprproject.R;

public class MyBookingCompletedFragment extends Fragment {

    private RecyclerView completedRecycler;
    public MyBookingCompletedFragment() {
        // Required empty public constructor
    }


    public static MyBookingCompletedFragment newInstance(String param1, String param2) {
        MyBookingCompletedFragment fragment = new MyBookingCompletedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_booking_completed, container, false);

        completedRecycler = view.findViewById(R.id.completedRecyclerView);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
