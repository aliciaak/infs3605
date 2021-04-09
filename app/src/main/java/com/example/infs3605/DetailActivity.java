package com.example.infs3605;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String INTENT_MESSAGE = "intent_message";

    TextView tvMemberName,tvLastLocation,tvLocationStatus,tvHelpStatus;
    Spinner spLF;
    Button btnUpdate;
    String status;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.popup_memberdetail);

        Intent intent = getIntent();
        String name = intent.getStringExtra(INTENT_MESSAGE);

        tvMemberName = findViewById(R.id.tvMemberName);
        tvLastLocation = findViewById(R.id.tvLastLocation);
        tvLocationStatus = findViewById(R.id.tvLocationStatus);
        tvHelpStatus = findViewById(R.id.tvHelpStatus);

        User user = User.getUser(name);

        tvMemberName.setText(user.getName());
        tvLastLocation.setText(user.getEmail());
        tvHelpStatus.setText(user.getType());
        tvLocationStatus.setText(user.getPassword());

        spLF = findViewById(R.id.spLF);
        spLF.setOnItemSelectedListener(this);

        String[] lostfound = getResources().getStringArray(R.array.Lost_Found);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,lostfound);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLF.setAdapter(adapter);

        final Dialog mDialog = new Dialog(this);

        btnUpdate = findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status.equals("Mark As Lost")){
                    mDialog.setContentView(R.layout.popup_notification);
                    mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mDialog.show();

                }
            }
        });







    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==0){
            onNothingSelected(parent);
        } else{
            String valueFromSpinner = parent.getItemAtPosition(position).toString();
            status = valueFromSpinner;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        String valueFromSpinner = "Not Entered";
        status = valueFromSpinner;
    }


}
