package com.islam.noteapptask.dagger.repository;

import com.islam.noteapptask.utils.other.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RepositoryModel.class})
public interface RepositoryComponent {
    ViewModelFactory getViewModelFactory();
}
