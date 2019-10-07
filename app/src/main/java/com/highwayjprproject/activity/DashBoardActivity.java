package com.highwayjprproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.highwayjprproject.R;
import com.highwayjprproject.fragment.DashBordFragment;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import com.highwayjprproject.utils.Constants;
import com.highwayjprproject.utils.HighwayPrefs;

public class DashBoardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;

    private Toolbar dashBoardToolbar;
    private CircleImageView nevCircularUserImgView;
    private TextView nevUserName, nevUserMobNo;
    String name, image, mobNo;
    private TextView tvName, tvMobileNo, tvSetting;
    private NavigationView navigationView;
    String userRole;
    private MenuItem newBooking, myBooking, wallet, notification, rateCard, help, about, share, send, gallery;
    private MenuItem item;
    private Button btnLogOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        nevigationInitView();
        updateNavViewHeader();
        navAccoringRoleId();// According RoleId Nevigation Icon
        setOnClickListenerOperation();

    }

    public void nevigationInitView() {

        dashBoardToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(dashBoardToolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        btnLogOut = findViewById(R.id.btnLogout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, dashBoardToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_dash_board);
        nevCircularUserImgView = headerView.findViewById(R.id.imageView);
        nevUserName = headerView.findViewById(R.id.userProfileName);
        nevUserMobNo = headerView.findViewById(R.id.userMobileNo);

        // navigation menuItem
        Menu menues = navigationView.getMenu();

        newBooking = menues.findItem(R.id.nav_new_booking);
        myBooking = menues.findItem(R.id.nav_my_booking);
        wallet = menues.findItem(R.id.nav_wallet);
        notification = menues.findItem(R.id.nav_notification);
        rateCard = menues.findItem(R.id.nav_rate_card);
        help = menues.findItem(R.id.nav_help);
        about = menues.findItem(R.id.nav_about);
        share = menues.findItem(R.id.nav_share);
        send = menues.findItem(R.id.nav_send);
        gallery = menues.findItem(R.id.nav_gallery);

    }

    public void updateNavViewHeader() {
        Intent intent = getIntent();
        image = HighwayPrefs.getString(getApplicationContext(), Constants.IMAGE);
        name = HighwayPrefs.getString(getApplicationContext(), Constants.NAME);
        mobNo = HighwayPrefs.getString(getApplicationContext(), Constants.USERMOBILE);

        nevUserName.setText(name);
        nevUserMobNo.setText(mobNo);

        if (!TextUtils.isEmpty(image)) {
            Picasso.with(this).load(image)
                    .error(R.drawable.highway_logo)
                    .into(nevCircularUserImgView);
        } else {
            Picasso.with(this)
                    .load(R.drawable.highway_logo)
                    .error(R.drawable.highway_logo)
                    .into(nevCircularUserImgView);
        }
    }

    public void navAccoringRoleId() {
        userRole = HighwayPrefs.getString(getApplicationContext(), Constants.ROLEID);
        switch (userRole) {
            case "4":
                newBooking.setVisible(true);
                myBooking.setVisible(true);
                wallet.setVisible(true);
                notification.setVisible(true);
                rateCard.setVisible(true);
                help.setVisible(true);
                about.setVisible(true);
                share.setVisible(true);
                send.setVisible(true);
                gallery.setVisible(false);
                break;

            case "3":
                newBooking.setVisible(false);
                myBooking.setVisible(true);
                wallet.setVisible(true);
                notification.setVisible(true);
                rateCard.setVisible(false);
                help.setVisible(true);
                about.setVisible(true);
                share.setVisible(false);
                send.setVisible(false);
                gallery.setVisible(false);
                break;

            case "2":
                newBooking.setVisible(true);
                myBooking.setVisible(true);
                wallet.setVisible(true);
                notification.setVisible(true);
                rateCard.setVisible(true);
                help.setVisible(true);
                about.setVisible(true);
                share.setVisible(true);
                send.setVisible(true);
                gallery.setVisible(true);
                break;
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

   /* @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        int id = menuItem.getItemId();
//  1 admin  //  2 mill user   // 3  driver // // 4  customer   // 5 owner
        switch (id){
            case  R.id.nav_new_booking:
                dashBoardToolbar.setTitle("New Booking");
               switch (userRole){
                   case "1":

                       break;
                   case "2":

                       break;
                   case "3":

                       break;
                   case "4":

                       break;
                   case "5":

                      break;
               }
                break;

            case  R.id.nav_my_booking:
                dashBoardToolbar.setTitle("My Booking");
                switch (userRole){
                    case "1":
                        /*fragment = DashBordFragment.newInstance();
                        replaceFragment(fragment);*/
                        break;
                    case "2":
                        fragment = DashBordFragment.newInstance();
                        replaceFragment(fragment);
                        break;
                    case "3":
                        fragment = DashBordFragment.newInstance();
                        replaceFragment(fragment);
                        break;
                    case "4":
                        fragment = DashBordFragment.newInstance();
                        replaceFragment(fragment);
                        break;
                    case "5":
                       /* fragment = DashBordFragment.newInstance();
                        replaceFragment(fragment);*/
                        break;
                }
                break;


            case  R.id.nav_wallet:
                dashBoardToolbar.setTitle("Wallet");
                switch (userRole){
                    case "1":
                        break;
                    case "2":

                        break;
                    case "3":

                        break;
                    case "4":

                        break;
                    case "5":

                        break;
                }
                break;

            case  R.id.nav_notification:
                dashBoardToolbar.setTitle("Notification");
                switch (userRole){
                    case "1":
                        break;
                    case "2":

                        break;
                    case "3":

                        break;
                    case "4":

                        break;
                    case "5":

                        break;
                }
                break;

            case  R.id.nav_rate_card:
                dashBoardToolbar.setTitle("Rate Card");
                switch (userRole){
                    case "1":
                        break;
                    case "2":

                        break;
                    case "3":

                        break;
                    case "4":

                        break;
                    case "5":

                        break;
                }
                break;

            case  R.id.nav_help:
                dashBoardToolbar.setTitle("Help");
                switch (userRole){
                    case "1":
                        break;
                    case "2":

                        break;
                    case "3":

                        break;
                    case "4":

                        break;
                    case "5":

                        break;
                }
                break;

            case  R.id.nav_about:
                dashBoardToolbar.setTitle("About");
                switch (userRole){
                    case "1":
                        break;
                    case "2":

                        break;
                    case "3":

                        break;
                    case "4":

                        break;
                    case "5":

                        break;
                }
                break;

            case  R.id.nav_share:
                dashBoardToolbar.setTitle("Share");
                switch (userRole){
                    case "1":
                        break;
                    case "2":

                        break;
                    case "3":

                        break;
                    case "4":

                        break;
                    case "5":

                        break;
                }
                break;

            case  R.id.nav_send:
                dashBoardToolbar.setTitle("Send");
                switch (userRole){
                    case "1":
                        break;
                    case "2":

                        break;
                    case "3":

                        break;
                    case "4":

                        break;
                    case "5":

                        break;
                }
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void replaceFragment(Fragment fragment) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager != null) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment, "");
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commitAllowingStateLoss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setOnClickListenerOperation() {
        navigationView.setNavigationItemSelectedListener(this);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // HighwayPrefs.clear(DashBoardActivity.this);
                HighwayPrefs.putBoolean(getApplicationContext(), Constants.LoginCheck, false);
                Intent intent = new Intent(DashBoardActivity.this, LoginRegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
