package com.SynClick.quiziniapp.Data.Models;

import java.util.List;

public class Post {
    int id;
    String title;
    String content;
    String author;
    String date;
    List<String> tags;
    List<String> links;
    String type;
    List<String> mediaUrls;
    String updatedAt;
    String createdAt;
    int upVoteNumber;
    int downVoteNumber;
    int seenNumber;
    boolean isActive;
    List<String> actions;
    boolean upVoted,downVoted;

    public Post(int id, String title, String content, String author, String date, List<String> tags, List<String> links, String type, List<String> mediaUrls, String updatedAt, String createdAt, int upVoteNumber, int downVoteNumber, int seenNumber, boolean isActive, List<String> actions, boolean upVoted, boolean downVoted) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.date = date;
        this.tags = tags;
        this.links = links;
        this.type = type;
        this.mediaUrls = mediaUrls;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.upVoteNumber = upVoteNumber;
        this.downVoteNumber = downVoteNumber;
        this.seenNumber = seenNumber;
        this.isActive = isActive;
        this.actions = actions;
        this.upVoted = upVoted;
        this.downVoted = downVoted;
    }

    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getMediaUrls() {
        return mediaUrls;
    }

    public void setMediaUrls(List<String> mediaUrls) {
        this.mediaUrls = mediaUrls;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getUpVoteNumber() {
        return upVoteNumber;
    }

    public void setUpVoteNumber(int upVoteNumber) {
        this.upVoteNumber = upVoteNumber;
    }

    public int getDownVoteNumber() {
        return downVoteNumber;
    }

    public void setDownVoteNumber(int downVoteNumber) {
        this.downVoteNumber = downVoteNumber;
    }

    public int getSeenNumber() {
        return seenNumber;
    }

    public void setSeenNumber(int seenNumber) {
        this.seenNumber = seenNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    public boolean isUpVoted() {
        return upVoted;
    }

    public void setUpVoted(boolean upVoted) {
        this.upVoted = upVoted;
    }

    public boolean isDownVoted() {
        return downVoted;
    }

    public void setDownVoted(boolean downVoted) {
        this.downVoted = downVoted;
    }
}
