package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Comment;
import com.example.myapplication.entities.Post;

import java.util.List;

public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.CommentViewHolder> {



    class CommentViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAuthor;
        private final ImageView ivProfile;
        private final TextView tvContent;

        private CommentViewHolder(View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvComAuthor);
            ivProfile = itemView.findViewById(R.id.ivComProfile);
            tvContent = itemView.findViewById(R.id.tvComContent);
        }
    }

    private final LayoutInflater mInfalter;
    private List<Comment> comments;

    public CommentsListAdapter(Context context) {
        this.mInfalter = LayoutInflater.from(context);
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInfalter.inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        if (comments != null) {
            final Comment current = comments.get(position);
            holder.tvAuthor.setText(current.getAuthor().getDisplayName());
            holder.ivProfile.setImageResource(current.getAuthor().getProfilePic());
            holder.tvContent.setText(current.getContent());
        }
    }

    public void setComment(List<Comment> s) {
        comments = s;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (comments != null)
            return comments.size();
        else return 0;
    }
}

