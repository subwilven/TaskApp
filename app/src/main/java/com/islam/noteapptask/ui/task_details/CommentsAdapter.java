package com.islam.noteapptask.ui.task_details;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.islam.noteapptask.R;
import com.islam.noteapptask.databinding.ItemCommentBinding;
import com.islam.noteapptask.pojo.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private List<Comment> commentList;



    @NonNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comment, viewGroup, false);
        ViewDataBinding viewDataBinding = DataBindingUtil.bind(view);
        return new ViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bind(commentList.get(i));
    }

    @Override
    public int getItemCount() {
        if (commentList == null)
            return 0;
        return commentList.size();
    }

    public void setData(List<Comment> commentList) {
        this.commentList = commentList;
        notifyDataSetChanged();
    }

    public void addItem(Comment comment) {
        if(commentList==null)
            commentList = new ArrayList<>();
        commentList.add(comment);
        notifyItemInserted(getItemCount()-1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCommentBinding binding;

        public ViewHolder(@NonNull ViewDataBinding itemView) {
            super(itemView.getRoot());
            binding = (ItemCommentBinding) itemView;
        }

        public void bind(Comment comment) {
            binding.setComment(comment);
            binding.executePendingBindings();
        }
    }
}
