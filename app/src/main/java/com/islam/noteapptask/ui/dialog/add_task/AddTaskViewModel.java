package com.islam.noteapptask.ui.dialog.add_task;

import android.arch.lifecycle.ViewModel;

import com.islam.noteapptask.data.TasksRepository;
import com.islam.noteapptask.pojo.Task;

public class AddTaskViewModel extends ViewModel {

    private TasksRepository mRepository;

    public AddTaskViewModel(TasksRepository mRepository) {
        this.mRepository = mRepository;
    }

    public String createNewTask(String username, Task task) {
       return mRepository.createNewTask(username, task);
    }
}
