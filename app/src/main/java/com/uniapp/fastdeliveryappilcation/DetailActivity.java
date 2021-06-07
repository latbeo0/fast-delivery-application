package com.uniapp.fastdeliveryappilcation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.uniapp.fastdeliveryappilcation.adapter.OptionAdapter;
import com.uniapp.fastdeliveryappilcation.adapter.PlanAdapter;
import com.uniapp.fastdeliveryappilcation.model.Option;
import com.uniapp.fastdeliveryappilcation.model.Plan;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private List optionList;
    private List planList;
    private PlanAdapter planAdapter;
    private OptionAdapter optionAdapter;
    private RecyclerView recyclerView,recyclerViewPlan;
    private TextView title,desc,veg,nonveg;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_detail);
        extras = getIntent().getExtras();

        initOptions();
    }

    private void initOptions() {
        title=findViewById(R.id.title);
        desc=findViewById(R.id.desc);
        veg=findViewById(R.id.veg);
        nonveg=findViewById(R.id.nonveg);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerViewPlan=findViewById(R.id.recycler_view_plan);

        title.setText(extras.getString("title"));
        desc.setText(extras.getString("desc"));
        if(extras.getInt("meal") == 0)
        {
            veg.setVisibility(View.INVISIBLE);
            nonveg.setVisibility(View.VISIBLE);
        }

        optionList = new ArrayList<>();
        planList =new ArrayList<>();

        optionList.add(new Option(R.drawable.like, "Skip Meal", "Sudden changes? Skip upcoming meal"));
        optionList.add(new Option(R.drawable.like, "Pause Plan", "Going out of town? Pause your meal for those days"));
        optionList.add(new Option(R.drawable.like, "Cancel Plan", "Never feel bound. Cancel plan anytime if you're unhappy"));

        planList.add(new Plan("07 days","60",extras.getInt("meal"),extras.getInt("combo")));
        planList.add(new Plan("14 days","75",extras.getInt("meal"),extras.getInt("combo")));
        planList.add(new Plan("30 days","120",extras.getInt("meal"),extras.getInt("combo")));

        optionAdapter=new OptionAdapter(optionList);
        planAdapter = new PlanAdapter(planList);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerViewPlan.setLayoutManager(layoutManager);
        recyclerView.setAdapter(planAdapter);
        recyclerViewPlan.setAdapter(optionAdapter);
    }
}