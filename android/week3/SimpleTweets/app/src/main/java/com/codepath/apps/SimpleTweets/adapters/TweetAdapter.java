package com.codepath.apps.SimpleTweets.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.SimpleTweets.R;
import com.codepath.apps.SimpleTweets.models.Tweet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.codepath.apps.SimpleTweets.R;
/**
 * Created by darewreck_PC on 3/25/2017.
 */

public class TweetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Tweet> mTweets;
    private Context mContext;
    private LayoutInflater inflater;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvUserName) TextView tvUserName;
        @BindView(R.id.tvUserScreenName) TextView tvUserScreenName;
        @BindView(R.id.ivProfileIcon) ImageView ivProfileIcon;
        @BindView(R.id.tvMessage) TextView tvMessage;
        @BindView(R.id.tvRelativeTime) TextView tvRelativeTime;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public TweetAdapter(Context context, List<Tweet> tweets) {
        mTweets = tweets;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_tweet, parent, false);
        RecyclerView.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.tvMessage.setText(tweet.getText());
        viewHolder.tvUserName.setText(tweet.getUser().getName());
        viewHolder.tvUserScreenName.setText(tweet.getUser().getScreenName());
        viewHolder.tvRelativeTime.setText(tweet.getRelativeTime());
        Glide.with(getContext()).load(tweet.getUser().profileImageUrl).fitCenter()
                .error(R.drawable.ic_blue_twitter)
                .placeholder(R.drawable.ic_blue_twitter)
                .into(viewHolder.ivProfileIcon);

    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }
    private Context getContext() {
        return mContext;
    }

    // Clean all elements of the recycler
    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }

}
