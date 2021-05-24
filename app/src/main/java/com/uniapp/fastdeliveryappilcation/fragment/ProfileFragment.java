package com.uniapp.fastdeliveryappilcation.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.uniapp.fastdeliveryappilcation.FeedbackActivity;
import com.uniapp.fastdeliveryappilcation.MapActivity;
import com.uniapp.fastdeliveryappilcation.PaymentActivity;
import com.uniapp.fastdeliveryappilcation.R;
import com.uniapp.fastdeliveryappilcation.TrialOrderActivity;
import com.uniapp.fastdeliveryappilcation.controller.UserController;
import com.uniapp.fastdeliveryappilcation.model.User;
import com.uniapp.fastdeliveryappilcation.ultils.ActivityConstants;
import com.uniapp.fastdeliveryappilcation.ultils.CustomDialogFragment;
import com.uniapp.fastdeliveryappilcation.ultils.EditProfileBottomSheet;
import com.uniapp.fastdeliveryappilcation.view.IProfileView;

import org.jetbrains.annotations.NotNull;

public class ProfileFragment extends Fragment implements IProfileView {
    private Context context;
    private TextView name, email, phone, editProfile;
    private SharedPreferences sharedPreferences;

    private Button logout;
    private LinearLayout manageAddress, payment, orderTrial, shareApp, leaveFeedBack,contactUs;

    public ProfileFragment(Context context, SharedPreferences sharedPreferences) {
        this.context = context;
        this.sharedPreferences = sharedPreferences;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserController userController = new UserController(null,null,view, this, null);

        initData(view);

        initAddress(view);
        initOrder(view);
        initLogOut(view);
        initFeedBack(view);
        initPayment(view);
        initShare(view);
        initContactUs(view);
        initEditProfile(view);
    }

    @Override
    public void loadUserData(User user, View view) {
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.number);

        name.setText(user.getName());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());
    }

    private void initData(View view) {
        User user = new User();
        user.setName(sharedPreferences.getString("name",""));
        user.setEmail(sharedPreferences.getString("email",""));
        user.setPhone(sharedPreferences.getString("phone",""));

        loadUserData(user, view);
    }

    private void initAddress(View view) {
        manageAddress = view.findViewById(R.id.manage_address);
        manageAddress.setOnClickListener(v -> startActivity(new Intent(context, MapActivity.class).putExtra("callingActivity",002)));
    }

    private void initOrder(View view) {
        orderTrial = view.findViewById(R.id.order_trial);
        orderTrial.setOnClickListener(v -> startActivity(new Intent(context, TrialOrderActivity.class)));
    }

    private void initLogOut(View view) {
        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(v -> {
            String title = "Are you sure want to logout";
            String p = "Logout";
            String n = "Cancel";
            CustomDialogFragment dialog  = new CustomDialogFragment(context,title,p,n, ActivityConstants.ProfileFragment);
            dialog.show(getFragmentManager(),"dialog");
        });
    }

    private void initFeedBack(View view) {
        leaveFeedBack = view.findViewById(R.id.leave_feedback);
        leaveFeedBack.setOnClickListener(v -> startActivity(new Intent(context, FeedbackActivity.class)));
    }

    
    private void initPayment(View view) {
        payment = view.findViewById(R.id.payment);
        payment.setOnClickListener(v -> startActivity(new Intent(context, PaymentActivity.class)));
    }

    private void initShare(View view) {
        shareApp = view.findViewById(R.id.share_app);
        shareApp.setOnClickListener(v -> {
            //TODO: Add a share link
            Intent i=new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT,"Share");
            i.putExtra(Intent.EXTRA_TEXT, "Share link");
            startActivity(Intent.createChooser(i,"Share via"));
        });
    }

    private void initContactUs(View view) {
        contactUs=view.findViewById(R.id.contact_us);
        contactUs.setOnClickListener(v -> {
            //TODO:Add Restaurant Phone number
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:0768185158"));
            startActivity(intent);
        });
    }

    private void initEditProfile(View view) {
        editProfile = view.findViewById(R.id.editProfile);
        editProfile.setOnClickListener(v -> {
            EditProfileBottomSheet editProfileBottomSheet = new EditProfileBottomSheet(context,sharedPreferences.getString("phone",""),
                    sharedPreferences.getString("email",""),sharedPreferences.getString("phone",""));
            editProfileBottomSheet.show(getChildFragmentManager(),"bottomSheet");
        });
    }
}
