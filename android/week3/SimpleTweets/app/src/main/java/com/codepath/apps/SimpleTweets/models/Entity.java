package com.codepath.apps.SimpleTweets.models;

import java.util.ArrayList;
import java.util.Map;

/**
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
    }
 */
public class Entity {
    private final String description;
    private final ArrayList<Url> urls;

    public Entity(String description, ArrayList<Url> urls) {
        this.description = description;
        this.urls = new ArrayList<>(urls);
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Url> getUrls() {
        return urls;
    }
}
