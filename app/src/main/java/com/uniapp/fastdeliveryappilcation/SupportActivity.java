package com.uniapp.fastdeliveryappilcation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.socketio.client.IO;
import com.uniapp.fastdeliveryappilcation.model.Message;
import com.uniapp.fastdeliveryappilcation.ultils.MessageAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SupportActivity extends AppCompatActivity {
    private ListView messageListView;
    private MessageAdapter messageAdapter;
    private EditText textField;
    private ImageButton sendButton;

    private Socket mSocket; {
        try {
            mSocket = IO.socket("https://fast-app-delivery.herokuapp.com");
        } catch (URISyntaxException ignored) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_support);
        initVariable();
        initSendMessage();
        socketConnection();
    }

    private void initVariable() {
        textField = findViewById(R.id.textField);
        sendButton = findViewById(R.id.sendButton);
        messageListView = findViewById(R.id.messageListView);

        List<Message> messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, R.layout.item_message, messageList);
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
                jsonObject.put("username", "Customer");
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

        if(isFinishing()) {
            mSocket.disconnect();
            mSocket.off("new message", onNewMessage);
            mSocket.off("typing", onTyping);
            messageAdapter.clear();
        }
    }

    Emitter.Listener onNewMessage = args -> runOnUiThread(() -> {
        try {
            JSONObject data = (JSONObject) args[0];

            String username = data.getString("username");
            String message = data.getString("message");

            Message format = new Message(username, message);
            messageAdapter.add(format);
        } catch (Exception ignored) {
        }
    });

    Emitter.Listener onTyping = args -> runOnUiThread(() -> {
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