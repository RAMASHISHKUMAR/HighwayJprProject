package com.highwayjprproject;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import utils.Constants;
import utils.HighwayPreface;

public class DashBoardActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private Toolbar dashBoardToolbar;
    private CircleImageView nevCircularUserImgView;
    private TextView nevUserName, nevUserMobNo;
    String name, image, mobNo;
    private TextView tvName, tvMobileNo, tvSetting;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        nevigationInitView();
        updateNavViewHeader();



      /*  FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public void nevigationInitView() {

        dashBoardToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(dashBoardToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_dash_board);

        nevCircularUserImgView =headerView.findViewById(R.id.imageView);
        nevUserName = headerView.findViewById(R.id.userProfileName);
        nevUserMobNo = headerView.findViewById(R.id.userMobileNo);

    }

    public void updateNavViewHeader() {
        Intent intent = getIntent();
        image = HighwayPreface.getString(getApplicationContext(), Constants.IMAGE);
        name = HighwayPreface.getString(getApplicationContext(), Constants.NAME);
        mobNo = HighwayPreface.getString(getApplicationContext(), Constants.MOBILE);

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


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
}
