package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    public static final String EXTRA_CURRENT_USER_ID = "currentUserID";
    public static final String EXTRA_OTHER_USER_ID = "otherUserID";
    private String currentUserId;
    private String otherUserId;

    private TextView textViewTitle;
    private View view_online_status;
    private EditText editTextInputMessage;
    private ImageView imageViewSend;
    private RecyclerView recyclerViewMessages;
    private MessagesAdapter messagesAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caht);
        initViews();
        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        otherUserId = getIntent().getStringExtra(EXTRA_OTHER_USER_ID);

        messagesAdapter = new MessagesAdapter(currentUserId);
        recyclerViewMessages.setAdapter(messagesAdapter);
//--------------Tests--------------
        List<Message> messageList = new ArrayList<>();
        for (int i=0; i<10;i++){
            Message message = new Message(
                    "Text "+i,
                    currentUserId,
                    otherUserId
            );
            messageList.add(message);
        }

        for (int i=0; i<10;i++){
            Message message = new Message(
                    "Text "+i,
                    otherUserId,
                    currentUserId
            );
            messageList.add(message);
        }

        messagesAdapter.setMessageList(messageList);
    }

    void initViews(){
        textViewTitle = findViewById(R.id.textViewTitle);
        view_online_status = findViewById(R.id.view_online_status);
        editTextInputMessage = findViewById(R.id.editTextInputMessage);
        imageViewSend = findViewById(R.id.imageViewSend);
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
    }

    public static Intent newIntent(Context context, String currentUserId, String otherUserId){
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId);
        intent.putExtra(EXTRA_OTHER_USER_ID, otherUserId);
        return intent;
    }
}