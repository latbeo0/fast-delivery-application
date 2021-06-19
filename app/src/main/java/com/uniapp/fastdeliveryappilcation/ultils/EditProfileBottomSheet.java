package com.uniapp.fastdeliveryappilcation.ultils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.uniapp.fastdeliveryappilcation.R;
import com.uniapp.fastdeliveryappilcation.VerificationActivity;
import com.uniapp.fastdeliveryappilcation.controller.UserController;
import com.uniapp.fastdeliveryappilcation.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfileBottomSheet extends BottomSheetDialogFragment {
    private String number,email,username, Id, amount, address;
    private EditText editTextNumber,editTextEmail,editTextUsername;
    private UserController userController;
    private Button update;
    private Context context;


    public EditProfileBottomSheet(Context context, UserController userController, String Id , String username, String email, String number, String amount, String address) {
        this.context = context;
        this.number = number;
        this.email = email;
        this.username = username;
        this.Id = Id;
        this.userController = userController;
        this.amount = amount;
        this.address = address;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile_layout,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setText();
        update.setOnClickListener(view -> {
            String n  = editTextNumber.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String userName = editTextUsername.getText().toString().trim();

            //TODO: Functionality Add 1. Send OTP 2. Verify Otp 3. Logout User 4. Clear Session
            Log.i(EditProfileBottomSheet.class.getSimpleName(),n);

            if (!validateData()) {
                Toast.makeText(getActivity(),"Wrong input type!",Toast.LENGTH_SHORT).show();
                return;
            }

            userController.updateData(new User(Long.parseLong(Id), userName, email, n, amount, address), view);

//            if(!number.equals(n) && n.length() == 10){
//                Log.i(EditProfileBottomSheet.class.getSimpleName(),n);
//                updatePhoneNumber(n);
//            }
        });

    }

    private void setText() {
        //Setting the text in the editText
        editTextUsername.setText(username);
        editTextUsername.setSelection(username.length());
        editTextNumber.setText(number);
        editTextNumber.getText().clearSpans();
        editTextNumber.setSelection(number.length());
        editTextEmail.setText(email);
        editTextEmail.setSelection(email.length());
    }

    private void updatePhoneNumber(String new_number) {
        //TODO: Functionality Add 1. Send OTP 2. Verify Otp 3. Logout User 4. Clear Session
        startActivity(new Intent(context, VerificationActivity.class));
    }

    private void init(View view){
        editTextNumber = view.findViewById(R.id.edit_profile_number);
        editTextEmail  = view.findViewById(R.id.edit_profile_email);
        editTextUsername = view.findViewById(R.id.edit_profile_username);
        update = view.findViewById(R.id.edit_profile_update_button);
    }

    private boolean validateData() {
        if (editTextNumber.getText().toString().trim().isEmpty()
        || editTextEmail.getText().toString().trim().isEmpty()
        || editTextUsername.getText().toString().trim().isEmpty()) {
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.getText().toString()).matches()) {
            return false;
        }

        Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(editTextUsername.getText().toString().trim());

        if (matcher.find()) {
            return false;
        }

        return true;
    }
}
