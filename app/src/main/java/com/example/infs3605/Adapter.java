package com.example.infs3605;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<User> familyMember;
    private LayoutInflater inflater;


    public Adapter(Context ctx, ArrayList<User> familyMember) {
       this.familyMember = familyMember;
       this.inflater = LayoutInflater.from(ctx);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.family_member_cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = familyMember.get(position);

        holder.status.setText(user.getType());
        holder.name.setText(user.getName());
        holder.location.setText(user.getEmail());
    }

    @Override
    public int getItemCount() {
        return familyMember.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,location,status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txtMemmberName);
            location = itemView.findViewById(R.id.txtLocation);
            status = itemView.findViewById(R.id.txtHelpStatus);
        }
    }
}
