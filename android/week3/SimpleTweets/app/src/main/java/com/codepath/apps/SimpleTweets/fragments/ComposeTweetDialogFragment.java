package com.codepath.apps.SimpleTweets.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.SimpleTweets.R;
import com.codepath.apps.SimpleTweets.models.Tweet;
import com.codepath.apps.SimpleTweets.networks.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ComposeTweetDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComposeTweetDialogFragment extends DialogFragment {
    @BindView(R.id.ivProfilePic)
    ImageView ivProfilePic;

    @BindView(R.id.etStatus)
    EditText etStatus;

    @BindView(R.id.tvMessageCount) TextView tvMessageCount;

    private TwitterClient twitterClient;
    private static final String TAG = ComposeTweetDialogFragment.class.getName();
    private static int MAX_CHARACTERS_TWEET = 140;

    public ComposeTweetDialogFragment() {

    }

    public interface ComposeTweetDialogFragmentListener {
        void onFinishCompose(Tweet tweet);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ComposeTweetDialogFragment.
     */
    public static ComposeTweetDialogFragment newInstance(String profilePicImg) {
        ComposeTweetDialogFragment fragment = new ComposeTweetDialogFragment();
        Bundle args = new Bundle();
        //args.putString("profilePicImage", profilePicImg);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        twitterClient = new TwitterClient(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_compose_tweet_dialog, container, false);
        ButterKnife.bind(this, view);

        /*
        String profilePicImage = savedInstanceState.getString("profilePicImage");
        Glide.with(getContext()).load(profilePicImage).fitCenter()
                .error(R.drawable.ic_blue_twitter)
                .placeholder(R.drawable.ic_blue_twitter)
                .into(ivProfilePic);
        */
        return view;
    }

    @OnClick(R.id.ivClose)
    public void close() {
        dismiss();
    }

    @OnTextChanged(R.id.etStatus)
    public void onStatusChange(){
        int charactersLeft = MAX_CHARACTERS_TWEET - this.etStatus.getText().toString().length();
        tvMessageCount.setText(String.valueOf(charactersLeft));
    }
    @OnClick(R.id.btTweet)
    public void sendTweet() {
        String message = etStatus.getText().toString();
        if(message.length() > MAX_CHARACTERS_TWEET)
        {
            Toast.makeText(getContext(), "Tweets can only be " + MAX_CHARACTERS_TWEET + " characters long.  Please try againt", Toast.LENGTH_SHORT).show();
        } else {
            twitterClient.updateStatus(message, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Toast.makeText(getContext(), "Tweet sent", Toast.LENGTH_SHORT).show();
                    Tweet tweet = Tweet.fromJSON(response);
                    ComposeTweetDialogFragmentListener listener = (ComposeTweetDialogFragmentListener) getActivity();
                    listener.onFinishCompose(tweet);
                    // Close the dialog and return back to the parent activity
                    dismiss();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    Log.e(TAG, "Twitter UpdateStatus Failed!");
                    Toast.makeText(getContext(), "Twitter UpdateStatus Failed!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

