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
    private final Listener mlistener;

    public Adapter(Context ctx, ArrayList<User> familyMember,Listener listener) {
       this.familyMember = familyMember;
       this.inflater = LayoutInflater.from(ctx);
       this.mlistener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.family_member_cardview,parent,false);
        return new ViewHolder(view,mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        User user = familyMember.get(position);
        holder.status.setText(user.getType());
        holder.name.setText(user.getName());
        holder.location.setText(user.getEmail());
        holder.locationStatus.setText(user.getPassword());
        holder.itemView.setTag(user.getName());
    }

    @Override
    public int getItemCount() {
        return familyMember.size();
    }

    public interface Listener {
        void onClick(View view,String name);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,location,status,locationStatus;

        private Listener listener;

        public ViewHolder(@NonNull View itemView, Listener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);

            name = itemView.findViewById(R.id.txtMemmberName);
            location = itemView.findViewById(R.id.txtLocation);
            status = itemView.findViewById(R.id.txtHelpStatus);
            locationStatus = itemView.findViewById(R.id.txtLocationStatus);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, (String) v.getTag());
        }
    }
}