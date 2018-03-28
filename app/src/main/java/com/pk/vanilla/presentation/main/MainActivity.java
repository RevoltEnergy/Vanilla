package com.pk.vanilla.presentation.main;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.pk.vanilla.R;
import com.pk.vanilla.domain.model.Image;
import com.pk.vanilla.presentation.common.BaseActivity;
import com.pk.vanilla.presentation.details.ImageDetailFragment;
import com.pk.vanilla.presentation.search.ImageAdapter;
import com.pk.vanilla.presentation.search.ImageClickListener;
import com.pk.vanilla.presentation.search.ImageSearchFragment;

public class MainActivity extends BaseActivity implements MainMvp.View {

    private MainPresenter mainPresenter;
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);
        changeState(new ImageSearchFragment());
        mainPresenter.getImageList("yellow flowers");

//        RecyclerView recyclerView = findViewById(R.id.search_recycler_view);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        imageAdapter = new ImageAdapter(mainPresenter.getFakeData(), this);
//        recyclerView.setAdapter(imageAdapter);
    }
}
