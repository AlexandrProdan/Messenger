package com.example.messenger;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotVM extends AndroidViewModel {
    private FirebaseAuth auth;
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> success = new MutableLiveData<>();


    public ForgotVM(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
    }
    public LiveData<Boolean> getSuccess() {
        return success;
    }
    public LiveData<String> getError() {
        return error;
    }


    public void resetPassword(String email) {
        auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                success.setValue(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                error.setValue(e.getMessage());
            }
        });
    }
}
