package com.highwayjprproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.highwayjprproject.R;

public class LoginOptionActivity extends AppCompatActivity {

    private RadioButton radioCustomer, radioDriver, radioMiller, radioOwner;
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
        radioCustomer = findViewById(R.id.customerRadioBtn);
        radioDriver = findViewById(R.id.driverRadioBtn);
        radioMiller = findViewById(R.id.millerRadioBtn);
        radioOwner = findViewById(R.id.ownerRadioBtn);
        btnNext = findViewById(R.id.btnNext);
    }

    public void  onUserRadioBtnClicked(View view){

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean checked = ((RadioButton) view).isChecked();

                switch (view.getId()){
                    case R.id.customerRadioBtn:
                        if (checked)
                            radioCustomer.setText("Customer");
                        userRole = radioCustomer.getText().toString().trim();
                        Toast.makeText(LoginOptionActivity.this,radioCustomer.getText().toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginOptionActivity.this,CustomerLoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.driverRadioBtn:
                        if (checked)
                            radioDriver.setText("Driver");
                        userRole = radioDriver.getText().toString().trim();
                        Toast.makeText(LoginOptionActivity.this,radioDriver.getText().toString(), Toast.LENGTH_SHORT).show();
                        intent = new Intent(LoginOptionActivity.this,DriverLoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.millerRadioBtn:
                        if (checked)
                            radioMiller.setText("Miller");
                        userRole = radioMiller.getText().toString().trim();
                        Toast.makeText(LoginOptionActivity.this,radioMiller.getText().toString(), Toast.LENGTH_SHORT).show();
                        intent = new Intent(LoginOptionActivity.this,MillerLoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.ownerRadioBtn:
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
