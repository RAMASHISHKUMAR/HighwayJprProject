package com.highwayjprproject.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.highwayjprproject.customerfragment.CustomerCancledFragment;
import com.highwayjprproject.customerfragment.CustomerCompletedFragment;
import com.highwayjprproject.customerfragment.CustomerOnGoingFragment;
import com.highwayjprproject.customerfragment.CustomerPendingFragment;
import com.highwayjprproject.customerfragment.CustomerUpCommingFragment;

import java.util.List;

public class CustomerFragmentAdapter extends FragmentPagerAdapter {
    List<Fragment>customerfragments;

    public CustomerFragmentAdapter(FragmentManager fm, List<Fragment>fragments) {
        super(fm);
        this.customerfragments =fragments;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return customerfragments.get(position);
    }

    @Override
    public int getCount() {
        return customerfragments.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (customerfragments.get(position)instanceof CustomerUpCommingFragment){
            return "UPCOMING";
        }else if (customerfragments.get(position)instanceof CustomerOnGoingFragment){
            return "ONGOING";
        }else if (customerfragments.get(position)instanceof CustomerPendingFragment){
            return "PENDING";
        }else if (customerfragments.get(position)instanceof CustomerCompletedFragment){
            return "COMPLETED";
        }if (customerfragments.get(position)instanceof CustomerCancledFragment){
            return "CANCLED";
        }
            return " ";
        //return super.getPageTitle(position);
    }
}
