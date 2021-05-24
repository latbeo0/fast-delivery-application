package com.uniapp.fastdeliveryappilcation.viewholder;

import android.view.View;
import android.widget.ImageView;

import com.uniapp.fastdeliveryappilcation.R;

import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderViewHolder extends SliderViewAdapter.ViewHolder {
    public View itemView;
    public ImageView imageViewBackground;
    public ImageView imageGifContainer;

    public SliderViewHolder(View itemView) {
        super(itemView);
        imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
        imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
        this.itemView = itemView;
    }
}
