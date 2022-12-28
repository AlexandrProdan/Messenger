package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    private LoginVM loginVM;
    private FirebaseAuth auth;
    private String email;
    private String password;
    private EditText passwordEditText;
    private EditText emailEditText;
    private Button loginBtn;
    private TextView forgotPassword;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        loginVM = new ViewModelProvider(this).get(LoginVM.class);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailEditText.getText().toString().trim();
                password = passwordEditText.getText().toString();
                loginVM.login(email,password);
            }
        });
        observeVM();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  RegisterActivity.newIntent(LoginActivity.this);
                startActivity(intent);
            }
        });

//        auth.signOut();
//        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = auth.getCurrentUser();
//                if(user == null){
//                    Log.d(TAG, "User not authorized");
//                }else {
//                    Log.d(TAG, "User authorized " + user.getUid());
//                    Toast.makeText(LoginActivity.this, "User logged in", Toast.LENGTH_LONG).show();
//                }
//            }
//        });


       //        //create user
//        auth.createUserWithEmailAndPassword("prodan.alexandru.simion@gmail.com","111111")
//                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                        FirebaseUser user = auth.getCurrentUser();
//                        if(user == null){
//                            Log.d(TAG, "User not logged in");
//                        }else {
//                            Log.d(TAG, "User logged in");
//                            Toast.makeText(MainActivity.this, "User logged in", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, e.getMessage());
//                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });

//        auth.sendPasswordResetEmail("prodan.alexandru.simion@gmail.com").addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                Log.d(TAG, "Password reset email successfully sent");
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d(TAG, e.getMessage());
//                Log.d(TAG, "Password reset email not send");
//            }
//        });

    }

    void initViews(){
        emailEditText = findViewById(R.id.editTextEmail);
        email = emailEditText.getText().toString().trim();

        passwordEditText = findViewById(R.id.editTextPassword);
        password = passwordEditText.getText().toString();

        loginBtn = findViewById(R.id.btnLogin);
        forgotPassword = findViewById(R.id.textViewForgotPassword);
        register = findViewById(R.id.textViewRegister);

    }

    private void observeVM(){
        loginVM.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser!=null){
                    Toast.makeText(LoginActivity.this,
                            "User"+firebaseUser.getUid()+"logged in",
                            Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG, firebaseUser.toString());
            }
        });


        loginVM.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Error: "+s);
            }
        });
    }
}