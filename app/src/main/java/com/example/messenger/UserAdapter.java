package com.example.messenger;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    public static final String TAG ="UserAdapter";
    private List<User> userList = new ArrayList<>();
    private OnUserClickListener onUserClickListener;


    public void setUserList(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    public void setOnUserClickListener(OnUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        //User user = new User("a","Alex","P",1,false);
        User user = userList.get(position);
        String userInfoStr = ""+user.getName()+" "+user.getLastName()+", "+user.getAge();
        holder.textViewUserInfo.setText(userInfoStr);
        Log.d(TAG, "onBindViewHolder: "+ userInfoStr);

        int bgResId;
        if(user.getIsOnline()){
            bgResId = R.drawable.circle_green;
        }else {
            bgResId = R.drawable.circle_red;
        }
        Drawable background = ContextCompat.getDrawable(holder.itemView.getContext(), bgResId);
        holder.viewOnlineStatus.setBackground(background);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onUserClickListener!=null){
                    onUserClickListener.onUserClick(user);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    interface OnUserClickListener{
        void onUserClick(User user);
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUserInfo;
        View viewOnlineStatus;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserInfo = itemView.findViewById(R.id.textViewUserInfo);
            viewOnlineStatus = itemView.findViewById(R.id.viewOnlineStatus);

        }
    }
}
