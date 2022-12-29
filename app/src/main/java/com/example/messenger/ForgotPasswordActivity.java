package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText editTextEmailForReset;
    private Button bntResetPassword;
    private static final String EMAIL_KEY = "Email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        editTextEmailForReset = findViewById(R.id.editTextEmailForReset);
        bntResetPassword = findViewById(R.id.bntResetPassword);
        String emailFromLoginActivity = getIntent().getStringExtra(EMAIL_KEY);
        editTextEmailForReset.setText(emailFromLoginActivity);


        bntResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailForReset = editTextEmailForReset.getText().toString().trim();
                //method to reset password
            }
        });
    }

    public static Intent newIntent(Context context, String email){
        Intent intent = new Intent(context, ForgotPasswordActivity.class);
        intent.putExtra(EMAIL_KEY, email);
        return intent;
    }
}