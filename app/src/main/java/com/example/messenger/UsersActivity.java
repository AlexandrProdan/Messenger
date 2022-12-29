package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UsersActivity extends AppCompatActivity {
    private UsersVM usersVM;
    private RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        initViews();
        usersVM = new ViewModelProvider(this).get(UsersVM.class);
        observeViewModel();

        List<User> users = new ArrayList<>();
        for (int i=0; i<30;i++){
            User user = new User(
                    "id "+i, "Name "+i,"Lastname "+i,i, new Random().nextBoolean()
            );
            users.add(user);
        }
        userAdapter.setUserList(users);
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

    private void initViews(){
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        userAdapter = new UserAdapter();

        recyclerViewUsers.setAdapter(userAdapter);
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