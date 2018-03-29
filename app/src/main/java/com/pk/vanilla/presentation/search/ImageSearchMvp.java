package com.pk.vanilla.presentation.search;

import com.pk.vanilla.domain.model.Image;

import java.util.List;

public interface ImageSearchMvp {

    interface View {}

    interface Presenter {
        void setImageList(List<Image> images);
        List<Image> getImageList();
    }
}
