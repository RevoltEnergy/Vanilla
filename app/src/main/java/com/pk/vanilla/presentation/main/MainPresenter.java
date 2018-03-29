package com.pk.vanilla.presentation.main;

import com.pk.vanilla.domain.model.Image;
import com.pk.vanilla.domain.model.ImageList;
import com.pk.vanilla.domain.model.ImageType;
import com.pk.vanilla.domain.service.RequestToApi;
import com.pk.vanilla.presentation.common.BasePresenter;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter extends BasePresenter<MainMvp.View> implements MainMvp.Presetner {
    @Override
    public List<Image> getData() {
        return null;
    }

    @Override
    public List<Image> getFakeData() {
        List<Image> images = new ArrayList<>();

        return images;
    }

    @Override
    public ImageList getImageList(String query) {
        RequestToApi api = new RequestToApi(query);
        return api.getImageList();
    }
}
