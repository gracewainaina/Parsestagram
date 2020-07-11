package com.example.parsestagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.parsestagram.fragments.ProfileFragment;


public class PostDetailsActivity extends AppCompatActivity {

    private TextView tvUsername;
    private ImageView ivImage;
    private TextView tvDescription;
    private TextView tvTimestamp;
    private ImageView ivProfileImage;
    private String profileImageUrl;

    private ImageView ivLike;
    private ImageView ivComment;
    private ImageView ivDM;
    private ImageView ivSaveactive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        tvUsername = findViewById(R.id.tvUsername);
        ivImage = findViewById(R.id.ivImage);
        tvDescription = findViewById(R.id.tvDescription);
        tvTimestamp = findViewById(R.id.tvTimestamp);
        ivProfileImage = findViewById(R.id.ivProfileImage);

        ivLike = findViewById(R.id.ivLike);
        ivComment = findViewById(R.id.ivComment);
        ivDM = findViewById(R.id.ivDM);
        ivSaveactive = findViewById(R.id.ivSaveactive);

        tvUsername.setText(getIntent().getStringExtra("username"));
        tvDescription.setText(getIntent().getStringExtra("description"));
        tvTimestamp.setText(getIntent().getStringExtra("timestamp"));
        profileImageUrl = getIntent().getStringExtra("profileimageurl");
        Glide.with(PostDetailsActivity.this).load(getIntent().getStringExtra("imageurl")).into(ivImage);
        Glide.with(PostDetailsActivity.this).load(profileImageUrl).circleCrop().into(ivProfileImage);

        tvUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PostDetailsActivity.this, ProfileActivity.class);
                i.putExtra("username", tvUsername.getText());
                i.putExtra("profileimageurl", profileImageUrl);
                startActivity(i);
            }
        });
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PostDetailsActivity.this, ProfileActivity.class);
                i.putExtra("username", tvUsername.getText());
                i.putExtra("profileimageurl", profileImageUrl);
                startActivity(i);
            }
        });
    }
}