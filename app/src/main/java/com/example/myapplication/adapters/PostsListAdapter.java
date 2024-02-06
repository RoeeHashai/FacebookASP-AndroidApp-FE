package com.example.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.CommentsPageActivity;
import com.example.myapplication.FeedPageActivity;
import com.example.myapplication.R;
import com.example.myapplication.entities.Post;

import java.util.List;

public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.PostViewHolder> {



    class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAuthor;
        private final ImageView ivProfile;
        private final TextView tvContent;
        private final ImageView ivPic;
        private final ImageButton commentBT;

        private PostViewHolder(View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            tvContent = itemView.findViewById(R.id.tvContent);
            ivPic = itemView.findViewById(R.id.ivPic);
            commentBT = itemView.findViewById(R.id.postCommentsBT);
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
            holder.ivProfile.setImageResource(current.getAuthor().getProfilePic());
            holder.tvContent.setText(current.getContent());
            holder.ivPic.setImageResource(current.getPic());
            holder.commentBT.setOnClickListener(v -> {
                Intent intent = new Intent(holder.commentBT.getContext(), CommentsPageActivity.class);
                holder.commentBT.getContext().startActivity(intent);
            });
        }
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
