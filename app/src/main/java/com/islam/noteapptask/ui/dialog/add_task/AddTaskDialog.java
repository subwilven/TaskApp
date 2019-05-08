package com.islam.noteapptask.ui.dialog.add_task;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.islam.noteapptask.MyApplication;
import com.islam.noteapptask.R;
import com.islam.noteapptask.pojo.Task;
import com.islam.noteapptask.ui.task_details.TaskDetailsFragment;
import com.islam.noteapptask.utils.ActivityUtils;
import com.islam.noteapptask.utils.PreferenceUtils;
import com.islam.noteapptask.utils.other.ViewModelFactory;

public class AddTaskDialog extends DialogFragment implements View.OnClickListener {

    EditText taskTitleEditText;
    AddTaskViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewModelFactory viewModelFactory = MyApplication.getInstance().getRepositoryComponent().getViewModelFactory();
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(AddTaskViewModel.class);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_task, null);
        taskTitleEditText = view.findViewById(R.id.dialog_et_task_title);
        view.findViewById(R.id.dialog_btn_new_task).setOnClickListener(this);

        builder.setView(view);

        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    @Override
    public void onClick(View v) {

        String taskTitle = taskTitleEditText.getText().toString();

        if (!taskTitle.trim().isEmpty()) {
            Task task = new Task(taskTitle);

            //create new task in the firebase database
            String taskId = mViewModel.createNewTask(PreferenceUtils.getUserName(getContext()), task);
            task.setId(taskId);

            ActivityUtils.replaceFragment(getActivity().getSupportFragmentManager(),
                    TaskDetailsFragment.getInstance(task), R.id.container, true);
            dismiss();
        }
    }
}
