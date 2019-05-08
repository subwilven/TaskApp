package com.islam.noteapptask.pojo;

public class TaskResponse {

    public static final int ITEM_ADDED =0;
    public static final int ITEM_CHANGED =1;
    public static final int ITEM_DELETED =2;

    private Task task;
    private int action;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    private  TaskResponse(Task task, int action) {
        this.task = task;
        this.action = action;
    }


    public static TaskResponse addedTaskInstance(Task task){
        return new TaskResponse(task,ITEM_ADDED);
    }
    public static TaskResponse changedTaskInstance(Task task){
        return new TaskResponse(task,ITEM_CHANGED);
    }
    public static TaskResponse deletedTaskInstance(Task task){
        return new TaskResponse(task,ITEM_DELETED);
    }
}
