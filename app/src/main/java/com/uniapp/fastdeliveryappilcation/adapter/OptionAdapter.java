package com.uniapp.fastdeliveryappilcation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uniapp.fastdeliveryappilcation.model.Option;
import com.uniapp.fastdeliveryappilcation.viewholder.OptionViewHolder;
import com.uniapp.fastdeliveryappilcation.R;
import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionViewHolder> {
    List<Option> flexibleDataList;
    public OptionAdapter(List<Option> flexibleDataList) {
        this.flexibleDataList = flexibleDataList;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_flexibleplan,parent,false);
        return new OptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
        Option data = flexibleDataList.get(position);
        holder.name.setText(data.getName());
        holder.descp.setText(data.getDescp());
        holder.image.setImageResource(data.getImgid());
    }

    @Override
    public int getItemCount() {
        return flexibleDataList.size();
    }
}
