package com.islam.noteapptask;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.islam.noteapptask.dagger.repository.DaggerRepositoryComponent;
import com.islam.noteapptask.dagger.repository.RepositoryComponent;

public class MyApplication extends Application {

    private RepositoryComponent repositoryComponent;
    private static MyApplication mInstance;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

    public RepositoryComponent getRepositoryComponent() {
        if (repositoryComponent == null)
            repositoryComponent = DaggerRepositoryComponent.create();
        return repositoryComponent;
    }
}
