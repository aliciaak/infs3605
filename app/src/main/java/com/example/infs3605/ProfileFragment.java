package com.example.infs3605;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    TextView tvName, tvBlood, tvEmail, tvId;
    Button btnSignOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        //user first,then uid
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getUid());

        tvName = v.findViewById(R.id.tvName);
        tvBlood = v.findViewById(R.id.tvBlood);
        tvEmail = v.findViewById(R.id.tvEmail);
        tvId = v.findViewById(R.id.tvId);
        btnSignOut = v.findViewById(R.id.btnSignOut);

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchWelcomeActivity();
            }
        });

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                tvName.setText(user.getName());
                tvEmail.setText(user.getEmail());
                tvBlood.setText(user.getBlood());
                tvId.setText("RC" + user.getId().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    public void launchWelcomeActivity() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), WelcomeActivity.class);
        startActivity(intent);
        getActivity().finish();

    }
}