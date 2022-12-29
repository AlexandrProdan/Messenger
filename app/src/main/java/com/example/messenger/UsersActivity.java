package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseUser;

public class UsersActivity extends AppCompatActivity {
    UsersVM usersVM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        usersVM = new ViewModelProvider(this).get(UsersVM.class);
        observeViewModel();
    }
    //==============================================================================================
    private void observeViewModel(){
        usersVM.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser == null){
                    Intent intent = LoginActivity.newIntent(UsersActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    public static Intent newIntent(Context context){
        return  new Intent(context, UsersActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.item_logout){
            usersVM.logOut();
        }
        return super.onOptionsItemSelected(item);
    }
}