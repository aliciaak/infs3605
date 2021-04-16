package com.example.infs3605;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String type = "";
    Button btnRegister;
    RadioButton radioCivilian,radioStaff;
    EditText txtName,txtEmail,txtPassword,txtLanguage;
    Spinner spBlood;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    String blood;
    TextView txtId,txtSignIn;
    int count = 1;
    int start = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtLanguage = findViewById(R.id.txtLanguage);
        btnRegister = findViewById(R.id.btnRegister);
        radioCivilian = findViewById(R.id.radioCivilian);
        radioStaff =findViewById(R.id.radioStaff);
        txtId = findViewById(R.id.txtId);
        txtSignIn = findViewById(R.id.txtSignIn);

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchWelcomeActivity();
            }
        });

        spBlood = findViewById(R.id.spBlood);
        spBlood.setOnItemSelectedListener(this);

        String[] blood_type = getResources().getStringArray(R.array.blood_type);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,blood_type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBlood.setAdapter(adapter);

        final LoadingDialog loadingDialog = new LoadingDialog(RegisterActivity.this);

        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        mAuth = FirebaseAuth.getInstance();

        final DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("User");

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    count++;
                    txtId.setText(Integer.toString(count));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //For storing location data
        /*databaseReference = FirebaseDatabase.getInstance().getReference("Location");
        mAuth = FirebaseAuth.getInstance();*/

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = txtName.getText().toString();
                final String email = txtEmail.getText().toString();
                final String password = txtPassword.getText().toString();
                final String language = txtLanguage.getText().toString();
                final String blood_type = blood;
                final Long userId = Long.valueOf(Integer.parseInt(txtId.getText().toString())+start);

                loadingDialog.startLoadingDialog1();
                Handler handler = new Handler();

                if(radioCivilian.isChecked()){
                    type = "Civilian";
                }
                if(radioStaff.isChecked()){
                    type = "Red Cross Staff";
                }

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(RegisterActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(language)){
                    Toast.makeText(RegisterActivity.this, "Please Enter Language", Toast.LENGTH_SHORT).show();
                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {

                                            User information = new User(name,email,password,language,type,blood_type,userId);

                                            FirebaseDatabase.getInstance().getReference("User")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .setValue(information)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Toast.makeText(RegisterActivity.this, "Registration complete", Toast.LENGTH_SHORT).show();
                                                            launchMainActivity();
                                                            loadingDialog.dismissDialog();
                                                        }
                                                    });
                                        }
                                    }
                                });
                    }
                }, 1000);
            }
        });
    }

    public void launchMainActivity() {
        Intent intent = new Intent (RegisterActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void launchWelcomeActivity() {
        Intent intent = new Intent (RegisterActivity.this,WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==0){
            onNothingSelected(parent);
        } else{
            String valueFromSpinner = parent.getItemAtPosition(position).toString();
            blood = valueFromSpinner;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        String valueFromSpinner = "Not Entered";
        blood = valueFromSpinner;
    }
}