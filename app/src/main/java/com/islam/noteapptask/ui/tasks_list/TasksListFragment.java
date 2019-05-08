package com.islam.noteapptask.ui.tasks_list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.islam.noteapptask.MyApplication;
import com.islam.noteapptask.R;
import com.islam.noteapptask.pojo.Task;
import com.islam.noteapptask.pojo.TaskResponse;
import com.islam.noteapptask.ui.dialog.add_task.AddTaskDialog;
import com.islam.noteapptask.ui.task_details.TaskDetailsFragment;
import com.islam.noteapptask.utils.ActivityUtils;
import com.islam.noteapptask.utils.PreferenceUtils;
import com.islam.noteapptask.utils.other.ViewModelFactory;

import static com.islam.noteapptask.pojo.TaskResponse.ITEM_ADDED;
import static com.islam.noteapptask.pojo.TaskResponse.ITEM_CHANGED;
import static com.islam.noteapptask.pojo.TaskResponse.ITEM_DELETED;

public class TasksListFragment extends Fragment implements View.OnClickListener {

    private TasksListAdapter mAdapter;
    private TasksListViewModel mViewModel;
    private MenuItem filterMenuItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);

        ViewModelFactory viewModelFactory = MyApplication.getInstance().getRepositoryComponent().getViewModelFactory();
        mViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(TasksListViewModel.class);

        String currentUserName = PreferenceUtils.getUserName(getContext());
        mViewModel.loadTasks(currentUserName);
        ((TextView) getActivity().findViewById(R.id.toolbar_title)).setText(currentUserName + "'s Tasks");


        initViews(rootView);

        setUpObservers();

        setHasOptionsMenu(true);
        return rootView;
    }

    private void initViews(View rootView) {

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        mAdapter = new TasksListAdapter(mViewModel);
        recyclerView.setAdapter(mAdapter);

        rootView.findViewById(R.id.add_task).setOnClickListener(this);
    }

    private void setUpObservers() {
        mViewModel.getOnTask().observe(getViewLifecycleOwner(), new Observer<TaskResponse>() {
            @Override
            public void onChanged(@Nullable TaskResponse taskResponse) {
                switch (taskResponse.getAction()) {
                    case ITEM_ADDED:
                        mAdapter.addTask(taskResponse.getTask());
                        break;
                    case ITEM_CHANGED:
                        mAdapter.updateTask(taskResponse.getTask());
                        break;
                    case ITEM_DELETED:
                        mAdapter.deleteTask(taskResponse.getTask());
                        break;
                }
            }
        });

        mViewModel.getOnTaskClicked().observe(getViewLifecycleOwner(), new Observer<Task>() {
            @Override
            public void onChanged(@Nullable Task task) {
                ActivityUtils.replaceFragment(getActivity().getSupportFragmentManager(),
                        TaskDetailsFragment.getInstance(task), R.id.container, true);
            }
        });

        mViewModel.getOnlyUnDoneTasks().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (filterMenuItem != null)
                    if (!aBoolean)
                        filterMenuItem.setIcon(R.drawable.ic_done_filter_gray_24dp);
                    else
                        filterMenuItem.setIcon(R.drawable.ic_done_filter_blue_24dp);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_task:
                launchTaskDetails();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_task_list, menu);
        filterMenuItem = menu.getItem(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                mAdapter.clear();
                mViewModel.changeLoadingStatus();
                mViewModel.loadTasks(PreferenceUtils.getUserName(getContext()));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void launchTaskDetails() {
        AddTaskDialog dialog = new AddTaskDialog();
        dialog.show(getActivity().getSupportFragmentManager(), "");
    }
}
