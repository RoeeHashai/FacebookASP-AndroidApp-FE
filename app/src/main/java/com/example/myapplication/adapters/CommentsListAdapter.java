package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.UserListSrc;
import com.example.myapplication.entities.Comment;
import com.example.myapplication.entities.Post;

import java.util.List;

public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.CommentViewHolder> {



    class CommentViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAuthor;
        private final ImageView ivProfile;
        private final TextView tvContent;
        private final EditText etContent;
        private final ImageView ivMenu;
        private final Button makeCommentChangeBT;

        private CommentViewHolder(View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvComAuthor);
            ivProfile = itemView.findViewById(R.id.ivComProfile);
            tvContent = itemView.findViewById(R.id.tvComContent);
            etContent = itemView.findViewById(R.id.etComContent);
            makeCommentChangeBT = itemView.findViewById(R.id.makeCommentChangeBT);
            ivMenu = itemView.findViewById(R.id.commentMenuBT);
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
            if (current.getAuthor().getUriProfilePic() != null) {
                holder.ivProfile.setImageURI(current.getAuthor().getUriProfilePic());
            }
            else {
                holder.ivProfile.setImageResource(current.getAuthor().getIntProfilePic());
            }
            holder.tvContent.setText(current.getContent());
            holder.makeCommentChangeBT.setOnClickListener(v -> {
                holder.tvContent.setVisibility(View.VISIBLE);
                holder.tvContent.setText(holder.etContent.getText());
                holder.etContent.setVisibility(View.GONE);
                holder.makeCommentChangeBT.setVisibility(View.GONE);
                comments.get(position).setContent(holder.etContent.getText().toString());
            });
            String postUser = comments.get(position).getAuthor().getUserName();
            String currentUser = UserListSrc.getInstance().getActiveUser().getUserName();
            if (postUser.equals(currentUser)) {
                holder.ivMenu.setOnClickListener(v -> {
                    showMenu(v, position, holder);
                });
            } else {
                holder.ivMenu.setVisibility(View.GONE);
            }
        }
    }
    private void showMenu(View v, int position, CommentViewHolder holder) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.getMenuInflater().inflate(R.menu.edit_delete_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.deleteItem) {
                    comments.remove(position);
                    notifyDataSetChanged();
                    return true;
                } else if (item.getItemId() == R.id.editItem) {
                    holder.tvContent.setVisibility(View.GONE);
                    holder.etContent.setText(holder.tvContent.getText());
                    holder.etContent.setVisibility(View.VISIBLE);
                    holder.makeCommentChangeBT.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        popupMenu.show();
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

