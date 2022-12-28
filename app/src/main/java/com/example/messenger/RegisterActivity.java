package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "RegisterActivity";
    private EditText email;
    private EditText password;
    private EditText name;
    private EditText lastName;
    private EditText age;
    private Button btnSignUp ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        iniViews();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailStr = getTrimmedValue(email);
                String passwordStr = getTrimmedValue(password);
                String NameStr = getTrimmedValue(name);
                String lastNameStr = getTrimmedValue(lastName);
                int ageInt =Integer.parseInt(getTrimmedValue(age));
                //singUp
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
}