package com.highwayjprproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.highwayjprproject.R;

public class LoginOptionActivity extends AppCompatActivity {

    private RadioButton radioCustomer, radioDriver, radioMiller, radioOwner;
    private ImageView imgCustomer, imgDriver,imgMiller, imgOwner;
    private Button  btnNext;
    private String userRole;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_option);

        initialView();

    }

    public void initialView(){
        radioCustomer = findViewById(R.id.radioBtnCustomer);
        radioDriver = findViewById(R.id.radioBtnMiller);
        radioMiller = findViewById(R.id.radioBtnOwner);
        radioOwner = findViewById(R.id.radioBtnDriver);
        btnNext = findViewById(R.id.btnNext);
    }

    public void onClickUser(View view){

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) view).isChecked();
                switch (view.getId()){
                    case R.id.radioBtnCustomer:
                        if (checked)
                            radioCustomer.setText("Driver");
                        userRole = radioCustomer.getText().toString().trim();
                        Toast.makeText(LoginOptionActivity.this,radioCustomer.getText().toString(), Toast.LENGTH_SHORT).show();
                        intent = new Intent(LoginOptionActivity.this,CustomerLoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.radioBtnDriver:
                        if (checked)
                            radioDriver.setText("Driver");
                        userRole = radioDriver.getText().toString().trim();
                        Toast.makeText(LoginOptionActivity.this,radioDriver.getText().toString(), Toast.LENGTH_SHORT).show();
                        intent = new Intent(LoginOptionActivity.this,DriverLoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.radioBtnMiller:
                        if (checked)
                            radioMiller.setText("Miller");
                        userRole = radioMiller.getText().toString().trim();
                        Toast.makeText(LoginOptionActivity.this,radioMiller.getText().toString(), Toast.LENGTH_SHORT).show();
                        intent = new Intent(LoginOptionActivity.this,MillerLoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.radioBtnOwner:
                        if (checked)
                            radioOwner.setText("Owner");
                        userRole = radioOwner.getText().toString().trim();
                        Toast.makeText(LoginOptionActivity.this,radioOwner.getText().toString(), Toast.LENGTH_SHORT).show();
                        intent = new Intent(LoginOptionActivity.this,OwnerLoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }

            }
        });




    }


}
