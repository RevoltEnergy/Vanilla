package com.pk.vanilla.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class NetworkBackgroundBitmapUtil {

    public static void downloadBitmap(String url, ImageView imageView) {
        new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                try {
                    bitmap = NetworkBackgroundBitmapUtil.downloadBitmap(new URL(url));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Bitmap finalBitmap = bitmap;
                new Handler(Looper.getMainLooper()).post(() -> imageView.setImageBitmap(finalBitmap));
            }
        }.start();
    }

    private static Bitmap downloadBitmap(URL url) throws IOException {
        InputStream stream = null;
        HttpsURLConnection connection = null;
        Bitmap result = null;
        try {
            connection = (HttpsURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }
            stream = connection.getInputStream();
            if (stream != null) {
                result = BitmapFactory.decodeStream(stream);
            }
        } finally {
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }
}
