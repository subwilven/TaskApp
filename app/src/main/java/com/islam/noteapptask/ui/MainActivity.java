package com.islam.noteapptask.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.islam.noteapptask.R;
import com.islam.noteapptask.ui.tasks_list.TasksListFragment;
import com.islam.noteapptask.utils.ActivityUtils;

public class MainActivity extends AppCompatActivity {

    public static void launch(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        if (savedInstanceState == null)
            ActivityUtils.replaceFragment(getSupportFragmentManager(), new TasksListFragment(), R.id.container,false);
    }

}
