package com.example.messenger;

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
        setContentView(R.layout.activity_login);
        initViews();
        loginVM = new ViewModelProvider(this).get(LoginVM.class);
        observeVM();
        setClickListeners();

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
                    Intent intent = UsersActivity.newIntent(LoginActivity.this);
                    startActivity(intent);
                    finish();
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

    private void setClickListeners(){
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailEditText.getText().toString().trim();
                password = passwordEditText.getText().toString();
                loginVM.login(email,password);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  RegisterActivity.newIntent(LoginActivity.this);
                startActivity(intent);
            }
        });


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailEditText.getText().toString().trim();
                Intent intent = ForgotPasswordActivity.newIntent(LoginActivity.this, email);
                startActivity(intent);
            }
        });
    }
}