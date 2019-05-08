package com.islam.noteapptask.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Task implements Serializable {

    private String id;
    private boolean isDone;
    private String title;
    private long createdTime;
    private int priority;
    private List<Comment> comments;

    public Task() {
        comments = new ArrayList<>();
    }


    public Task(String title) {
        this.title = title;
        createdTime = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return getCreatedTime() == task.getCreatedTime() &&
                Objects.equals(getId(), task.getId()) &&
                Objects.equals(getTitle(), task.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), isDone(), getTitle(), getCreatedTime(), getPriority());
    }
}
