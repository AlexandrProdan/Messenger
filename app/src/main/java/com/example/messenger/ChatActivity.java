package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";
    private static final String EXTRA_CURRENT_USER_ID = "currentUserID";
    private static final String EXTRA_OTHER_USER_ID = "otherUserID";
    private String currentUserId;
    private String otherUserId;

    private TextView textViewTitle;
    private View view_online_status;
    private EditText editTextInputMessage;
    private ImageView imageViewSend;
    private RecyclerView recyclerViewMessages;
    private MessagesAdapter messagesAdapter;

    ChatVM chatVM;
    ChatVMFactory chatVMFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caht);
        initViews();
        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        otherUserId = getIntent().getStringExtra(EXTRA_OTHER_USER_ID);

        chatVMFactory = new ChatVMFactory(currentUserId, otherUserId);
        chatVM = new ViewModelProvider(this, chatVMFactory).get(ChatVM.class);

        messagesAdapter = new MessagesAdapter(currentUserId);
        recyclerViewMessages.setAdapter(messagesAdapter);

        observeViewModel();

        imageViewSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageText = editTextInputMessage.getText().toString().trim();
                Message message = new Message(messageText, currentUserId, otherUserId);
                chatVM.sendMessage(message);
            }
        });

    }

    void initViews(){
        textViewTitle = findViewById(R.id.textViewTitle);
        view_online_status = findViewById(R.id.view_online_status);
        editTextInputMessage = findViewById(R.id.editTextInputMessage);
        imageViewSend = findViewById(R.id.imageViewSend);
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
    }

    void observeViewModel(){
        chatVM.getMessagesListLD().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                messagesAdapter.setMessageList(messages);
            }
        });

        chatVM.getErrorLD().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMsg) {
                if(errorMsg != null){
                    Toast.makeText(ChatActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Error "+errorMsg);
                }
            }
        });

        chatVM.getMessageSentLD().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean messageSent) {
                if(messageSent){
                    editTextInputMessage.setText("");
                }
            }
        });

        chatVM.getOtherUserLD().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                String userNameLastname = ""+user.getName()+" "+user.getLastName();
                textViewTitle.setText(userNameLastname);
            }
        });

    }

    public static Intent newIntent(Context context, String currentUserId, String otherUserId){
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId);
        intent.putExtra(EXTRA_OTHER_USER_ID, otherUserId);
        return intent;
    }
}