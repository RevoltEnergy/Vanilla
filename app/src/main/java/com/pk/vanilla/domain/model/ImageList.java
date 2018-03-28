package com.pk.vanilla.domain.model;

import java.util.List;

public class ImageList {
    private int total;
    private int totalHits;
    private List<Image> hits;

    public ImageList(int total, int totalHits, List<Image> hits) {
        this.total = total;
        this.totalHits = totalHits;
        this.hits = hits;
    }

    public int getTotal() {
        return total;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public List<Image> getHits() {
        return hits;
    }

    public int getTotalOfPages() {
        return (int) Math.ceil(total / 20.0);
    }
}
