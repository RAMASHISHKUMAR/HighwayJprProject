package com.highwayjprproject.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.highwayjprproject.fragment.MyBookingCancledFragment;
import com.highwayjprproject.fragment.MyBookingCompletedFragment;
import com.highwayjprproject.fragment.MyBookingOnGoingFragment;
import com.highwayjprproject.fragment.MyBookingPendingFragment;
import com.highwayjprproject.fragment.MyBookingUpCommingFragment;

import java.util.List;

public class MyBookingFragmentAdapter extends FragmentPagerAdapter {
    List<Fragment>fragments;

    public MyBookingFragmentAdapter(FragmentManager fm, List<Fragment>fragments) {
        super(fm);
        this.fragments =fragments;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (fragments.get(position)instanceof MyBookingUpCommingFragment){
            return "UPCOMING";
        }else if (fragments.get(position)instanceof MyBookingOnGoingFragment){
            return "ONGOING";
        }else if (fragments.get(position)instanceof MyBookingPendingFragment){
            return "PENDING";
        }else if (fragments.get(position)instanceof MyBookingCompletedFragment){
            return "COMPLETED";
        }if (fragments.get(position)instanceof MyBookingCancledFragment){
            return "CANCLED";
        }
            return " ";
        //return super.getPageTitle(position);
    }
}
