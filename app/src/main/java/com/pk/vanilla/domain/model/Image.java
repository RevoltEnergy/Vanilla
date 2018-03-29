package com.pk.vanilla.domain.model;

import android.text.TextUtils;

public class Image {
    private int previewHeight;
    private int likes;
    private int favorites;
    private String tags;
    private int webformatHeight;
    private long views;
    private int webformatWidth;
    private int previewWidth;
    private int comments;
    private long downloads;
    private String pageURL;
    private String previewURL;
    private String webformatURL;
    private int imageWidth;
    private long userId;
    private String user;
    private ImageType type;
    private long id;
    private String userImageURL;
    private String largeImageURL;
    private int imageHeight;

    public Image(int previewHeight, int likes, int favorites, String tags,
                 int webformatHeight, long views, int webformatWidth, int previewWidth,
                 int comments, long downloads, String pageURL, String previewURL,
                 String webformatURL, int imageWidth, long userId, String user,
                 ImageType type, long id, String userImageURL, String largeImageURL, int imageHeight) {

        this.previewHeight = previewHeight;
        this.likes = likes;
        this.favorites = favorites;
        this.tags = tags;
        this.webformatHeight = webformatHeight;
        this.views = views;
        this.webformatWidth = webformatWidth;
        this.previewWidth = previewWidth;
        this.comments = comments;
        this.downloads = downloads;
        this.pageURL = pageURL;
        this.previewURL = previewURL;
        this.webformatURL = webformatURL;
        this.imageWidth = imageWidth;
        this.userId = userId;
        this.user = user;
        this.type = type;
        this.id = id;
        this.userImageURL = userImageURL;
        this.largeImageURL = largeImageURL;
        this.imageHeight = imageHeight;
    }

    public int getPreviewHeight() {
        return previewHeight;
    }

    public String getLikes() {
        return String.valueOf(likes);
    }

    public String getFavorites() {
        return String.valueOf(favorites);
    }

    public String getTags() {
        if (tags == null) return "";
        if (tags.contains(", ")) {
            String[] splitTags = tags.toUpperCase().split(", ");
            return TextUtils.join(" - ", splitTags);
        } else return tags;
    }

    public int getWebformatHeight() {
        return webformatHeight;
    }

    public long getViews() {
        return views;
    }

    public int getWebformatWidth() {
        return webformatWidth;
    }

    public int getPreviewWidth() {
        return previewWidth;
    }

    public String getComments() {
        return String.valueOf(comments);
    }

    public long getDownloads() {
        return downloads;
    }

    public String getPageURL() {
        return pageURL;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public String getWebformatURL() {
        return webformatURL;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public long getUserId() {
        return userId;
    }

    public String getUser() {
        return "By: " + user;
    }

    public ImageType getType() {
        return type;
    }

    public long getId() {
        return id;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public static class Builder {
        private int previewHeight;
        private int likes;
        private int favorites;
        private String tags;
        private int webformatHeight;
        private long views;
        private int webformatWidth;
        private int previewWidth;
        private int comments;
        private long downloads;
        private String pageURL;
        private String previewURL;
        private String webformatURL;
        private int imageWidth;
        private long userId;
        private String user;
        private ImageType type;
        private long id;
        private String userImageURL;
        private String largeImageURL;
        private int imageHeight;

        public Builder setPreviewHeight(int previewHeight) {
            this.previewHeight = previewHeight;
            return this;
        }

        public Builder setLikes(int likes) {
            this.likes = likes;
            return this;
        }

        public Builder setFavorites(int favorites) {
            this.favorites = favorites;
            return this;
        }

        public Builder setTags(String tags) {
            this.tags = tags;
            return this;
        }

        public Builder setWebformatHeight(int webformatHeight) {
            this.webformatHeight = webformatHeight;
            return this;
        }

        public Builder setViews(long views) {
            this.views = views;
            return this;
        }

        public Builder setWebformatWidth(int webformatWidth) {
            this.webformatWidth = webformatWidth;
            return this;
        }

        public Builder setPreviewWidth(int previewWidth) {
            this.previewWidth = previewWidth;
            return this;
        }

        public Builder setComments(int comments) {
            this.comments = comments;
            return this;
        }

        public Builder setDownloads(long downloads) {
            this.downloads = downloads;
            return this;
        }

        public Builder setPageURL(String pageURL) {
            this.pageURL = pageURL;
            return this;
        }

        public Builder setPreviewURL(String previewURL) {
            this.previewURL = previewURL;
            return this;
        }

        public Builder setWebformatURL(String webformatURL) {
            this.webformatURL = webformatURL;
            return this;
        }

        public Builder setImageWidth(int imageWidth) {
            this.imageWidth = imageWidth;
            return this;
        }

        public Builder setUserId(long userId) {
            this.userId = userId;
            return this;
        }

        public Builder setUser(String user) {
            this.user = user;
            return this;
        }

        public Builder setType(ImageType type) {
            this.type = type;
            return this;
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setUserImageURL(String userImageURL) {
            this.userImageURL = userImageURL;
            return this;
        }

        public Builder setLargeImageURL(String largeImageURL) {
            this.largeImageURL = largeImageURL;
            return this;
        }

        public Builder setImageHeight(int imageHeight) {
            this.imageHeight = imageHeight;
            return this;
        }

        public Image build() {
            return new Image(previewHeight, likes, favorites, tags, webformatHeight, views,
                    webformatWidth, previewWidth, comments, downloads, pageURL, previewURL,
                    webformatURL, imageWidth, userId, user, type, id, userImageURL, largeImageURL, imageHeight);
        }
    }
}
