package com.example.moviecatalogue.provider;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.moviecatalogue.viewmodels.LibraryFragmentViewModel;

public class FavoriteFactory implements ViewModelProvider.Factory {

    @NonNull
    private Application application;

    public FavoriteFactory(@NonNull Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LibraryFragmentViewModel.class)) {
            return (T) new LibraryFragmentViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
