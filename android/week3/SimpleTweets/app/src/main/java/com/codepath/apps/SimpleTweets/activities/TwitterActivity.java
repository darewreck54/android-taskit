package com.codepath.apps.SimpleTweets.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.SimpleTweets.R;
import com.codepath.apps.SimpleTweets.adapters.TweetAdapter;
import com.codepath.apps.SimpleTweets.fragments.ComposeTweetDialogFragment;
import com.codepath.apps.SimpleTweets.interfaces.EndlessRecyclerViewScrollListener;
import com.codepath.apps.SimpleTweets.models.Tweet;
import com.codepath.apps.SimpleTweets.networks.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class TwitterActivity extends AppCompatActivity implements ComposeTweetDialogFragment.ComposeTweetDialogFragmentListener {
    @BindView(R.id.rvResults) RecyclerView rvResults;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshLayout;

    private EndlessRecyclerViewScrollListener scrollListener;
    private LinearLayoutManager layoutManager;
    private static final String TAG = TwitterActivity.class.getName();
    private TweetAdapter adapter;
    private List<Tweet> tweets;
    private TwitterClient twitterClient;
    private int COUNT_MAX = 25;
    private long greatestTweetId = Long.MIN_VALUE;
    private long lowestTweetId = Long.MAX_VALUE;

    private HashMap<Long, Tweet> set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.ic_twitter_snow);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tweets = new ArrayList<>();
        adapter = new TweetAdapter(this, tweets);
        rvResults.setAdapter(adapter);
        twitterClient = new TwitterClient(this);

        populateTimeline(null,null, true,COUNT_MAX);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        rvResults.setLayoutManager(layoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
               // populateTimeline(null,lowestTweetId-1, false,COUNT_MAX);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvResults.addOnScrollListener(scrollListener);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               populateTimeline(null, null, true,COUNT_MAX);
               layoutManager.scrollToPosition(tweets.size()-1);
            }
        });
        set = new HashMap<>();

        // Configure the refreshing colors
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    private void populateTimeline(Long sinceId, Long maxId, final boolean isRefresh, long count) {
        twitterClient.getTimeline(sinceId, maxId,count, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                if(isRefresh) {
                    tweets.clear();
                    adapter.clear();
                }
                ArrayList<Tweet> tweetList = Tweet.fromJSONArray(response);
                for (Tweet tweet : tweetList) {

                    tweets.add(0,tweet);
                    adapter.notifyItemChanged(0);

                    greatestTweetId = Math.max(greatestTweetId, tweet.getId());
                    lowestTweetId = Math.min(lowestTweetId, tweet.getId());

                    if(set.containsKey(tweet.getId())) {
                        Log.e(TAG, "Duplicate tweet received." + tweet.getInReplyToScreenName());
                    }
                    set.put(tweet.getId(), tweet);
                }

                if(isRefresh) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e(TAG, "Hit Twitter API limit.");
                Toast.makeText(getApplicationContext(), "Hit Twitter API limit. Please wait a couple minutes and try agian.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /* Using floating bar so can disable the tool bar menu actions
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_compose: {
              //  showComposeDialog();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    */

    @OnClick(R.id.floatingActionBar)
    public void showComposeDialog() {
        FragmentManager fm = getSupportFragmentManager();
        ComposeTweetDialogFragment fragment = ComposeTweetDialogFragment.newInstance(null);
        fragment.show(fm, "fragment_compose");
    }

    @Override
    public void onFinishCompose(Tweet tweet) {
        tweets.add(tweet);
        adapter.notifyItemChanged(tweets.size()-1);
        layoutManager.scrollToPosition(tweets.size()-1);
    }

}
