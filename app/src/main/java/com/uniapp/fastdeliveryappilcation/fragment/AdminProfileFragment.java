package com.uniapp.fastdeliveryappilcation.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uniapp.fastdeliveryappilcation.R;
import com.uniapp.fastdeliveryappilcation.adapter.AdminMenuAdapter;
import com.uniapp.fastdeliveryappilcation.adapter.AdminProfileAdapter;
import com.uniapp.fastdeliveryappilcation.controller.AdminController;
import com.uniapp.fastdeliveryappilcation.controller.IAdminController;
import com.uniapp.fastdeliveryappilcation.model.Slider;
import com.uniapp.fastdeliveryappilcation.model.User;
import com.uniapp.fastdeliveryappilcation.view.IAdminMenuView;
import com.uniapp.fastdeliveryappilcation.view.IAdminProfileView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AdminProfileFragment extends Fragment implements IAdminProfileView {
    private RecyclerView recyclerView;
    IAdminController adminController;
    Context context;
    SharedPreferences sharedPreferences;

    public AdminProfileFragment(Context applicationContext, SharedPreferences sharedPreferences) {
        this.context = applicationContext;
        this.sharedPreferences = sharedPreferences;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adminController = new AdminController(this, view);
        adminController.InitProfileMenu();
    }

    @Override
    public void initData(View view, List<User> list) {
        recyclerView = view.findViewById(R.id.profile_list);
        AdminProfileAdapter adminProfileAdapter = new AdminProfileAdapter(context, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adminProfileAdapter);
    }

}
