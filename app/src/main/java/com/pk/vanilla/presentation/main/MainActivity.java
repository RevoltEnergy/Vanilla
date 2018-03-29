package com.pk.vanilla.presentation.main;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.SearchView;

import com.pk.vanilla.R;
import com.pk.vanilla.domain.model.Image;
import com.pk.vanilla.domain.service.DownloadCallback;
import com.pk.vanilla.domain.service.DownloadType;
import com.pk.vanilla.presentation.common.BaseActivity;
import com.pk.vanilla.presentation.network.NetworkFragment;
import com.pk.vanilla.presentation.search.ImageAdapter;
import com.pk.vanilla.presentation.search.ImageSearchFragment;
import com.pk.vanilla.presentation.search.ImageSearchPresenter;

import java.util.List;

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
        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);
        searchView = findViewById(R.id.searchView);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(false);
        searchView.setQueryHint("Search images...");
        searchView.clearFocus();
//        changeState(new ImageSearchFragment());
        mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager(), "");
        imageSearchFragment = new ImageSearchFragment();
//        mainPresenter.getImageList("yellow flowers");
    }

    private void startDownload(String url) {
        if (!mDownloading && mNetworkFragment != null) {
            // Execute the async download.
            mNetworkFragment.setUrlString(url);
            mNetworkFragment.startDownload(this, DownloadType.IMAGE_JSON);
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
            // You can add UI behavior for progress updates here.
            case Progress.ERROR:
                // TODO
                break;
            case Progress.CONNECT_SUCCESS:
                // TODO
                break;
            case Progress.GET_INPUT_STREAM_SUCCESS:
                // TODO
                break;
            case Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:
                // TODO
                break;
            case Progress.PROCESS_INPUT_STREAM_SUCCESS:
                // TODO
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
        String url = "https://pixabay.com/api/?key=8499934-51ca6dfffe38c79d79c24afc0&q=" + query.replaceAll(" ", "+") + "&image_type=photo";
        startDownload(url);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
