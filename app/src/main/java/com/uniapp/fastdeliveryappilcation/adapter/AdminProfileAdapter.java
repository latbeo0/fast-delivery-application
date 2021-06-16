package com.uniapp.fastdeliveryappilcation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uniapp.fastdeliveryappilcation.R;
import com.uniapp.fastdeliveryappilcation.controller.IAdminController;
import com.uniapp.fastdeliveryappilcation.model.Slider;
import com.uniapp.fastdeliveryappilcation.model.User;
import com.uniapp.fastdeliveryappilcation.viewholder.AdminMenuViewHolder;
import com.uniapp.fastdeliveryappilcation.viewholder.AdminProfileViewHolder;

import java.util.List;

public class AdminProfileAdapter extends RecyclerView.Adapter<AdminProfileViewHolder>{
    Context context;
    List<User> users;
    IAdminController adminController;

    public AdminProfileAdapter(Context context, List<User> users) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public AdminProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.admin_profile_item,parent,false);
        return new AdminProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdminProfileViewHolder holder, int position) {
        final User user = users.get(position);
        holder.id.setText(String.valueOf(user.getId()));
        holder.name.setText(user.getName());
        holder.email.setText(user.getEmail());
        holder.phone.setText(user.getPhone());

        holder.remove.setOnClickListener(t -> {

        });
    }
    @Override
    public int getItemCount() {
        return users.size();
    }
}
