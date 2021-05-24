package com.uniapp.fastdeliveryappilcation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.uniapp.fastdeliveryappilcation.R;

public class TNonVegDashboard extends Fragment {
    private TextView dashboard_dinner;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.tnonvegdashboard, container, false);
        dashboard_dinner = rootView.findViewById(R.id.dashboard_dinner);

        return rootView;
    }
}
