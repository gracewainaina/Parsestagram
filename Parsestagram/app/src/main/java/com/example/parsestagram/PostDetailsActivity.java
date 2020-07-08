package com.example.parsestagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;



public class PostDetailsActivity extends AppCompatActivity {

    private TextView tvUsername;
    private ImageView ivImage;
    private TextView tvDescription;
    private TextView tvTimestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        tvUsername = findViewById(R.id.tvUsername);
        ivImage = findViewById(R.id.ivImage);
        tvDescription = findViewById(R.id.tvDescription);
        tvTimestamp = findViewById(R.id.tvTimestamp);

        tvUsername.setText(getIntent().getStringExtra("username"));
        tvDescription.setText(getIntent().getStringExtra("description"));
        tvTimestamp.setText(getIntent().getStringExtra("timestamp"));
        Glide.with(PostDetailsActivity.this).load(getIntent().getStringExtra("imageurl")).into(ivImage);
    }
}