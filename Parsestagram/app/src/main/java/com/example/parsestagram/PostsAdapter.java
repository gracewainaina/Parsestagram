package com.example.parsestagram;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;
    private adapterListener listener;

    public PostsAdapter(Context context, List<Post> posts, adapterListener listener) {
        this.context = context;
        this.posts = posts;
        this.listener = listener;
    }

    public interface adapterListener {
        public int getCurrentFragment();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvUsername;
        private ImageView ivProfileImage;
        private ImageView ivImage;
        private TextView tvDescription;
        private TextView tvTimestamp;
        private ImageView ivLike;
        private ImageView ivComment;
        private ImageView ivDM;
        private ImageView ivSaveactive;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            ivLike = itemView.findViewById(R.id.ivLike);
            ivComment = itemView.findViewById(R.id.ivComment);
            ivDM = itemView.findViewById(R.id.ivDM);
            ivSaveactive = itemView.findViewById(R.id.ivSaveactive);

            itemView.setOnClickListener(this);
            // This means we are in the profile fragment
            if (listener.getCurrentFragment() == 1) {
                ivLike.setVisibility(View.GONE);
                ivComment.setVisibility(View.GONE);
                ivDM.setVisibility(View.GONE);
                ivSaveactive.setVisibility(View.GONE);
                tvDescription.setVisibility(View.GONE);
                tvTimestamp.setVisibility(View.GONE);
                ivProfileImage.setVisibility(View.GONE);
                tvUsername.setVisibility(View.GONE);

            }

            if (listener.getCurrentFragment() == 0) {
                ivProfileImage.setVisibility(View.VISIBLE);
                tvUsername.setVisibility(View.VISIBLE);
            }
        }

        public void bind(Post post) {
            // Bind the post data to the view element
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            tvTimestamp.setText(post.getKeyCreatedAt());
            ParseFile image = post.getImage();
            ParseFile profileimage = post.getUser().getParseFile("profilephoto");
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }
            if (profileimage != null) {
                Glide.with(context).load(profileimage.getUrl()).circleCrop().into(ivProfileImage);
            }


        }

        public void onClick(View view) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the post at the position, this won't work if the class is static
                Post post = posts.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, PostDetailsActivity.class);
                // serialize the post using parceler, use its short name as a key
                intent.putExtra("username", post.getUser().getUsername());
                intent.putExtra("description", post.getDescription());
                intent.putExtra("imageurl", post.getImage().getUrl());
                intent.putExtra("timestamp", post.getKeyCreatedAt());
                intent.putExtra("profileimageurl", post.getUser().getParseFile("profilephoto").getUrl());
                // show the activity
                context.startActivity(intent);
                Toast.makeText(context, "Post clicked", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
