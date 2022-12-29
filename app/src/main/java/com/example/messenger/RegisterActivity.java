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

import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "RegisterActivity";
    private EditText email;
    private EditText password;
    private EditText name;
    private EditText lastName;
    private EditText age;
    private Button btnSignUp ;
    private RegistrationVM registrationVM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        iniViews();
        registrationVM = new ViewModelProvider(this).get(RegistrationVM.class);

        observeVM();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailStr = getTrimmedValue(email);
                String passwordStr = getTrimmedValue(password);
                String NameStr = getTrimmedValue(name);
                String lastNameStr = getTrimmedValue(lastName);
                int ageInt =Integer.parseInt(getTrimmedValue(age));
                registrationVM.signUp(
                        emailStr,
                        passwordStr,
                        NameStr,
                        lastNameStr,
                        ageInt
                );
            }
        });


    }
    void iniViews(){
         email = findViewById(R.id.editTextEmailRegister);
         password = findViewById(R.id.editTextPasswordRegister);
         name = findViewById(R.id.editTextName);
         lastName = findViewById(R.id.editTextLastName);
         age = findViewById(R.id.editTextAge);
         btnSignUp = findViewById(R.id.btnSignUp);
    }

    private String getTrimmedValue(EditText editText){
        return editText.getText().toString().trim();
    }

    public static Intent newIntent(Context context){
        return new Intent(context, RegisterActivity.class);
    }

    private void observeVM(){
        registrationVM.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser != null){
                    Intent intent = UsersActivity.newIntent(RegisterActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });


        registrationVM.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!=null){
                    Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Registration error message: "+s);
                }
            }
        });
    }
}