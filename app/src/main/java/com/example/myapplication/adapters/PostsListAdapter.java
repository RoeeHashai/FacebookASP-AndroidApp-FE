package com.example.myapplication.adapters;

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
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activities.CommentsPageActivity;
import com.example.myapplication.Activities.ProfileActivity;
import com.example.myapplication.Base64Utils;
import com.example.myapplication.DateConverter;
import com.example.myapplication.MyApplication;
import com.example.myapplication.MyJWTtoken;
import com.example.myapplication.R;
import com.example.myapplication.UserListSrc;
import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.User;
import com.example.myapplication.viewmodels.PostsViewModel;

import java.io.IOException;
import java.util.List;

/**
 * Adapter for displaying a list of posts in a RecyclerView.
 */
public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.PostViewHolder> {

    private PostsViewModel postsViewModel;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    public void setEditedImage(Uri uri) {
        editedImage.setValue(uri);
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
    private MutableLiveData<Uri> editedImage;
    private LifecycleOwner owner;

    public PostsListAdapter(Context context, PostsViewModel postsViewModel, LifecycleOwner owner) {

        this.mInfalter = LayoutInflater.from(context);
        this.postsViewModel = postsViewModel;
        this.owner = owner;
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
        if (posts != null) {
            final Post current = posts.get(position);
            // Set author name
            holder.tvAuthor.setText(current.getAuthor().getName());
            holder.tvAuthor.setOnClickListener(v -> {
                openHisProfile(current.getAuthor().get_id(), v.getContext());
            });
            // Set profile picture
            try {
                holder.ivProfile.setImageURI(Base64Utils.base64ToUri(current.getAuthor().getImage()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            holder.ivProfile.setOnClickListener(v -> {
                openHisProfile(current.getAuthor().get_id(), v.getContext());
            });
            // Set post content
            holder.tvContent.setText(current.getContent());
            // Set post date
            holder.tvDate.setText(DateConverter.fromDBtoDisplay(current.getDate()));
            // Set post image
            if (!current.getImage().isEmpty() && !(current.getImage() == null)) {
                try {
                    holder.ivPic.setImageURI(Base64Utils.base64ToUri(current.getImage()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                holder.ivPic.setImageDrawable(null);
            }
            // Handle editing post functionality
            holder.makePostChangeBT.setOnClickListener(v -> {
                Post editedPost = new Post();
                editedPost.set_id(current.get_id());
                editedPost.setContent(holder.etContent.getText().toString());
                editedPost.setImage(current.getImage());
                editedPost.setDate(current.getDate());
                editedPost.setLikes(current.getLikes());
                editedPost.setAuthor(current.getAuthor());
                editedPost.setCommentsLength(current.getCommentsLength());
                MutableLiveData<Boolean> editOK = new MutableLiveData<>();
                postsViewModel.updatePost(editedPost.get_id(), editedPost, editOK);
                editOK.observe(owner, isOK -> {
                    if(isOK) {
                        holder.tvContent.setHeight(holder.etContent.getHeight());
                        holder.tvContent.setText(holder.etContent.getText());
                        holder.etContent.setVisibility(View.GONE);
                        holder.makePostChangeBT.setVisibility(View.GONE);
                        current.setContent(holder.etContent.getText().toString());
                        postsViewModel.reload();
                    }
                });

            });
            // Show post menu for the author of the post
            String postUser = current.getAuthor().get_id();
            String currentUser = MyJWTtoken.getInstance().getUserDetails().getValue().get_id();
            if (postUser.equals(currentUser)) {
                holder.menuBT.setOnClickListener(v -> {
                    showPostMenu(v, position, holder);
                });
                holder.menuBT.setVisibility(View.VISIBLE);
            } else {
                holder.menuBT.setVisibility(View.GONE);
            }
            // Handle sharing post
            holder.shareBT.setOnClickListener(v -> {
                showShareMenu(v);
            });
            boolean isUserLiked = current.getLikes().contains(MyJWTtoken.getInstance().getUserDetails().getValue().get_id());
            if (isUserLiked) {
                holder.likeBT.setBackgroundColor(Color.rgb(220, 220, 220));
            } else {
                holder.likeBT.setBackgroundColor(Color.TRANSPARENT);
            }
            // Handle like functionality
            holder.likeBT.setOnClickListener(v -> {
                if (isUserLiked) {
                    postsViewModel.unlikePost(current.get_id());
                    current.getLikes().remove(currentUser);
                }
                else {
                    postsViewModel.likePost(current.get_id());
                    current.getLikes().add(currentUser);
                }
                notifyDataSetChanged();
            });
            // Display like counter
            String likes = "";
            if (current.getLikes().size() >= 1000) {
                likes = current.getLikes().size() / 1000 + "K";
            } else {
                likes += current.getLikes().size();
            }
            holder.likeCounter.setText(likes);
            holder.commentBT.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), CommentsPageActivity.class);
                intent.putExtra("POST", current.get_id());
                v.getContext().startActivity(intent);
            });
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
                    postsViewModel.deletePost(posts.get(position).get_id());
                    posts.remove(position);
                    notifyDataSetChanged();
                    return true;
                } else if (item.getItemId() == R.id.editTextItem) {
                    // Edit the post
                    holder.tvContent.setHeight(0);
                    holder.etContent.setText(holder.tvContent.getText());
                    holder.etContent.setVisibility(View.VISIBLE);
                    holder.makePostChangeBT.setVisibility(View.VISIBLE);
                    return true;
                } else if (item.getItemId() == R.id.editImageItem) {
                    editedImage = new MutableLiveData<>();
                    selectPhoto(holder.editImage.getContext());
                    editedImage.observe(owner, uri -> {
                        try {
                            posts.get(position).setImage(Base64Utils.uriToBase64(uri));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        MutableLiveData<Boolean> editOK = new MutableLiveData<>();
                        postsViewModel.updatePost(posts.get(position).get_id(), posts.get(position), editOK);
                        editOK.observe(owner, isOK -> {
                            if(isOK) {
                                postsViewModel.reload();
                                postsViewModel.reloadProfile(MyJWTtoken.getInstance().getUserDetails().getValue().get_id());
                            }
                        });
                    });
                    return true;
                } else if (item.getItemId() == R.id.deleteImageItem) {
                    if (holder.tvContent.getText().toString().isEmpty()) {
                        Toast.makeText(holder.tvContent.getContext(), "You cannot leave a post completely blank", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        editedImage = null;
                        posts.get(position).setImage("");
                        holder.ivPic.setImageDrawable(null);
                        postsViewModel.updatePost(posts.get(position).get_id(), posts.get(position));
                    }
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

    private void openHisProfile(String id, Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra("ID", id);
        context.startActivity(intent);
        ((Activity) context).finish();
    }
}