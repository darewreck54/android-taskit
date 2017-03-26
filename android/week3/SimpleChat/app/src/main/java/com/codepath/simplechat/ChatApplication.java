package com.codepath.simplechat;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.interceptors.ParseLogInterceptor;

/**
 * Created by darewreck_PC on 3/22/2017.
 */

public class ChatApplication extends Application {
    private static final String APPLICATION_ID = "93bd0b59a90d46b1999e484431b83f41";
    private static final String SERVER_URL = "https://simplechatclient.herokuapp.com/parse/";

    @Override
    public void onCreate() {

        super.onCreate();

        // Register your parse models here
        ParseObject.registerSubclass(Message.class);

        // set applicationId and server based on the values in the Heroku settings.
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(this.APPLICATION_ID) // should correspond to APP_ID env variable
                .addNetworkInterceptor(new ParseLogInterceptor())
                .server(this.SERVER_URL).build());

    }

}