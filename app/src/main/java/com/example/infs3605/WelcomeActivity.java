package com.example.infs3605;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    TextView txtSignEmail;
    Button btnLogin,btnSignGoogle;
    EditText txtEmailLogin,txtPasswordLogin;
    ImageView ivSignGoogle;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);

        txtEmailLogin = findViewById(R.id.txtEmailLogin);
        txtPasswordLogin = findViewById(R.id.txtPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignEmail = findViewById(R.id.txtSignEmail);

        txtSignEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchEmailLoginActivity();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtEmailLogin.getText().toString().isEmpty()){
                    Toast.makeText(WelcomeActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                } else if(txtPasswordLogin.getText().toString().isEmpty()){
                    Toast.makeText(WelcomeActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else{
                    userLogin();
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();


    }

    private void userLogin(){
        String email = txtEmailLogin.getText().toString();
        String password = txtPasswordLogin.getText().toString();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    launchMapFragment();
                } else {
                    Toast.makeText(WelcomeActivity.this, "Login Fail, Please Check Your Credential", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void launchEmailLoginActivity() {
        Intent intent = new Intent(WelcomeActivity.this, RegisterActivity.class);

        startActivity(intent);
    }

    private void launchMapFragment() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);

        startActivity(intent);
    }


}
