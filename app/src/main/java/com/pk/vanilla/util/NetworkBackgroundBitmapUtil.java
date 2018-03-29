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
            // Timeout for reading InputStream arbitrarily set to 3000ms.
            connection.setReadTimeout(3000);
            // Timeout for connection.connect() arbitrarily set to 3000ms.
            connection.setConnectTimeout(3000);
            // For this use case, set HTTP method to GET.
            connection.setRequestMethod("GET");
            // Already true by default but setting just in case; needs to be true since this request
            // is carrying an input (response) body.
            connection.setDoInput(true);
            // Open communications link (network traffic occurs here).
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }
            // Retrieve the response body as an InputStream.
            stream = connection.getInputStream();
            if (stream != null) {
                // Converts Stream to String with max length of 500.
                result = BitmapFactory.decodeStream(stream);
            }
        } finally {
            // Close Stream and disconnect HTTPS connection.
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
