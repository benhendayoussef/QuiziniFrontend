package com.SynClick.quiziniapp.Data.Models;

public class PostVote {
    boolean downvoted,upvoted;
    int id,seeNumber,downVoteNumber,upVoteNumber;

    public PostVote(boolean downvoted, boolean upvoted, int id, int seeNumber, int downVoteNumber, int upVoteNumber) {
        this.downvoted = downvoted;
        this.upvoted = upvoted;
        this.id = id;
        this.seeNumber = seeNumber;
        this.downVoteNumber = downVoteNumber;
        this.upVoteNumber = upVoteNumber;
    }

    public PostVote() {
    }

    public boolean isDownvoted() {
        return downvoted;
    }

    public void setDownvoted(boolean downvoted) {
        this.downvoted = downvoted;
    }

    public boolean isUpvoted() {
        return upvoted;
    }

    public void setUpvoted(boolean upvoted) {
        this.upvoted = upvoted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeeNumber() {
        return seeNumber;
    }

    public void setSeeNumber(int seeNumber) {
        this.seeNumber = seeNumber;
    }

    public int getDownVoteNumber() {
        return downVoteNumber;
    }

    public void setDownVoteNumber(int downVoteNumber) {
        this.downVoteNumber = downVoteNumber;
    }

    public int getUpVoteNumber() {
        return upVoteNumber;
    }

    public void setUpVoteNumber(int upVoteNumber) {
        this.upVoteNumber = upVoteNumber;
    }
}
