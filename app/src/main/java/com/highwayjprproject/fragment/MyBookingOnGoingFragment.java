package com.highwayjprproject.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.highwayjprproject.R;


public class MyBookingOnGoingFragment extends Fragment {
    private RecyclerView ongoingRecycler;

    public MyBookingOnGoingFragment() {
        // Required empty public constructor
    }


    public static MyBookingOnGoingFragment newInstance() {
        MyBookingOnGoingFragment fragment = new MyBookingOnGoingFragment();
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
       View  view = inflater.inflate(R.layout.fragment_my_booking_on_going, container, false);
        ongoingRecycler = view.findViewById(R.id.onGoingRecyclerView);
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
