package com.example.parsestagram;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;

//import org.parceler.Parcel;

@ParseClassName("Post")
//@Parcel
public class Post extends ParseObject {
    // Getters abd setter will be based on the keys created in post- heroku
    // description, image and user

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_COMMENTS = "comments";

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser parseUser) {
        put(KEY_USER, parseUser);
    }

    public String getKeyCreatedAt() {
        return getCreatedAt().toString();
    }

    public ArrayList<Comment> getComments() {
        return (ArrayList<Comment>) get(KEY_COMMENTS);
    }

    public void setComments(ArrayList<Comment> comments) {
        put(KEY_COMMENTS, comments);
    }
}
