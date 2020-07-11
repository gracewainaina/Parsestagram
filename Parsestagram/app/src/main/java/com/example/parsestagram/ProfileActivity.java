package com.example.parsestagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements PostsAdapter.adapterListener {

    public static final String TAG = "ProfileActivity";
    private String profileImageUrl;
    private TextView tvUsername;
    private ImageView ivProfileImage;
    private RecyclerView rvPosts;
    protected List<Post> allPosts;
    protected PostsAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvUsername = findViewById(R.id.tvUsername);
        ivProfileImage = findViewById(R.id.ivProfileImage);

        tvUsername.setText(getIntent().getStringExtra("username"));
        profileImageUrl = getIntent().getStringExtra("profileimageurl");
        Glide.with(ProfileActivity.this).load(profileImageUrl).circleCrop().into(ivProfileImage);
        // using recycler view in a fragment
        // 0. create a view for on row in the list
        rvPosts = findViewById(R.id.rvPosts);
        // 1. create adapter
        // 2. create data source
        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(this, allPosts, this);
        // 3. set the adapter on the recycler view
        rvPosts.setAdapter(adapter);
        // 4. set the layout manager on the recycler view
        // create a linear layout manager to pass into the endless recycler view scroll listener
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        // rvPosts.setLayoutManager(linearLayoutManager);

        // First param is number of columns and second param is orientation i.e Vertical or Horizontal
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        // Attach the layout manager to the recycler view
        rvPosts.setLayoutManager(gridLayoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                try {
                    queryPosts(page);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
        // Adds the scroll listener to RecyclerView
        rvPosts.addOnScrollListener(scrollListener);
        try {
            queryPosts(0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    protected ParseUser queryUser(String username_selected) throws ParseException {
        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        query.whereEqualTo("username", username_selected);
        return query.find().get(0);
    }

    protected void queryPosts(int page) throws ParseException {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, queryUser(tvUsername.getText().toString()));
        query.setLimit(5);
        query.setSkip(5 * page);
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue retrieving posts", e);
                    return;
                }
                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getCurrentFragment() {
        return 1;
    }

}