package com.SynClick.quiziniapp.Data.Models.RequestsModel;

import com.SynClick.quiziniapp.Data.Models.PostVote;

public class VotePostByIdResponse {
    private String message;
    private PostVote post;

    public VotePostByIdResponse(String message, PostVote post) {
        this.message = message;
        this.post = post;
    }

    public VotePostByIdResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PostVote getPost() {
        return post;
    }

    public void setPost(PostVote post) {
        this.post = post;
    }
}
