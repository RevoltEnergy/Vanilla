package com.pk.vanilla.presentation.search;

import com.pk.vanilla.domain.model.Image;

import java.util.List;

public interface ImageSearchMvp {

    interface View {}

    interface Presenter {
        List<Image> getData();
        List<Image> getFakeData();
    }
}
