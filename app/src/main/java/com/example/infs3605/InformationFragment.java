package com.example.infs3605;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class InformationFragment extends Fragment  {
    RelativeLayout RedCrossButton;
    RelativeLayout TheRedCrossEmblems;
    RelativeLayout CorrectEmblemsUsage;
    Fragment fragment;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_information, container, false);
        RedCrossButton = v.findViewById(R.id.RedCrossButton);
        TheRedCrossEmblems = v.findViewById(R.id.TheRedCrossEmblems);
        CorrectEmblemsUsage = v.findViewById(R.id.CorrectEmblemsUsage);
        bundle = new Bundle();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RedCrossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new YoutubePlayerFragment();
                sendMyData(getResources().getString(R.string.redcrossdesc),getResources().getString(R.string.the_red_cross_video),getResources().getString(R.string.rc_what),false);
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });

        TheRedCrossEmblems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new YoutubePlayerFragment();
                sendMyData(getResources().getString(R.string.Red_Cross_emblems),getResources().getString(R.string.Red_Cross_emblems_video),getResources().getString(R.string.Red_Cross_emblems_title),false);
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });

        CorrectEmblemsUsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new YoutubePlayerFragment();
                sendMyData(getResources().getString(R.string.use_the_emblems),getResources().getString(R.string.use_the_emblems_video),getResources().getString(R.string.use_the_emblems_title),true);
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });
    }

    private void sendMyData(String str, String str2, String str3, boolean isImageShow) {
        bundle.putString("redcrossdesc", str);
        bundle.putString("youtubeLink", str2);
        bundle.putString("redcrosstitle", str3);
        bundle.putBoolean("isImageShow", isImageShow);
        fragment.setArguments(bundle);
    }
}