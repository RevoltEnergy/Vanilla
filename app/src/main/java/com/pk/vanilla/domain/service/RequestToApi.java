package com.pk.vanilla.domain.service;

import android.os.Handler;
import android.os.Looper;

import com.pk.vanilla.domain.model.Image;
import com.pk.vanilla.domain.model.ImageList;
import com.pk.vanilla.domain.model.ImageType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestToApi {
    JSONObject imageListJson = new JSONObject();
    JSONArray jsonArray;
    List<Image> images = new ArrayList<>();
    private String requeustQuery;
    private URL url;
    private HttpURLConnection httpURLConnection;
    private BufferedReader bufferedReader;
    private StringBuilder stringBuilder;


    public RequestToApi(String query) {
        this.requeustQuery = "https://pixabay.com/api/?key=8499934-51ca6dfffe38c79d79c24afc0&q=" + query.replaceAll(" ", "+") + "&image_type=photo";
    }

    public ImageList getImageList() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    url = new URL(requeustQuery);
                    bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                    String line;
                    line = bufferedReader.readLine();
                    while (line !=null && !line.isEmpty()) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(line).append(System.lineSeparator());
                        line = bufferedReader.readLine();
                    }
                    bufferedReader.close();
                    String response = stringBuilder.toString();
                    if (!response.isEmpty()) {
                        imageListJson = new JSONObject(response);
                        jsonArray = imageListJson.getJSONArray("hits");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject imageJson = jsonArray.getJSONObject(i);
                            Image image = new Image.Builder()
                                    .setId(imageJson.getLong("id"))
                                    .setPageURL(imageJson.getString("pageURL"))
                                    .setType(getImageType(imageJson.getString("type")))
                                    .setTags(imageJson.getString("tags"))
                                    .setPreviewURL(imageJson.getString("previewURL"))
                                    .setWebformatURL(imageJson.getString("webformatURL"))
                                    .setUserImageURL(imageJson.getString("userImageURL"))
                                    .setUser(imageJson.getString("user"))
                                    .build();
                            images.add(image);
                        }
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

                Looper.prepare();
                final Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        // STOP SPINNER
                        handler.removeCallbacks(this);
                        Objects.requireNonNull(Looper.myLooper()).quit();
                    }
                });

                Looper.loop();
            }
        };

        thread.start();

        return null;
    }

    private ImageType getImageType(String type) {
        if (type.contains(ImageType.ILLUSTRATION.name())) {
            return ImageType.ILLUSTRATION;
        }
        if (type.contains(ImageType.VECTOR.name())) {
            return ImageType.VECTOR;
        }
        return ImageType.PHOTO;
    }
}
