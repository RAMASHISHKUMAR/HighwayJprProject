package com.highwayjprproject.fragment.customer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.highwayjprproject.R;


public class CustomerPendingFragment extends Fragment {
    private RecyclerView pendingRecycler;

    public CustomerPendingFragment() {
        // Required empty public constructor
    }


    public static CustomerPendingFragment newInstance() {
        CustomerPendingFragment fragment = new CustomerPendingFragment();
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
        View view = inflater.inflate(R.layout.fragment_customer_pending, container, false);

        pendingRecycler = view.findViewById(R.id.pendingRecyclerView);
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
