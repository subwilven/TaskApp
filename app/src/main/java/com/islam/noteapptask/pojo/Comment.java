package com.islam.noteapptask.pojo;

public class Comment {

    private String content;
    private long createdTime;

    public Comment() {
    }

    public Comment(String content, long createdTime) {
        this.content = content;
        this.createdTime = createdTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }
}
