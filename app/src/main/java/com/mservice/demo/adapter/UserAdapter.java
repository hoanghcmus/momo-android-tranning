package com.mservice.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mservice.demo.R;
import com.mservice.demo.model.Result;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    RecyclerView userListView;
    Context mContext;
    List<Result> userList;

    public UserAdapter(Context mContext, List<Result> userList, RecyclerView userListView) {
        this.mContext = mContext;
        this.userList = userList;
        this.userListView = userListView;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_item, parent, false);



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = userListView.getChildLayoutPosition(view);
                Result user = null;
                if (userList != null && userList.size() > 0) {
                    user = userList.get(itemPosition);
                }
                if (user != null) {
                    Toast.makeText(mContext, "Click on user " + user.getName().getFirst(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Click on user at position" + itemPosition, Toast.LENGTH_SHORT).show();
                }

            }
        });
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Result user = userList.get(position);
        String fullName  = user.getName().getTitle() + ". " + user.getName().getFirst() + " " + user.getName().getLast();
        holder.tvFullName.setText(fullName);

        String userAvatarUrl = user.getPicture().getThumbnail();
        Glide.with(mContext).load(userAvatarUrl).placeholder(R.drawable.ic_launcher_background).circleCrop().into(holder.imgAvatar);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        public  final View  mView;
        TextView tvFullName;
        ImageView imgAvatar;

        public UserViewHolder(@NonNull View itemVieww) {
            super(itemVieww);
            this.mView = itemVieww;

            tvFullName = mView.findViewById(R.id.txtFullUserName);
            imgAvatar = mView.findViewById(R.id.imgAvatar);
        }
    }
}
