package com.codepath.apps.SimpleTweets.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
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
      }
*/
public class User {
    public String name;
    public String profileImageUrl;
    public String location;
    public long id;
    public String url;
    public ArrayList<Entity> entities;
    public int favoriteCount;
    public String description;
    public String profileBackgroundImgUrl;
    public int statusesCount;
    public int friendsCount;
    public boolean following;
    public String screenName;

    public User() {

    }
    public User(String name, String profileImageUrl, String location, long id, String url, ArrayList<Entity> entities, int favoriteCount, String description, String profileBackgroundImgUrl, int statusesCount, int friendsCount, boolean following, String screenName) {
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.location = location;
        this.id = id;
        this.url = url;
        this.entities = entities;
        this.favoriteCount = favoriteCount;
        this.description = description;
        this.profileBackgroundImgUrl = profileBackgroundImgUrl;
        this.statusesCount = statusesCount;
        this.friendsCount = friendsCount;
        this.following = following;
        this.screenName = screenName;
    }

    public static User fromJSON(JSONObject jsonObject) {
        User user = null;
        try {
            String name = jsonObject.getString("name");
            String profileImageUrl = jsonObject.getString("profile_image_url");
            String location = jsonObject.getString("location");
            long id = jsonObject.getLong("id");
            String url = jsonObject.getString("url");
            int favoriteCount = jsonObject.getInt("favourites_count");
            String desc = jsonObject.getString("description");
            boolean following = jsonObject.getBoolean("following");
            String screenName = jsonObject.getString("screen_name");
            user = new User();
            user.name = name;
            user.profileImageUrl = profileImageUrl;
            user.location = location;
            user.id = id;
            user.url = url;
            user.favoriteCount = favoriteCount;
            user.description = desc;
            user.following = following;
            user.screenName = "@" + screenName;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getLocation() {
        return location;
    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public String getDescription() {
        return description;
    }

    public String getProfileBackgroundImgUrl() {
        return profileBackgroundImgUrl;
    }

    public int getStatusesCount() {
        return statusesCount;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public boolean isFollowing() {
        return following;
    }

    public String getScreenName() {
        return screenName;
    }
}
