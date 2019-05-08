package com.islam.noteapptask.ui.tasks_list;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.islam.noteapptask.R;
import com.islam.noteapptask.data.TasksRepository;
import com.islam.noteapptask.pojo.Task;
import com.islam.noteapptask.pojo.TaskResponse;
import com.islam.noteapptask.ui.task_details.TaskDetailsViewModel;
import com.islam.noteapptask.utils.other.SingleLiveEvent;

public class TasksListViewModel extends ViewModel {

    private TasksRepository mRepository;
    private String username;
    private MutableLiveData<Boolean> onlyUnDoneTasks = new MutableLiveData<>();

    private LiveData<TaskResponse> onTaskAdded;

    private SingleLiveEvent<Task> onTaskClicked = new SingleLiveEvent<>();

    public TasksListViewModel(TasksRepository mRepository) {
        this.mRepository = mRepository;
        onlyUnDoneTasks.setValue(false);
    }

    public void loadTasks(final String username) {
        this.username = username;
        onTaskAdded = Transformations.switchMap(onlyUnDoneTasks, new Function<Boolean, LiveData<TaskResponse>>() {
            @Override
            public LiveData<TaskResponse> apply(Boolean input) {
                return mRepository.fetchUserTasks(username, input);
            }
        });
    }

    public LiveData<TaskResponse> getOnTask() {
        return onTaskAdded;
    }

    public void changeTaskPriority(Task task, int priority) {
        task.setPriority(priority);
        mRepository.updateTaskDetails(username, task);
    }

    public void changeTaskStatus(Task task) {
        task.setDone(!task.isDone());
        mRepository.updateTaskDetails(username, task);
    }

    public void launchTaskDetailsFragment(Task task) {
        onTaskClicked.setValue(task);
    }

    public LiveData<Task> getOnTaskClicked() {
        return onTaskClicked;
    }

    public void changeLoadingStatus() {
        onlyUnDoneTasks.setValue(!onlyUnDoneTasks.getValue());
    }

    public LiveData<Boolean> getOnlyUnDoneTasks(){
        return onlyUnDoneTasks;
    }
}
