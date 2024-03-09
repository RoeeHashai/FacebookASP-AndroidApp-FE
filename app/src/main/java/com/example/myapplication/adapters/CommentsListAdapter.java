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

import com.example.myapplication.R;
import com.example.myapplication.UserListSrc;
import com.example.myapplication.entities.Comment;

import java.util.List;
/**
 * Adapter for displaying a list of comments in a RecyclerView.
 */
public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.CommentViewHolder> {

    // ViewHolder for individual comments
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
        // Inflate the comment item layout
        View itemView = mInfalter.inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        /*if (comments != null) {
            // Get the current comment
            final Comment current = comments.get(position);
            // Set author name
            holder.tvAuthor.setText(current.getAuthor().getName());
            // Set profile picture
            if (current.getAuthor().getUriProfilePic() != null) {
                holder.ivProfile.setImageURI(current.getAuthor().getUriProfilePic());
            } else {
                holder.ivProfile.setImageResource(current.getAuthor().getIntProfilePic());
            }
            // Set comment content
            holder.tvContent.setText(current.getContent());
            // Handle edit comment functionality
            holder.makeCommentChangeBT.setOnClickListener(v -> {
                holder.tvContent.setVisibility(View.VISIBLE);
                holder.tvContent.setText(holder.etContent.getText());
                holder.etContent.setVisibility(View.GONE);
                holder.makeCommentChangeBT.setVisibility(View.GONE);
                comments.get(position).setContent(holder.etContent.getText().toString());
            });
            // Show edit/delete menu only for the author of the comment
            String postUser = comments.get(position).getAuthor().getEmail();
            String currentUser = UserListSrc.getInstance(holder.etContent.getContext()).getActiveUser().getEmail();
            if (postUser.equals(currentUser)) {
                holder.ivMenu.setOnClickListener(v -> {
                    showMenu(v, position, holder);
                });
            } else {
                holder.ivMenu.setVisibility(View.GONE);
            }
        }*/
    }

    // Show popup menu for edit/delete options
    private void showMenu(View v, int position, CommentViewHolder holder) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.getMenuInflater().inflate(R.menu.edit_delete_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.deleteItem) {
                    // Delete the comment
                    comments.remove(position);
                    notifyDataSetChanged();
                    return true;
                } else if (item.getItemId() == R.id.editItem) {
                    // Edit the comment
                    holder.tvContent.setVisibility(View.GONE);
                    holder.etContent.setText(holder.tvContent.getText());
                    holder.etContent.setVisibility(View.VISIBLE);
                    holder.makeCommentChangeBT.setVisibility(View.VISIBLE);
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    // Set comments data
    public void setComment(List<Comment> s) {
        comments = s;
        notifyDataSetChanged();
    }

    // Return the number of comments
    @Override
    public int getItemCount() {
        if (comments != null)
            return comments.size();
        else return 0;
    }
}

