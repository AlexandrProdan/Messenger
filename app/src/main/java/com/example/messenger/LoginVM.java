package com.example.messenger;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginVM extends AndroidViewModel {
    public static final String TAG = "LoginVM";
    private FirebaseAuth auth;
    MutableLiveData<String> error = new MutableLiveData<>();
    MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    public LoginVM(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public void login(String email, String password){
        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                user.setValue(authResult.getUser());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                error.setValue(e.getMessage());
                Log.d(TAG, "Error: "+e.getMessage());
            }
        });
    }
}
