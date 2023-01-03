package com.example.messenger;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatVM extends ViewModel {
    private MutableLiveData<List<Message>> messagesListLD = new MutableLiveData<>();
    private MutableLiveData<User> otherUserLD = new MutableLiveData<>();
    private MutableLiveData<Boolean> messageSentLD = new MutableLiveData<>();
    private MutableLiveData<String> errorLD = new MutableLiveData<>();

    private String currentUserId;
    private String otherUserId;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference refUsers = database.getReference("Users");
    private DatabaseReference refMessages = database.getReference("Messages");
    //---------------------------------Constructor--------------------------------------------------
    public ChatVM(String currentUserId, String otherUserId) {
        this.currentUserId = currentUserId;
        this.otherUserId = otherUserId;

        refUsers.child(otherUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User otherUserFromDb = snapshot.getValue(User.class);
                otherUserLD.setValue(otherUserFromDb);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        refMessages.child(currentUserId).child(otherUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Message> messageListFromDb = new ArrayList<>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Message messageFromDB = dataSnapshot.getValue(Message.class);
                    messageListFromDb.add(messageFromDB);
                }
                messagesListLD.setValue(messageListFromDb);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //----------------------------------getters----------------------------------------------------
    public LiveData<List<Message>> getMessagesListLD() {
        return messagesListLD;
    }

    public LiveData<User> getOtherUserLD() {
        return otherUserLD;
    }

    public LiveData<Boolean> getMessageSentLD() {
        return messageSentLD;
    }

    public LiveData<String> getErrorLD() {
        return errorLD;
    }

    //-------------------------------sendMessage-------------------------------------------------
    public void sendMessage(Message message){
        refMessages.child(message.getSenderId())
                .child(message.getReceiverId())
                .push()
                .setValue(message)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        refMessages.child(message.getReceiverId())
                                .child(message.getSenderId())
                                .push()
                                .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        messageSentLD.setValue(true);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        errorLD.setValue(e.getMessage());
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        errorLD.setValue(e.getMessage());
                    }
                });
    }
    //------------------------------------------setUserOnline---------------------------------------
    public void setUserOnline(Boolean online){
        refUsers.child(currentUserId).child("isOnline").setValue(online);
        if(online){}
    }
}
