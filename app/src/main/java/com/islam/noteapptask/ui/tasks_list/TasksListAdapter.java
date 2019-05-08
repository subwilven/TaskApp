package com.islam.noteapptask.ui.tasks_list;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.islam.noteapptask.R;
import com.islam.noteapptask.databinding.ItemTaskBinding;
import com.islam.noteapptask.pojo.Task;

import java.util.ArrayList;
import java.util.List;

public class TasksListAdapter extends RecyclerView.Adapter<TasksListAdapter.ViewHolder> {

    private List<Task> taskList;
    private TasksListViewModel viewModel;

    public TasksListAdapter(TasksListViewModel viewModel) {
        this.taskList = new ArrayList<>();
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);
        ViewDataBinding viewDataBinding = DataBindingUtil.bind(view);
        return new ViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(taskList.get(i));
    }

    @Override
    public int getItemCount() {
        if (taskList == null)
            return 0;
        return taskList.size();
    }

    public void addTask(Task task) {
        taskList.add(task);
        notifyItemInserted(getItemCount() - 1);
    }

    public void deleteTask(Task task) {
        int index = taskList.indexOf(task);
        taskList.remove(index);
        notifyItemRemoved(index);
    }

    public void updateTask(Task task) {
        int index = taskList.indexOf(task);
        taskList.set(index,task);
        notifyItemChanged(index);
    }

    public void clear() {
        taskList.clear();
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemTaskBinding binding;

        public ViewHolder(@NonNull ViewDataBinding itemView) {
            super(itemView.getRoot());
            binding = (ItemTaskBinding) itemView;
        }

        public void bind(Task task) {
            binding.setTask(task);
            binding.setViewModel(viewModel);
            binding.executePendingBindings();
        }
    }
}
