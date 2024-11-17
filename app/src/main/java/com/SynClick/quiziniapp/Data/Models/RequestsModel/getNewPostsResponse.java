
package com.SynClick.quiziniapp.Data.Models.RequestsModel;

import com.SynClick.quiziniapp.Data.Models.Post;

import java.util.List;

public class getNewPostsResponse {
    String message;
    List<Post> posts;

    public getNewPostsResponse(String message, List<Post> posts) {
        this.message = message;
        this.posts = posts;
    }
    public getNewPostsResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}

