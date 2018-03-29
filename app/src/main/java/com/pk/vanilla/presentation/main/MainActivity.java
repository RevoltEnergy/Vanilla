package com.pk.vanilla.presentation.main;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import com.pk.vanilla.R;
import com.pk.vanilla.domain.model.Image;
import com.pk.vanilla.presentation.network.DownloadCallback;
import com.pk.vanilla.presentation.common.BaseActivity;
import com.pk.vanilla.presentation.network.NetworkFragment;
import com.pk.vanilla.presentation.search.ImageAdapter;
import com.pk.vanilla.presentation.search.ImageSearchFragment;
import com.pk.vanilla.presentation.search.ImageSearchPresenter;
import com.pk.vanilla.presentation.vanilla.VanillaFragment;
import com.pk.vanilla.util.ApiUtil;

import java.util.List;
import java.util.Objects;

public class MainActivity extends BaseActivity implements MainMvp.View, DownloadCallback, SearchView.OnQueryTextListener {

    private MainPresenter mainPresenter;
    private ImageSearchPresenter imageSearchPresenter;
    private ImageAdapter imageAdapter;
    private SearchView searchView;

    private ImageSearchFragment imageSearchFragment;

    // Keep a reference to the NetworkFragment, which owns the AsyncTask object
    // that is used to execute network ops.
    private NetworkFragment mNetworkFragment;

    // Boolean telling us whether a download is in progress, so we don't trigger overlapping
    // downloads with consecutive button clicks.
    private boolean mDownloading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);
        searchView = findViewById(R.id.searchView);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(false);
        searchView.setQueryHint("Search images...");
        searchView.clearFocus();
        changeState(new VanillaFragment());
        mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager(), "");
        imageSearchFragment = new ImageSearchFragment();
    }

    private void startDownload(String url) {
        if (!mDownloading && mNetworkFragment != null) {
            mNetworkFragment.setUrlString(url);
            mNetworkFragment.startDownload(this);
            mDownloading = true;
        }
    }

    @Override
    public void updateFromDownload(List<Image> images) {
        // Update your UI here based on result of download.
        // changeState(new ImageSearchFragment(List of images));

        imageSearchFragment.getImageSearchPresenter().setImageList(images);
        imageSearchFragment.updateView();
        changeState(imageSearchFragment);
    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
    }

    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) {
        switch (progressCode) {
            case Progress.ERROR:
                break;
            case Progress.CONNECT_SUCCESS:
                break;
            case Progress.GET_INPUT_STREAM_SUCCESS:
                break;
            case Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:
                break;
            case Progress.PROCESS_INPUT_STREAM_SUCCESS:
                break;
        }
    }

    @Override
    public void finishDownloading() {
        mDownloading = false;
        if (mNetworkFragment != null) {
            mNetworkFragment.cancelDownload();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchView.clearFocus();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(ApiUtil.QUERY_PREFIX)
                .append(ApiUtil.API_KEY)
                .append(ApiUtil.QUERY_PARAMETER)
                .append(query.replaceAll(" ", "+"))
                .append(ApiUtil.QUERY_SUFIX);
        startDownload(queryBuilder.toString());
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
