package com.uniapp.fastdeliveryappilcation.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.jetbrains.annotations.NotNull;

import com.google.android.material.tabs.TabLayout;
import com.smarteist.autoimageslider.SliderView;
import com.uniapp.fastdeliveryappilcation.HistoryActivity;
import com.uniapp.fastdeliveryappilcation.MapActivity;
import com.uniapp.fastdeliveryappilcation.R;
import com.uniapp.fastdeliveryappilcation.controller.IProductController;
import com.uniapp.fastdeliveryappilcation.controller.ProductController;
import com.uniapp.fastdeliveryappilcation.ultils.SlidePagerAdapter;
import com.uniapp.fastdeliveryappilcation.ultils.ViewPagerAdapter;
import com.uniapp.fastdeliveryappilcation.view.IDashboardView;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment implements IDashboardView {
    private Context context;
    private SliderView sliderView;
    private TabLayout tabLayout;
    private ViewPager viewPager, pager;
    private ImageButton getmap,dash_wallet;
    private TextView dashBoardCredit, loc;

    IProductController productController;
    SharedPreferences sharedPreferences;

    public DashboardFragment(Context context, SharedPreferences sharedPreferences) {
        this.context = context;
        this.sharedPreferences = sharedPreferences;
    }

    public DashboardFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productController = new ProductController(this, view);

        sliderInitialization(view);

        menuInitialization(view);

        todayMenuInitialization(view);

        productController.handleInitCredit(sharedPreferences.getString("id", ""));
    }

    private void sliderInitialization(View view) {
        ViewPager hScroll = view.findViewById(R.id.horiscroll);
        sliderView = view.findViewById(R.id.imageSlider);

        productController.handleSliderInitialization(view, hScroll, sliderView);
    }

    private void menuInitialization(View view) {
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), 2);

        productController.handleMenuInitialization(tabLayout, viewPager, viewPagerAdapter);
    }

    private void todayMenuInitialization(View view) {
        dash_wallet=view.findViewById(R.id.dash_wallet);
        getmap = view.findViewById(R.id.getmap);
        pager = view.findViewById(R.id.pager);
        dashBoardCredit = view.findViewById(R.id.dashboard_credits);
        loc = view.findViewById(R.id.loc);

        List<Fragment> list = new ArrayList<>();
        list.add(new TVegDashboard());
        list.add(new TNonVegDashboard());
        PagerAdapter pageadapter = new SlidePagerAdapter(getChildFragmentManager(), list);
        pager.setAdapter(pageadapter);

        loc.setText(sharedPreferences.getString("location","Not Set"));

        getmap.setOnClickListener(v -> {
            startActivity(new Intent(context, MapActivity.class));
        });

        dash_wallet.setOnClickListener(v -> startActivity(new Intent(context, HistoryActivity.class)));
    }

    @Override
    public void initCredit(String number) {
        dashBoardCredit.setText((number == null || number.isEmpty()) ? "0" : number);
    }
}
