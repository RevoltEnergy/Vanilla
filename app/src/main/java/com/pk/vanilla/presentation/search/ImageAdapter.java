package com.pk.vanilla.presentation.search;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pk.vanilla.R;
import com.pk.vanilla.domain.model.Image;
import com.pk.vanilla.domain.service.DownloadCallback;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> implements DownloadCallback<Bitmap> {

    private List<Image> images;
    private ImageClickListener imageClickListener;

    public ImageAdapter(List<Image> images, ImageClickListener imageClickListener) {
        this.images = images;
        this.imageClickListener = imageClickListener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Image image = images.get(position);
        String previewUrl = image.getPreviewURL();
        StringBuilder description = new StringBuilder();
        description.append(image.getUser()).append(". ").append(image.getType()).append(". ").append(image.getTags());
        holder.textView.setText(description.toString());

        new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                try {
                    bitmap = downloadBitmap(new URL(previewUrl));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Bitmap finalBitmap = bitmap;
                new Handler(Looper.getMainLooper()).post(() -> holder.imageView.setImageBitmap(finalBitmap));
            }
        }.start();

        holder.itemView.setOnClickListener(view -> {
            Toast.makeText(view.getContext(), "pressed", Toast.LENGTH_SHORT).show();
            imageClickListener.openImageDetails(images.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    @Override
    public void updateFromDownload(Bitmap result) {

    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        return null;
    }

    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) {

    }

    @Override
    public void finishDownloading() {

    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.imageDetails);
            imageView = itemView.findViewById(R.id.image);
        }
    }

    private Bitmap downloadBitmap(URL url) throws IOException {
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
