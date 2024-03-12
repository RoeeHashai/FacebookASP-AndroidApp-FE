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

import java.io.IOException;
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

    public FriendsListAdapter(Context context) {
        this.mInfalter = LayoutInflater.from(context);
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
                holder.delete.setOnClickListener(v -> {
                    deleteFriend();
                });
                break;
            case "pending":
                holder.accept.setVisibility(View.VISIBLE);
                holder.delete.setVisibility(View.VISIBLE);
                holder.delete.setText(R.string.reject);
                holder.delete.setOnClickListener(v -> {
                    deleteFriend();
                });
                holder.delete.setOnClickListener(v -> {
                    // missing
                });
                break;
            case "s-pending":
                holder.accept.setVisibility(View.GONE);
                holder.delete.setVisibility(View.VISIBLE);
                holder.delete.setText(R.string.cancel_request);
                holder.delete.setOnClickListener(v -> {
                    deleteFriend();
                });
                break;
            default:
                break;
        }
    }

    private void deleteFriend() {

    }
    // Set friends data
    public void setFriends(List<Friend> s) {
        friends = s;
        notifyDataSetChanged();
    }

    // Return the number of comments
    @Override
    public int getItemCount() {
        if (friends != null)
            return friends.size();
        else return 0;
    }
}
