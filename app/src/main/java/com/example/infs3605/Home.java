package com.example.infs3605;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import androidx.biometric.BiometricManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends Application {
    //check the current logging user in the firebase
    @Override
    public void onCreate(){
        super.onCreate();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        BiometricManager biometricManager = BiometricManager.from(this);

        if(firebaseUser != null){

            Intent intent = new Intent(Home.this,MainActivity.class);
            Intent intent1 = new Intent(Home.this,FingerprintActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//            if(BiometricManager.BIOMETRIC_SUCCESS == 0){
//                Toast.makeText(this, "You can use your fingerprint", Toast.LENGTH_SHORT).show();
//                startActivity(intent1);
//            } else {
//                startActivity(intent);
//            }

            switch (biometricManager.canAuthenticate()) {
                case BiometricManager.BIOMETRIC_SUCCESS:
                    //Toast.makeText(this, "You can use your fingerprint", Toast.LENGTH_SHORT).show();
                    startActivity(intent1);
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                    //Toast.makeText(this, "No fingerprint sensor", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    break;
                case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                    //Toast.makeText(this, "not available at the moment", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                    //Toast.makeText(this, "Your device dont have any fingerpirnt", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    break;
            }


        }
    }
}

