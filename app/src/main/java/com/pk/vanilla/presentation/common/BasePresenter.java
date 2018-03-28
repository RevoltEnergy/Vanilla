package com.pk.vanilla.presentation.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class BasePresenter<V> implements BaseMvp.Presenter<V> {

    private V view;

    @Override
    public void create(@Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onSaveViewState(@NonNull Bundle bundle) {
    }

    @Override
    public void onRestoreViewState(Bundle savedInstanceState) {
    }

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void onBecomeVisible() {
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void destroy() {
    }

    @Override
    public V getView() {
        return view;
    }
}
