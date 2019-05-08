package com.islam.noteapptask.ui.task_details;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.islam.noteapptask.data.TasksRepository;
import com.islam.noteapptask.pojo.Comment;
import com.islam.noteapptask.pojo.Task;

import java.util.List;

public class TaskDetailsViewModel extends ViewModel {

    private String username;
    private TasksRepository mRepository;
    private MutableLiveData<Task> task = new MutableLiveData<>();
    private MutableLiveData<Comment> onCommentAdded = new MutableLiveData<>();

    public TaskDetailsViewModel(TasksRepository mRepository) {
        this.mRepository = mRepository;
    }

    public void init(String username, Task task) {
        this.username = username;
        this.task.setValue(task);
    }

    public void deleteTask() {
        mRepository.deleteTask(username, task.getValue());
    }

    public void changeTaskPriority(int priority) {
        Task task = this.task.getValue();
        task.setPriority(priority);
        this.task.setValue(task);
        mRepository.updateTaskDetails(username, task);
    }

    public void changeTaskStatus() {
        Task task = this.task.getValue();
        task.setDone(!task.isDone());
        this.task.setValue(task);
        mRepository.updateTaskDetails(username, task);
    }

    public void addComment(String content) {
        if (!content.trim().isEmpty()) {
            Comment newComment = new Comment(content, System.currentTimeMillis());
            onCommentAdded.setValue(newComment);

            List<Comment> comments = task.getValue().getComments();
            int commentId = 0;
            if (comments != null)
                commentId = comments.size() - 1;

            mRepository.addComment(username, task.getValue(), newComment, String.valueOf(commentId));
        }
    }

    public LiveData<Task> getTask() {
        return task;
    }

    public LiveData<Comment> getOnCommentAdded() {
        return onCommentAdded;
    }

}
