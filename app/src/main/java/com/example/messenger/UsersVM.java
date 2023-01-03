package com.example.messenger;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersVM extends AndroidViewModel {
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersRef;
    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private MutableLiveData<List<User>> userListFromDb = new MutableLiveData<>();


    public UsersVM(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    user.setValue(firebaseAuth.getCurrentUser());
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        usersRef = firebaseDatabase.getReference("Users");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUser currentFirebaseUser = auth.getCurrentUser();
                if (currentFirebaseUser == null) {
                    return;
                }
                List<User> usersFromDb = new ArrayList<>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);

                    if (user == null) {
                        return;
                    }
                    if (!currentFirebaseUser.getUid().equals(user.getId())){
                    usersFromDb.add(user);
                    }

                }
                userListFromDb.setValue(usersFromDb);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public LiveData<FirebaseUser> getFirebaseUser() {return user;}


    public LiveData<List<User>> getUserListFromDb() {return userListFromDb;}


    public void setUserOnline(Boolean online){
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if(firebaseUser == null){return;}
        usersRef.child(firebaseUser.getUid()).child("isOnline").setValue(online);

    }

    public void logOut(){
        setUserOnline(false);
        auth.signOut();


    }
}
