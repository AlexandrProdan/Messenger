package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity {
    public static final String TAG = "ForgotPasswordActivity";
    private static final String EMAIL_KEY = "Email";
    private EditText editTextEmailForReset;
    private Button bntResetPassword;
    private ForgotVM forgotVM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextEmailForReset = findViewById(R.id.editTextEmailForReset);
        bntResetPassword = findViewById(R.id.bntResetPassword);

        forgotVM = new ViewModelProvider(this).get(ForgotVM.class);
        observeVM();

        String emailFromLoginActivity = getIntent().getStringExtra(EMAIL_KEY);
        editTextEmailForReset.setText(emailFromLoginActivity);


        bntResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: HI");
                String emailForReset = editTextEmailForReset.getText().toString().trim();
                Log.d(TAG, "onClick: " +emailForReset);

                forgotVM.resetPassword(emailForReset);
            }
        });
    }
//==================================================================================================
    public static Intent newIntent(Context context, String email){
        Intent intent = new Intent(context, ForgotPasswordActivity.class);
        intent.putExtra(EMAIL_KEY, email);
        return intent;
    }

    private void observeVM(){
        forgotVM.getSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean success) {
                if(success){
                    Toast.makeText(
                            ForgotPasswordActivity.this,
                            "A reset link was sent to your email",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = LoginActivity.newIntent(ForgotPasswordActivity.this);
                    startActivity(intent);
                    finish();

                }
            }
        });

        forgotVM.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!=null) {
                    Toast.makeText(ForgotPasswordActivity.this, s, Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Registration error message: " + s);
                }
            }
        });


    }
}