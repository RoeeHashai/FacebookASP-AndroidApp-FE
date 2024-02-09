package com.example.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.CommentsPageActivity;
import com.example.myapplication.FeedPageActivity;
import com.example.myapplication.R;
import com.example.myapplication.UserListSrc;
import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.User;

import java.util.List;

public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.PostViewHolder> {


    class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAuthor;
        private final ImageView ivProfile;
        private final TextView tvDate;
        private final TextView tvContent;
        private final ImageView ivPic;
        private final ImageButton commentBT;
        private final ImageButton menuBT;

        private PostViewHolder(View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            tvDate = itemView.findViewById(R.id.postDate);
            tvContent = itemView.findViewById(R.id.tvContent);
            ivPic = itemView.findViewById(R.id.ivPic);
            commentBT = itemView.findViewById(R.id.postCommentsBT);
            menuBT = itemView.findViewById(R.id.postMenu);
        }
    }

    private final LayoutInflater mInfalter;
    private List<Post> posts;

    public PostsListAdapter(Context context) {
        this.mInfalter = LayoutInflater.from(context);
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInfalter.inflate(R.layout.post_item, parent, false);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        if (posts != null) {
            final Post current = posts.get(position);
            holder.tvAuthor.setText(current.getAuthor().getDisplayName());
            if (current.getAuthor().getUriProfilePic() != null) {
                holder.ivProfile.setImageURI(current.getAuthor().getUriProfilePic());
            }
            else {
                holder.ivProfile.setImageResource(current.getAuthor().getIntProfilePic());
            }
            holder.tvContent.setText(current.getContent());
            holder.tvDate.setText(current.getDate());
            if(current.getUriPic() == null) {
                holder.ivPic.setImageResource(current.getIntPic());
            }
            else {
                holder.ivPic.setImageURI(current.getUriPic());
            }
            holder.commentBT.setOnClickListener(v -> {
                Intent intent = new Intent(holder.commentBT.getContext(), CommentsPageActivity.class);
                User user = UserListSrc.getInstance().getActiveUser();
                int fixPos = posts.size() - position - 1;
                intent.putExtra("CURRENT_POST", fixPos);
                holder.commentBT.getContext().startActivity(intent);
            });
            holder.menuBT.setOnClickListener(v -> {
                showPostMenu(v, position);
            });
        }
    }

    private void showPostMenu(View v, int position) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu. getMenuInflater().inflate(R.menu.edit_delete_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        popupMenu.show();
    }


    public void setPosts(List<Post> s) {
        posts = s;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (posts != null)
            return posts.size();
        else return 0;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
