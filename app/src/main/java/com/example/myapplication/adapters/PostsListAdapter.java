package com.example.myapplication.adapters;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
/**
 * Adapter for displaying a list of posts in a RecyclerView.
 */
public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.PostViewHolder> {


    private static final int REQUEST_IMAGE_CAPTURE = 1;

    public int getPosOfEditedImage() {
        return posOfEditedImage;
    }
    /**
     * ViewHolder for individual posts
     */
    class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAuthor;
        private final ImageView ivProfile;
        private final TextView tvDate;
        private final TextView tvContent;
        private final TextView etContent;
        private final Button makePostChangeBT;
        private final ImageButton editImage;
        private final ImageButton deleteImage;
        private final ImageView ivPic;
        private final ImageButton commentBT;
        private final ImageButton menuBT;
        private final ImageButton likeBT;
        private final ImageButton shareBT;
        private final TextView likeCounter;

        private PostViewHolder(View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            tvDate = itemView.findViewById(R.id.postDate);
            tvContent = itemView.findViewById(R.id.tvContent);
            etContent = itemView.findViewById(R.id.tvEditContent);
            makePostChangeBT = itemView.findViewById(R.id.makePostChangeBT);
            editImage = itemView.findViewById(R.id.editImageBT);
            deleteImage = itemView.findViewById(R.id.deleteImageBT);
            ivPic = itemView.findViewById(R.id.ivPic);
            commentBT = itemView.findViewById(R.id.postCommentsBT);
            menuBT = itemView.findViewById(R.id.postMenu);
            shareBT = itemView.findViewById(R.id.shareBT);
            likeBT = itemView.findViewById(R.id.likeBT);
            likeCounter = itemView.findViewById(R.id.likeCounter);
        }
    }

    private final LayoutInflater mInfalter;
    private List<Post> posts;
    private int posOfEditedImage;

    public PostsListAdapter(Context context) {
        this.mInfalter = LayoutInflater.from(context);
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the post item layout
        View itemView = mInfalter.inflate(R.layout.post_item, parent, false);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        Context context = holder.shareBT.getContext();
        final int revposition = posts.size() - position - 1;
        if (posts != null) {
            final Post current = posts.get(revposition);
            // Set author name
            holder.tvAuthor.setText(current.getAuthor().getDisplayName());
            // Set profile picture
            if (current.getAuthor().getUriProfilePic() != null) {
                holder.ivProfile.setImageURI(current.getAuthor().getUriProfilePic());
            } else {
                holder.ivProfile.setImageResource(current.getAuthor().getIntProfilePic());
            }
            // Set post content
            holder.tvContent.setText(current.getContent());
            // Set post date
            holder.tvDate.setText(current.getDate());
            // Set post image
            if (current.getUriPic() == null) {
                holder.ivPic.setImageResource(current.getIntPic());
            } else {
                holder.ivPic.setImageURI(current.getUriPic());
            }
            // Handle editing post functionality
            holder.makePostChangeBT.setOnClickListener(v -> {
                holder.tvContent.setHeight(holder.etContent.getHeight());
                holder.tvContent.setText(holder.etContent.getText());
                holder.etContent.setVisibility(View.GONE);
                holder.makePostChangeBT.setVisibility(View.GONE);
                holder.editImage.setVisibility(View.GONE);
                holder.deleteImage.setVisibility(View.GONE);
                current.setContent(holder.etContent.getText().toString());
            });
            // Handle editing post image functionality
            holder.editImage.setOnClickListener(v -> {
                posOfEditedImage = revposition;
                selectPhoto(holder.editImage.getContext());
            });
            // Handle deleting post image functionality
            holder.deleteImage.setOnClickListener(v -> {
                holder.ivPic.setImageResource(0);
                current.setIntPic(0);
            });
            // Handle opening comments page
            holder.commentBT.setOnClickListener(v -> {
                Intent intent = new Intent(holder.commentBT.getContext(), CommentsPageActivity.class);
                User user = UserListSrc.getInstance(context).getActiveUser();
                intent.putExtra("CURRENT_POST", revposition);
                holder.commentBT.getContext().startActivity(intent);
            });
            // Show post menu for the author of the post
            String postUser = posts.get(revposition).getAuthor().getUserName();
            String currentUser = UserListSrc.getInstance(context).getActiveUser().getUserName();
            if (postUser.equals(currentUser)) {
                holder.menuBT.setOnClickListener(v -> {
                    showPostMenu(v, revposition, holder);
                });
                holder.menuBT.setVisibility(View.VISIBLE);
            } else {
                holder.menuBT.setVisibility(View.GONE);
            }
            // Handle sharing post
            holder.shareBT.setOnClickListener(v -> {
                showShareMenu(v);
            });
            if (current.isUserLiked(UserListSrc.getInstance(context).getActiveUser())) {
                holder.likeBT.setBackgroundColor(Color.rgb(220, 220, 220));
            } else {
                holder.likeBT.setBackgroundColor(Color.TRANSPARENT);
            }
            // Handle like functionality
            holder.likeBT.setOnClickListener(v -> {
                if (current.isUserLiked(UserListSrc.getInstance(context).getActiveUser())) {
                    current.setLikes(current.getLikes() - 1);
                    holder.likeBT.setBackgroundColor(Color.TRANSPARENT);
                    current.removeLikedUser(UserListSrc.getInstance(context).getActiveUser());
                    notifyDataSetChanged();
                } else {
                    current.setLikes(current.getLikes() + 1);
                    holder.likeBT.setBackgroundColor(Color.rgb(220, 220, 220));
                    current.addLikedUser(UserListSrc.getInstance(context).getActiveUser());
                    notifyDataSetChanged();
                }
            });
            // Display like counter
            String likes = "";
            if (current.getLikes() >= 1000) {
                likes = current.getLikes() / 1000 + "K";
            } else {
                likes += current.getLikes();
            }
            holder.likeCounter.setText(likes);
        }
    }

    // Show post menu for edit/delete options
    private void showPostMenu(View v, int position, PostViewHolder holder) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.getMenuInflater().inflate(R.menu.edit_delete_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.deleteItem) {
                    // Delete the post
                    posts.remove(position);
                    notifyDataSetChanged();
                    return true;
                } else if (item.getItemId() == R.id.editItem) {
                    // Edit the post
                    holder.tvContent.setHeight(0);
                    holder.etContent.setText(holder.tvContent.getText());
                    holder.etContent.setVisibility(View.VISIBLE);
                    holder.editImage.setVisibility(View.VISIBLE);
                    holder.deleteImage.setVisibility(View.VISIBLE);
                    holder.makePostChangeBT.setVisibility(View.VISIBLE);
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }
    // Show share menu
    private void showShareMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.getMenuInflater().inflate(R.menu.share_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        popupMenu.show();
    }

    // Set posts data
    public void setPosts(List<Post> s) {
        posts = s;
        notifyDataSetChanged();
    }
    // Return the number of posts
    @Override
    public int getItemCount() {
        if (posts != null)
            return posts.size();
        else return 0;
    }
    // Get posts
    public List<Post> getPosts() {
        return posts;
    }

    // Method to select photo from gallery or capture from camera
    private void selectPhoto(Context context) {
        // Intent to pick an image from the gallery
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Intent to capture a photo from the camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Create a chooser intent to present the user with options
        Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});

        // Start the activity based on the user's choice
        ((Activity) context).startActivityForResult(chooserIntent, REQUEST_IMAGE_CAPTURE);
    }
}
