package com.uniapp.fastdeliveryappilcation.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uniapp.fastdeliveryappilcation.R;

public class AdminMenuViewHolder extends RecyclerView.ViewHolder{
    public TextView name,descp;
    public ImageView image;
    public AdminMenuViewHolder(@NonNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.title);
        descp=itemView.findViewById(R.id.desc);
        image=itemView.findViewById(R.id.image);
    }
}