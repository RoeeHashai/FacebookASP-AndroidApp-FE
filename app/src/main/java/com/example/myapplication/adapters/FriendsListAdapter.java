package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Base64Utils;
import com.example.myapplication.R;
import com.example.myapplication.UserListSrc;
import com.example.myapplication.entities.Comment;
import com.example.myapplication.entities.Friend;
import com.example.myapplication.viewmodels.UsersViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.FriendViewHolder> {
    // ViewHolder for individual friend
    class FriendViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final ImageView ivProfile;
        private final Button accept;
        private final Button delete;


        private FriendViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvFriendName);
            ivProfile = itemView.findViewById(R.id.ivFriendProfile);
            accept = itemView.findViewById(R.id.accepReqBT);
            delete = itemView.findViewById(R.id.deleteReqBT);
        }
    }

    private final LayoutInflater mInfalter;
    private List<Friend> friends;
    private UsersViewModel usersViewModel;

    public FriendsListAdapter(Context context, UsersViewModel usersViewModel) {
        this.mInfalter = LayoutInflater.from(context);
        this.usersViewModel = usersViewModel;
    }

    @Override
    public com.example.myapplication.adapters.FriendsListAdapter.FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the comment item layout
        View itemView = mInfalter.inflate(R.layout.friend_item, parent, false);
        return new com.example.myapplication.adapters.FriendsListAdapter.FriendViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(com.example.myapplication.adapters.FriendsListAdapter.FriendViewHolder holder, int position) {
        if (friends != null) {
            // Get the current friend
            final Friend current = friends.get(position);
            // Set author name
            holder.tvName.setText(current.getName());
            try {
                holder.ivProfile.setImageURI(Base64Utils.base64ToUri(current.getImage()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            handleStatus(holder, current);
        }
    }

    private void handleStatus(FriendViewHolder holder ,Friend current) {
        switch (current.getStatus()) {
            case "approved":
                holder.accept.setVisibility(View.GONE);
                holder.delete.setVisibility(View.VISIBLE);
                holder.delete.setText("delete");
                holder.delete.setOnClickListener(v -> {
                    deleteFriend(current.get_id());
                    friends.remove(current);
                    notifyDataSetChanged();
                });
                break;
            case "pending":
                holder.accept.setVisibility(View.VISIBLE);
                holder.delete.setVisibility(View.VISIBLE);
                holder.delete.setText(R.string.reject);
                holder.delete.setOnClickListener(v -> {
                    deleteFriend(current.get_id());
                    friends.remove(current);
                    notifyDataSetChanged();
                });
                holder.accept.setOnClickListener(v -> {
                    acceptFriend(current.get_id());
                    current.setStatus("approved");
                    notifyDataSetChanged();
                });
                break;
            case "s-pending":
                holder.accept.setVisibility(View.GONE);
                holder.delete.setVisibility(View.VISIBLE);
                holder.delete.setText(R.string.cancel_request);
                holder.delete.setOnClickListener(v -> {
                    deleteFriend(current.get_id());
                    friends.remove(current);
                    notifyDataSetChanged();
                });
                break;
            default:
                break;
        }
    }

    private void deleteFriend(String id) {
        usersViewModel.deleteRequest(id);
    }

    private void acceptFriend(String id) {
        usersViewModel.acceptRequest(id);
    }
    // Set friends data
    public void setFriends(List<Friend> s) {
        friends = sortList(s);
        notifyDataSetChanged();
    }

    // Return the number of comments
    @Override
    public int getItemCount() {
        if (friends != null)
            return friends.size();
        else return 0;
    }

    private List<Friend> sortList(List<Friend> s) {
        List<Friend> friendList = new ArrayList<>();
        for (Friend f : s) {
            switch (f.getStatus()) {
                case "approved":
                    friendList.add(f);
                    break;
                case "pending":
                    friendList.add(0, f);
                    break;
                default:
                    break;
            }
        }
        for (Friend f : s) {
            if (f.getStatus().equals("s-pending")) {
                friendList.add(f);
            }
        }
        return friendList;
    }
}
