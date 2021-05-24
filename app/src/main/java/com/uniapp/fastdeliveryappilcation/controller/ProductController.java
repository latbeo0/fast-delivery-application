package com.uniapp.fastdeliveryappilcation.controller;

import android.graphics.Color;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.uniapp.fastdeliveryappilcation.adapter.SliderAdapter;
import com.uniapp.fastdeliveryappilcation.model.Slider;
import com.uniapp.fastdeliveryappilcation.ultils.Adapter;

import com.uniapp.fastdeliveryappilcation.R;
import com.uniapp.fastdeliveryappilcation.ultils.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProductController implements IProductController{
    Adapter adapter;
    List models;
    SliderAdapter sliderAdapter;

    public ProductController() {

    }

    @Override
    public void handleSliderInitialization(View view, ViewPager viewPager, SliderView sliderView) {
        models = new ArrayList<>();
        models.add(new Slider(R.drawable.photo4, "LUNCH", "Brochure is an informative paper document (often also used for advertising) that can be folded into a template", 0,0));
        models.add(new Slider(R.drawable.photo2, "DINNER", "Sticker is a type of label: a piece of printed paper, plastic, vinyl, or other material with pressure sensitive adhesive on one side", 0,0));
        models.add(new Slider(R.drawable.photo1, "LUNCH", "Poster is any piece of printed paper designed to be attached to a wall or vertical surface.", 1,0));
        models.add(new Slider(R.drawable.photo3, "DINNER", "Business cards are cards bearing business information about a company or individual.", 1,0));
        models.add(new Slider(R.drawable.photo3, "COMBO", "Business cards are cards bearing business information about a company or individual.", 0,1));
        models.add(new Slider(R.drawable.photo3, "COMBO", "Business cards are cards bearing business information about a company or individual.", 1,1));

        adapter = new Adapter(models, view.getContext());
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0, 130, 0);

        sliderAdapter = new SliderAdapter(view.getContext());
        sliderAdapter.setCount(5);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(2);
        sliderView.startAutoCycle();
        sliderView.setOnIndicatorClickListener(position -> sliderView.setCurrentPagePosition(position));
    }

    @Override
    public void handleMenuInitialization(TabLayout tabLayout, ViewPager viewPager, ViewPagerAdapter viewPagerAdapter) {
        tabLayout.addTab(tabLayout.newTab().setText("Veg"));
        tabLayout.addTab(tabLayout.newTab().setText("Non Veg"));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(viewPagerAdapter);
    }
}
