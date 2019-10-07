package com.highwayjprproject.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.highwayjprproject.R;
import com.highwayjprproject.adapter.MyBookingFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class DashBordFragment extends Fragment {
    private TabLayout myBookingTabLayout;
    private ViewPager myBookingViewPager;

    List<Fragment> fragmentList = new ArrayList<>();


    public static DashBordFragment newInstance() {
        DashBordFragment fragment = new DashBordFragment();
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
        View view = inflater.inflate(R.layout.fragment_dash_bord, container, false);

        myBookingTabLayout = view.findViewById(R.id.tabModeOfMyBooking);
        myBookingViewPager = view.findViewById(R.id.myBookingViewPager);

        fragmentList.add(new MyBookingCancledFragment());
        fragmentList.add(new MyBookingOnGoingFragment());
        fragmentList.add(new MyBookingPendingFragment());
        fragmentList.add(new MyBookingUpCommingFragment());
        fragmentList.add(new MyBookingCompletedFragment());

        MyBookingFragmentAdapter fragmentAdapter = new MyBookingFragmentAdapter(getActivity().getSupportFragmentManager(), fragmentList);

        myBookingViewPager.setAdapter(fragmentAdapter);
        myBookingTabLayout.setupWithViewPager(myBookingViewPager);

        myBookingTabLayout.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        myBookingViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
