package com.uniapp.fastdeliveryappilcation.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uniapp.fastdeliveryappilcation.AdminLoginActivity;
import com.uniapp.fastdeliveryappilcation.AdminMenuItemActivity;
import com.uniapp.fastdeliveryappilcation.HistoryActivity;
import com.uniapp.fastdeliveryappilcation.R;
import com.uniapp.fastdeliveryappilcation.adapter.AdminMenuAdapter;
import com.uniapp.fastdeliveryappilcation.controller.AdminController;
import com.uniapp.fastdeliveryappilcation.controller.IAdminController;
import com.uniapp.fastdeliveryappilcation.model.Slider;
import com.uniapp.fastdeliveryappilcation.view.IAdminMenuView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AdminMenuFragment extends Fragment implements IAdminMenuView {
    private RecyclerView recyclerView;
    private TextView addBtn;
    List<Slider> sliderList;
    IAdminController adminController;
    Context context;
    SharedPreferences sharedPreferences;

    public AdminMenuFragment(Context applicationContext, SharedPreferences sharedPreferences) {
        this.context = applicationContext;
        this.sharedPreferences = sharedPreferences;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adminController = new AdminController(this, view);
        adminController.InitMenu();

        initAddButton(view);
    }

    @Override
    public void initData(View view, List<Slider> list) {
        recyclerView = view.findViewById(R.id.subscribtion_list);
        AdminMenuAdapter adminMenuAdapter = new AdminMenuAdapter(context, list, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adminMenuAdapter);
    }

    private void initAddButton(View view) {
        addBtn = view.findViewById(R.id.add);

        addBtn.setOnClickListener(t -> {
            startActivity(new Intent(context, AdminMenuItemActivity.class));
        });
    }

}
