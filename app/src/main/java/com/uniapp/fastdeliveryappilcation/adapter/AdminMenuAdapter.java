package com.uniapp.fastdeliveryappilcation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uniapp.fastdeliveryappilcation.AdminLoginActivity;
import com.uniapp.fastdeliveryappilcation.AdminSignupActivity;
import com.uniapp.fastdeliveryappilcation.R;
import com.uniapp.fastdeliveryappilcation.controller.IAdminController;
import com.uniapp.fastdeliveryappilcation.controller.IProductController;
import com.uniapp.fastdeliveryappilcation.model.ActiveSubscription;
import com.uniapp.fastdeliveryappilcation.model.Slider;
import com.uniapp.fastdeliveryappilcation.view.ISubscriptionView;
import com.uniapp.fastdeliveryappilcation.viewholder.ActiveSubscriptionViewHolder;
import com.uniapp.fastdeliveryappilcation.viewholder.AdminMenuViewHolder;
import com.uniapp.fastdeliveryappilcation.viewholder.SliderViewHolder;

import java.util.List;

public class AdminMenuAdapter extends RecyclerView.Adapter<AdminMenuViewHolder>{
    Context context;
    List<Slider> sliders;
    View view;
    IAdminController adminController;

    public AdminMenuAdapter(Context context, List<Slider> slider, View view) {
        this.sliders = slider;
        this.context = context;
        this.view = view;
    }

    @NonNull
    @Override
    public AdminMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new AdminMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdminMenuViewHolder holder, int position) {
        final Slider slider = sliders.get(position);
        holder.name.setText(slider.getTitle());
        holder.descp.setText(slider.getDesc());

        holder.image.setOnClickListener(t -> {
            view.getContext().startActivity(new Intent(context, AdminSignupActivity.class).putExtra("menuId",slider.getId()));
        });
    }
    @Override
    public int getItemCount() {
        return sliders.size();
    }
}
