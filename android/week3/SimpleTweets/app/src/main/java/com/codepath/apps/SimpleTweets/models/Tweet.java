package com.codepath.apps.SimpleTweets.models;

import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
     {
         "coordinates": null,
         "truncated": false,
         "created_at": "Tue Aug 28 21:16:23 +0000 2012",
         "favorited": false,
         "id_str": "240558470661799936",
         "in_reply_to_user_id_str": null,
         "entities": {
             "urls": [

             ],
             "hashtags": [

             ],
             "user_mentions": [

             ]
         },
         "text": "just another test",
         "contributors": null,
         "id": 240558470661799936,
         "retweet_count": 0,
         "in_reply_to_status_id_str": null,
         "geo": null,
         "retweeted": false,
         "in_reply_to_user_id": null,
         "place": null,
         "source": "OAuth Dancer Reborn",
         "user": {
             "name": "OAuth Dancer",
             "profile_sidebar_fill_color": "DDEEF6",
             "profile_background_tile": true,
             "profile_sidebar_border_color": "C0DEED",
             "profile_image_url": "http://a0.twimg.com/profile_images/730275945/oauth-dancer_normal.jpg",
             "created_at": "Wed Mar 03 19:37:35 +0000 2010",
             "location": "San Francisco, CA",
             "follow_request_sent": false,
             "id_str": "119476949",
             "is_translator": false,
             "profile_link_color": "0084B4",
             "entities": {
                 "url": {
                     "urls": [{
                         "expanded_url": null,
                         "url": "http://bit.ly/oauth-dancer",
                         "indices": [
                             0,
                             26
                         ],
                        "display_url": null
                     }]
                 },
                 "description": null
             },
             "default_profile": false,
             "url": "http://bit.ly/oauth-dancer",
             "contributors_enabled": false,
             "favourites_count": 7,
             "utc_offset": null,
             "profile_image_url_https": "https://si0.twimg.com/profile_images/730275945/oauth-dancer_normal.jpg",
             "id": 119476949,
             "listed_count": 1,
             "profile_use_background_image": true,
             "profile_text_color": "333333",
             "followers_count": 28,
             "lang": "en",
             "protected": false,
             "geo_enabled": true,
             "notifications": false,
             "description": "",
             "profile_background_color": "C0DEED",
             "verified": false,
             "time_zone": null,
             "profile_background_image_url_https": "https://si0.twimg.com/profile_background_images/80151733/oauth-dance.png",
             "statuses_count": 166,
             "profile_background_image_url": "http://a0.twimg.com/profile_background_images/80151733/oauth-dance.png",
             "default_profile_image": false,
             "friends_count": 14,
             "following": false,
             "show_all_inline_media": false,
             "screen_name": "oauth_dancer"
         },
         "in_reply_to_screen_name": null,
         "in_reply_to_status_id": null
     }
 */

public class Tweet {
    private String createdAt;
    private String relativeTime;
    private boolean favorited;
    private String text;
    private long id;
    private String source;
    private User user;
    private String inReplyToScreenName;
    private String inReplyToStatusId;
    private boolean retweeted;
    private int retweetCount;

    public Tweet() {
    }

    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = null;
        try {
            tweet = new Tweet();
            String createdDateString = jsonObject.getString("created_at");
            String twitterTime = getRelativeTimeAgo(createdDateString);


            Boolean favorited = jsonObject.getBoolean("favorited");
            long id = jsonObject.getLong("id");
            String text = jsonObject.getString("text");
            String source = jsonObject.getString("source");
            String inReplyToScreenName = jsonObject.getString("in_reply_to_screen_name");
            String inReplyToStatusId = jsonObject.getString("in_reply_to_status_id");
            boolean retweeted = jsonObject.getBoolean("retweeted");
            User user = User.fromJSON(jsonObject.getJSONObject("user"));
            int retweetedCount = jsonObject.getInt("retweet_count");

            tweet.favorited = favorited;
            tweet.id = id;
            tweet.text = text;
            tweet.source = source;
            tweet.inReplyToScreenName = inReplyToScreenName;
            tweet.inReplyToStatusId = inReplyToStatusId;
            tweet.retweetCount = retweetedCount;
            tweet.user = user;
            tweet.createdAt = createdDateString;
            tweet.relativeTime = twitterTime;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if(tweet != null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }

        return tweets;
    }
    public String getCreatedAt() {
        return createdAt;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public String getText() {
        return text;
    }

    public long getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public User getUser() {
        return user;
    }

    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    public String getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    public boolean isRetweeted() {
        return retweeted;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getRelativeTime() {
        return relativeTime;
    }

    public void setRelativeTime(String relativeTime) {
        this.relativeTime = relativeTime;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setInReplyToScreenName(String inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
    }

    public void setInReplyToStatusId(String inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
    }

    public void setRetweeted(boolean retweeted) {
        this.retweeted = retweeted;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }
}
