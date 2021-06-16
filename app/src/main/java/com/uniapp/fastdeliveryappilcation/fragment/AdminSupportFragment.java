package com.uniapp.fastdeliveryappilcation.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.engineio.client.transports.Polling;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.uniapp.fastdeliveryappilcation.R;
import com.uniapp.fastdeliveryappilcation.adapter.AdminProfileAdapter;
import com.uniapp.fastdeliveryappilcation.controller.AdminController;
import com.uniapp.fastdeliveryappilcation.controller.IAdminController;
import com.uniapp.fastdeliveryappilcation.model.Message;
import com.uniapp.fastdeliveryappilcation.model.User;
import com.uniapp.fastdeliveryappilcation.ultils.AdminMessageAdapter;
import com.uniapp.fastdeliveryappilcation.ultils.MessageAdapter;
import com.uniapp.fastdeliveryappilcation.view.IAdminProfileView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AdminSupportFragment extends Fragment {
    FragmentActivity activity;
    SharedPreferences sharedPreferences;
    private ListView messageListView;
    private AdminMessageAdapter messageAdapter;
    private EditText textField;
    private ImageButton sendButton;

    private Socket mSocket; {
        try {
            IO.Options opts = new IO.Options();
            opts.transports = new String[] { Polling.NAME };
            mSocket = IO.socket("https://fast-app-delivery.herokuapp.com/", opts);
        } catch (URISyntaxException ignored) {}
    }

    public AdminSupportFragment(FragmentActivity applicationContext, SharedPreferences sharedPreferences) {
        this.activity = applicationContext;
        this.sharedPreferences = sharedPreferences;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_admin_support, container, false);

        initVariable(view);
        initSendMessage();
        socketConnection();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initVariable(View view) {
        textField = view.findViewById(R.id.textField);
        sendButton = view.findViewById(R.id.sendButton);
        messageListView = view.findViewById(R.id.messageListView);

        List<Message> messageList = new ArrayList<>();
        messageAdapter = new AdminMessageAdapter(view.getContext(), R.layout.item_message, messageList);
        messageListView.setAdapter(messageAdapter);
    }

    private void initSendMessage() {
        sendButton.setOnClickListener(t -> {
            String message = textField.getText().toString().trim();

            if(TextUtils.isEmpty(message)){
                return;
            }

            textField.setText("");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("message", message);
                jsonObject.put("username", "Admin");
                mSocket.emit("new message", jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private void socketConnection() {
        mSocket.connect();

        mSocket.on("new message", onNewMessage);
        mSocket.on("typing", onTyping);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(activity.isFinishing()) {
            mSocket.disconnect();
            mSocket.off("new message", onNewMessage);
            mSocket.off("typing", onTyping);
            messageAdapter.clear();
        }
    }

    Emitter.Listener onNewMessage = args -> activity.runOnUiThread(() -> {
        try {
            JSONObject data = (JSONObject) args[0];

            String username = data.getString("username");
            String message = data.getString("message");

            Message format = new Message(username, message);
            messageAdapter.add(format);
        } catch (Exception ignored) {
        }
    });

    Emitter.Listener onTyping = args -> activity.runOnUiThread(() -> {
        try {
            JSONObject data = (JSONObject) args[0];

            String username = data.getString("username");
            String message = data.getString("message");

            Message format = new Message(username, message);
            messageAdapter.add(format);
        } catch (Exception ignored) {
        }
    });

}
