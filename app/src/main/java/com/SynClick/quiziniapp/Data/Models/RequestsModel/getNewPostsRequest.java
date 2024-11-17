package com.SynClick.quiziniapp.Data.Models.RequestsModel;

import java.util.List;

public class getNewPostsRequest {
    List<Integer> cachedPostsId;
    int numberOfPosts;

    public getNewPostsRequest(List<Integer> cachedPostsIds, int numberOfPosts) {
        this.cachedPostsId = cachedPostsIds;
        this.numberOfPosts = numberOfPosts;
    }

    public getNewPostsRequest() {
    }

    public List<Integer> getCachedPostsIds() {
        return cachedPostsId;
    }

    public void setCachedPostsIds(List<Integer> cachedPostsIds) {
        this.cachedPostsId = cachedPostsIds;
    }

    public int getNumberOfPosts() {
        return numberOfPosts;
    }

    public void setNumberOfPosts(int numberOfPosts) {
        this.numberOfPosts = numberOfPosts;
    }
}
