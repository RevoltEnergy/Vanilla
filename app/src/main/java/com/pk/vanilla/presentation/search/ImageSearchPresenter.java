package com.pk.vanilla.presentation.search;

import com.pk.vanilla.domain.model.Image;
import com.pk.vanilla.domain.model.ImageType;
import com.pk.vanilla.presentation.common.BasePresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImageSearchPresenter extends BasePresenter<ImageSearchFragment> implements ImageSearchMvp.Presenter {

    private List<Image> images = Collections.emptyList();

    @Override
    public void setImageList(List<Image> images) {
        this.images = images;
    }

    @Override
    public List<Image> getImageList() {
        return images;
    }
}
