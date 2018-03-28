package com.pk.vanilla.presentation.search;

import com.pk.vanilla.domain.model.Image;
import com.pk.vanilla.domain.model.ImageType;
import com.pk.vanilla.presentation.common.BasePresenter;

import java.util.ArrayList;
import java.util.List;

public class ImageSearchPresenter extends BasePresenter<ImageSearchFragment> implements ImageSearchMvp.Presenter {

    @Override
    public List<Image> getData() {
        return null;
    }

    @Override
    public List<Image> getFakeData() {
        List<Image> images = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Image image = new Image(1, 2, 3, "4", 5, 6, 7, 8, 9, 10,
                    "URL" + i, "preURL", "wURL", 11, i, "lol", ImageType.ILLUSTRATION, i, "uURL", 12);
            images.add(image);
        }

        return images;
    }
}
