package com.example.parsestagram;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Comment")
public class Comment extends ParseObject {
    public static final String KEY_USER = "user";
    public static final String KEY_POST = "post";
    public static final String KEY_BODY = "body";

    public String getBody() {
        return getString(KEY_POST);
    }

    public void setBody(String body) {
        put(KEY_BODY, body);
    }

    public ParseObject getPost() {
        return getParseObject(KEY_POST);
    }

    public void setPost(Post post) {
        put(KEY_POST, post);
    }

    public ParseUser getUser() {
        try {
            return fetchIfNeeded().getParseUser(KEY_USER);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setUser(ParseUser parseUser) {
        put(KEY_USER, parseUser);
    }

}
