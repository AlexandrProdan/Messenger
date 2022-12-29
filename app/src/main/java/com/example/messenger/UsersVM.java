package com.example.messenger;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UsersVM extends AndroidViewModel {
    private FirebaseAuth auth;
    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();


    public UsersVM(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    user.setValue(firebaseAuth.getCurrentUser());
            }
        });
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public void logOut(){
        auth.signOut();
    }
}
