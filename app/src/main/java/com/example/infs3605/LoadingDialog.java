package com.example.infs3605;

import android.app.AlertDialog;
import android.view.LayoutInflater;

//Get Loading screen. Reference:https://www.youtube.com/watch?v=tccoRIrMyhU&list=LL&index=1
public class LoadingDialog {

    private WelcomeActivity welcomeActivity;
    private RegisterActivity registerActivity;
    private AlertDialog dialog;

    LoadingDialog(WelcomeActivity myActivity){
        welcomeActivity = myActivity;
    }

    LoadingDialog (RegisterActivity myActivity1){
        registerActivity = myActivity1;
    }


    void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(welcomeActivity);

        LayoutInflater inflater = welcomeActivity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_screen,null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    void startLoadingDialog1(){
        AlertDialog.Builder builder = new AlertDialog.Builder(registerActivity);

        LayoutInflater inflater = registerActivity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_screen,null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }



    void dismissDialog(){
        dialog.dismiss();
    }
}
