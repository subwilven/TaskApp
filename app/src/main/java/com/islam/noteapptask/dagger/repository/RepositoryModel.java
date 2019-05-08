package com.islam.noteapptask.dagger.repository;

import com.islam.noteapptask.data.TasksRepository;
import com.islam.noteapptask.utils.other.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class RepositoryModel {

    @Singleton
    @Provides
    static TasksRepository provideArticleRepository() {
        return new TasksRepository();
    }

    @Singleton
    @Provides
    static ViewModelFactory provideViewModelFactory(TasksRepository tasksRepository) {
        return new ViewModelFactory(tasksRepository);
    }
}

