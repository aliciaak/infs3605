package com.example.infs3605;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.infs3605.InformationPages.AboutEmblems;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class InformationFragment extends Fragment  {
    Button aboutRedCross;
    Button aboutEmblems;
    Button protectEmblems;
    Button emblemUsage;
    Button goBack;
    TextView textView;
    TextView title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_information, container, false);

        aboutRedCross = v.findViewById(R.id.aboutRedCrossButton);
        aboutEmblems = v.findViewById(R.id.button2);
        protectEmblems = v.findViewById(R.id.button3);
        emblemUsage = v.findViewById(R.id.button4);

        textView = v.findViewById(R.id.textView12);
        title = v.findViewById(R.id.pageTitle);

        goBack = v.findViewById(R.id.btn_back);

        aboutRedCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), AboutEmblems.class);
//                startActivity(intent);
                textView.setText("About RedCross");
                textView.setVisibility(View.VISIBLE);
                aboutRedCross.setVisibility(View.INVISIBLE);
                aboutEmblems.setVisibility(View.INVISIBLE);
                protectEmblems.setVisibility(View.INVISIBLE);
                emblemUsage.setVisibility(View.INVISIBLE);
                title.setVisibility(View.INVISIBLE);
                goBack.setVisibility(View.VISIBLE);

            }
        });

        aboutEmblems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("About emblem");
                textView.setVisibility(View.VISIBLE);
                aboutRedCross.setVisibility(View.INVISIBLE);
                aboutEmblems.setVisibility(View.INVISIBLE);
                protectEmblems.setVisibility(View.INVISIBLE);
                emblemUsage.setVisibility(View.INVISIBLE);
                title.setVisibility(View.INVISIBLE);
            }
        });

        protectEmblems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("Protect emblem");
                textView.setVisibility(View.VISIBLE);
                aboutRedCross.setVisibility(View.INVISIBLE);
                aboutEmblems.setVisibility(View.INVISIBLE);
                protectEmblems.setVisibility(View.INVISIBLE);
                emblemUsage.setVisibility(View.INVISIBLE);
                title.setVisibility(View.INVISIBLE);
            }
        });

        emblemUsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("emblemUsage");
                textView.setVisibility(View.VISIBLE);
                aboutRedCross.setVisibility(View.INVISIBLE);
                aboutEmblems.setVisibility(View.INVISIBLE);
                protectEmblems.setVisibility(View.INVISIBLE);
                emblemUsage.setVisibility(View.INVISIBLE);
                title.setVisibility(View.INVISIBLE);
            }
        });


        return v;

    }

}



