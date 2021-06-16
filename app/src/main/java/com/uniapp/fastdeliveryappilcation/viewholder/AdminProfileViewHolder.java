package com.uniapp.fastdeliveryappilcation.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uniapp.fastdeliveryappilcation.R;

public class AdminProfileViewHolder extends RecyclerView.ViewHolder{
    public TextView name,email, phone, id;
    public Button remove;
    public AdminProfileViewHolder(@NonNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.profile_name);
        email=itemView.findViewById(R.id.profile_email);
        phone=itemView.findViewById(R.id.profile_phone);
        id=itemView.findViewById(R.id.profile_id);
        remove = itemView.findViewById(R.id.profile_remove);
    }
}