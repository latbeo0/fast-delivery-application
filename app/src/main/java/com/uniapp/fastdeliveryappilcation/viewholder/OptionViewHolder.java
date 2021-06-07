package com.uniapp.fastdeliveryappilcation.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.uniapp.fastdeliveryappilcation.R;

public class OptionViewHolder extends RecyclerView.ViewHolder{
    public TextView name,descp;
    public ImageView image;
    public OptionViewHolder(@NonNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.name);
        descp=itemView.findViewById(R.id.descp);
        image=itemView.findViewById(R.id.image);
    }
}