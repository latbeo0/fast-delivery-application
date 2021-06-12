package com.uniapp.fastdeliveryappilcation.fragment;

import android.content.Context;
import android.content.Intent;
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

import com.uniapp.fastdeliveryappilcation.MainActivity;
import com.uniapp.fastdeliveryappilcation.R;
import com.uniapp.fastdeliveryappilcation.adapter.ActiveSubscriptionAdapter;
import com.uniapp.fastdeliveryappilcation.controller.IProductController;
import com.uniapp.fastdeliveryappilcation.controller.ProductController;
import com.uniapp.fastdeliveryappilcation.model.ActiveSubscription;
import com.uniapp.fastdeliveryappilcation.view.ISubscriptionView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.N)
public class SubscriptionFragment extends Fragment implements ISubscriptionView {



    private Context context;
    private RecyclerView recyclerView;
    List<ActiveSubscription> subcriptionList;
    final String[] days = {""};
    final String[] date_Of_activation={""};
    final String[] no_of_dabba={""};
    IProductController productController;
    SharedPreferences sharedPreferences;

    public SubscriptionFragment(Context context, SharedPreferences sharedPreferences) {
        this.context = context;
        this.sharedPreferences = sharedPreferences;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subscription, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productController = new ProductController(this, view);
        productController.handleInitSubscriptionData(sharedPreferences.getString("id", ""));
    }

    @Override
    public void initData(View view, Map<String, Object> sublist) {


        recyclerView = view.findViewById(R.id.subscribtion_list);
        subcriptionList = new ArrayList<>();

        days[0] = String.valueOf(sublist.get("days"));
        date_Of_activation[0]= String.valueOf(sublist.get("date_Of_activation"));

        no_of_dabba[0]=String.valueOf(sublist.get("no_of_dabba"));
        subcriptionList.add(new ActiveSubscription(Long.parseLong(String.valueOf(sublist.get("id"))), days[0] , date_Of_activation[0],"13-06-2021",no_of_dabba[0]));
        ActiveSubscriptionAdapter subcriptionAdapter = new ActiveSubscriptionAdapter(getContext(),this, subcriptionList, productController);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(subcriptionAdapter);
    }

    @Override
    public void handleRemove() {
        startActivity(new Intent(getActivity(), MainActivity.class));
    }
}
