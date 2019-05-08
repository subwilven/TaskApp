package com.islam.noteapptask.utils.other;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.islam.noteapptask.data.TasksRepository;
import com.islam.noteapptask.ui.dialog.add_task.AddTaskViewModel;
import com.islam.noteapptask.ui.task_details.TaskDetailsViewModel;
import com.islam.noteapptask.ui.tasks_list.TasksListViewModel;


public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final TasksRepository mTaskRepository;

    public ViewModelFactory(TasksRepository articleRepository) {
        this.mTaskRepository = articleRepository; }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskDetailsViewModel.class)) {
            //noinspection unchecked
            return (T) new TaskDetailsViewModel(mTaskRepository);
        } else if (modelClass.isAssignableFrom(TasksListViewModel.class)) {
            //noinspection unchecked
            return (T) new TasksListViewModel(mTaskRepository);
        } else if (modelClass.isAssignableFrom(AddTaskViewModel.class)) {
            //noinspection unchecked
            return (T) new AddTaskViewModel(mTaskRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}