package com.uniapp.fastdeliveryappilcation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.uniapp.fastdeliveryappilcation.R;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.uniapp.fastdeliveryappilcation.viewholder.SliderViewHolder;

public class SliderAdapter extends
        SliderViewAdapter<SliderViewHolder> {

    private Context context;
    private int mCount;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public void setCount(int count) {
        this.mCount = count;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, final int position) {
        viewHolder.itemView.setOnClickListener(v -> Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show());

        switch (position) {
            case 0:
                viewHolder.imageGifContainer.setVisibility(View.GONE);
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.login_image)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;
            case 1:
                viewHolder.imageGifContainer.setVisibility(View.GONE);
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.photo1)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;
            case 2:
                viewHolder.imageGifContainer.setVisibility(View.GONE);
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.photo2)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;
            case 3:
                viewHolder.imageGifContainer.setVisibility(View.GONE);
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.photo3)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;
            case 4:
                viewHolder.imageGifContainer.setVisibility(View.GONE);
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.photo4)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;
            case 5:
                viewHolder.imageGifContainer.setVisibility(View.GONE);
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.poster)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;
            default:
                viewHolder.imageGifContainer.setVisibility(View.VISIBLE);
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.login_image)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                Glide.with(viewHolder.itemView)
                        .asGif()
                        .load(R.drawable.login_image)
                        .into(viewHolder.imageGifContainer);
                break;
        }
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mCount;
    }
}
