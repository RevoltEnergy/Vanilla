package com.pk.vanilla.presentation.main;

import com.pk.vanilla.domain.model.Image;

import java.util.List;

public interface MainMvp {

    interface View {

    }

    interface Presetner {
        List<Image> getData();
        List<Image> getFakeData();
    }
}
