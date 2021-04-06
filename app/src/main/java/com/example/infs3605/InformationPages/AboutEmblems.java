package com.example.infs3605.InformationPages;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.infs3605.R;

public class AboutEmblems extends AppCompatActivity {
public static final String INTENT_MESSAGE = "au.edu.unsw.infs3634.numberGuesser.intent_message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_emblems);

    }
}