package com.islam.noteapptask.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.islam.noteapptask.pojo.Comment;
import com.islam.noteapptask.pojo.Task;
import com.islam.noteapptask.pojo.TaskResponse;
import com.islam.noteapptask.utils.Constants;

public class TasksRepository {
    private DatabaseReference mDatabase;

    public TasksRepository() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public LiveData<TaskResponse> fetchUserTasks(String username, boolean onlyUnDoneTasks) {
        final MutableLiveData<TaskResponse> result = new MutableLiveData<>();
        Query query = null;
        if (onlyUnDoneTasks) {
            query = mDatabase.getRef().child(Constants.FireBase.TASKS).child(username)
                    .orderByChild(Constants.FireBase.DONE).equalTo(false);
        } else {
            query = mDatabase.getRef().child(Constants.FireBase.TASKS).child(username);
        }

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Task task = dataSnapshot.getValue(Task.class);
                task.setId(dataSnapshot.getKey());
                result.setValue(TaskResponse.addedTaskInstance(task));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Task task = dataSnapshot.getValue(Task.class);
                task.setId(dataSnapshot.getKey());
                result.setValue(TaskResponse.changedTaskInstance(task));
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Task task = dataSnapshot.getValue(Task.class);
                task.setId(dataSnapshot.getKey());
                result.setValue(TaskResponse.deletedTaskInstance(task));
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return result;
    }

    public String createNewTask(String username, Task task) {
        String taskId = mDatabase.child(Constants.FireBase.TASKS).child(username).push().getKey();
        mDatabase.child(Constants.FireBase.TASKS).child(username).child(taskId).setValue(task);
        return taskId;
    }

    public void updateTaskDetails(String username, Task task) {
        mDatabase.child(Constants.FireBase.TASKS).child(username).child(task.getId()).setValue(task);
    }

    public void deleteTask(String username, Task task) {
        mDatabase.child(Constants.FireBase.TASKS).child(username).child(task.getId()).setValue(null);
    }

    public void addComment(String username, Task task, Comment newComment, String commentNum) {
        mDatabase.child(Constants.FireBase.TASKS).child(username).child(task.getId())
                .child(Constants.FireBase.COMMENTS).child(commentNum).setValue(newComment);
    }
}
