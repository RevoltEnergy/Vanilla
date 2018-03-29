package com.pk.vanilla.presentation.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pk.vanilla.R;
import com.pk.vanilla.domain.model.Image;
import com.pk.vanilla.presentation.details.ImageDetailFragment;

import java.util.Collections;
import java.util.List;

public class ImageSearchFragment extends Fragment implements ImageSearchMvp.View, ImageClickListener {

    private ImageSearchPresenter imageSearchPresenter = new ImageSearchPresenter();
    private ImageAdapter imageAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageSearchPresenter.attachView(this);

        RecyclerView recyclerView = view.findViewById(R.id.search_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        List<Image> images = imageSearchPresenter.getImageList() == null ? Collections.emptyList() : imageSearchPresenter.getImageList();
        imageAdapter = new ImageAdapter(images, this);
        recyclerView.setAdapter(imageAdapter);
    }

    @Override
    public void openImageDetails(Image image) {
        if (getView() != null) {
            FragmentTransaction transaction;
            if (getFragmentManager() != null) {
                transaction = getFragmentManager().beginTransaction();
                ImageDetailFragment detailFragment = new ImageDetailFragment();
                Bundle args = new Bundle();
                args.putString("URL", image.getPageURL());
                detailFragment.setArguments(args);
                transaction.add(R.id.container, detailFragment);
                transaction.commit();
            } else {
                Toast.makeText(getContext(), "No Fragment Manager", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "View is null", Toast.LENGTH_SHORT).show();
        }
    }

    public ImageSearchPresenter getImageSearchPresenter() {
        return imageSearchPresenter;
    }

    public ImageAdapter getImageAdapter() {
        return imageAdapter;
    }
}
