package com.islam.noteapptask.ui.task_details;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.islam.noteapptask.MyApplication;
import com.islam.noteapptask.R;
import com.islam.noteapptask.databinding.FragmentTaskDetailsBinding;
import com.islam.noteapptask.pojo.Comment;
import com.islam.noteapptask.pojo.Task;
import com.islam.noteapptask.utils.ActivityUtils;
import com.islam.noteapptask.utils.PreferenceUtils;
import com.islam.noteapptask.utils.other.ViewModelFactory;

import info.hoang8f.android.segmented.SegmentedGroup;

import static com.islam.noteapptask.utils.Constants.BUNDLE_TASK;

public class TaskDetailsFragment extends Fragment implements View.OnClickListener {

    private CommentsAdapter mAdapter;
    private TaskDetailsViewModel mViewModel;
    private EditText commentEditText;
    private ImageView taskStatusImageView;
    private SegmentedGroup segmentedGroup;

    public static TaskDetailsFragment getInstance(Task task) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_TASK, task);
        TaskDetailsFragment taskDetailsFragment = new TaskDetailsFragment();
        taskDetailsFragment.setArguments(bundle);
        return taskDetailsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_details, container, false);

        ViewModelFactory viewModelFactory = MyApplication.getInstance().getRepositoryComponent().getViewModelFactory();
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(TaskDetailsViewModel.class);

        Task task = (Task) getArguments().getSerializable(BUNDLE_TASK);
        mViewModel.init(PreferenceUtils.getUserName(getContext()), task);

        initViews(rootView);

        bindViews(rootView);

        setUpObservers();

        setHasOptionsMenu(true);

        return rootView;
    }

    private void setUpObservers() {
        mViewModel.getTask().observe(getViewLifecycleOwner(), new Observer<Task>() {
            @Override
            public void onChanged(@Nullable Task task) {
                //set toolbar title
                ((TextView) getActivity().findViewById(R.id.toolbar_title)).setText(task.getTitle());

                segmentedGroup.check(ActivityUtils.getTaskPriority(task));

                if (task.isDone())
                    taskStatusImageView.setImageResource(R.drawable.ic_check_circle_blue_48dp);
                else
                    taskStatusImageView.setImageResource(R.drawable.ic_circle_blue_48dp);

                if (mAdapter.getItemCount() == 0)
                    mAdapter.setData(task.getComments());
            }
        });

        mViewModel.getOnCommentAdded().observe(getViewLifecycleOwner(), new Observer<Comment>() {
            @Override
            public void onChanged(@Nullable Comment comment) {
                mAdapter.addItem(comment);
            }
        });
    }


    public void initViews(View view) {
        view.findViewById(R.id.task_details_iv_send).setOnClickListener(this);
        commentEditText = view.findViewById(R.id.task_details_et_comment);
        segmentedGroup = view.findViewById(R.id.task_details_priority);
        taskStatusImageView = view.findViewById(R.id.task_details_iv_status);

        RecyclerView recyclerView = view.findViewById(R.id.task_details_rv_comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new CommentsAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    public void bindViews(View view) {
        FragmentTaskDetailsBinding binding = DataBindingUtil.bind(view);
        binding.setViewModel(mViewModel);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_task_details, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                mViewModel.deleteTask();
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        String comment = commentEditText.getText().toString();
        commentEditText.setText("");
        mViewModel.addComment(comment);
        hideKeyboard();
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}
