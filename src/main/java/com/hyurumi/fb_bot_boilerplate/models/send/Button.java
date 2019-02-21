package com.hyurumi.fb_bot_boilerplate.models.send;

import com.google.gson.annotations.SerializedName;
import com.hyurumi.fb_bot_boilerplate.models.common.Action;

/**
 * Created by genki.furumi on 4/15/16.
 */
public class Button {
    private enum Type {
        @SerializedName("postback")
        Postback,
        @SerializedName("web_url")
        WebUrl
    }
    private final Type type;
    private final String title;
    private final Action payload;
    private final String url;
    private final boolean messenger_extensions;

    private Button(Type type, String title, String url, Action action){
        this.type = type;
        this.title = title;
        this.url = url;
        this.payload = action;
        this.messenger_extensions = false;
    }
    
    private Button(Type type, String title, String url, boolean messenger_extensions){
        this.type = type;
        this.title = title;
        this.url = url;
        this.payload = null;
        this.messenger_extensions = messenger_extensions;
    }

    public static Button Url(String title, String url){
        return new Button(Type.WebUrl, title, url, null);
    }

    public static Button Postback(String title, Action action){
        return new Button(Type.Postback, title, null, action);
    }
    
    public static Button Url(String title, String url, boolean messenger_extensions){
        return new Button(Type.WebUrl, title, url, messenger_extensions);
    }
}
