package com.pk.vanilla.presentation.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface BaseMvp {

    interface View {}

    interface Presenter<V> {

        void create(@Nullable Bundle savedInstanceState);

        void onSaveViewState(@NonNull Bundle bundle);

        void onRestoreViewState(Bundle savedInstanceState);

        void attachView(V view);

        void onBecomeVisible();

        void detachView();

        void destroy();

        V getView();
    }
}
