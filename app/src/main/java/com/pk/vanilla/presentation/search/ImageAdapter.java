package com.pk.vanilla.presentation.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pk.vanilla.R;
import com.pk.vanilla.domain.model.Image;
import com.pk.vanilla.util.NetworkBackgroundBitmapUtil;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

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
        String webformatURL = image.getWebformatURL();
        StringBuilder description = new StringBuilder();
        description.append(image.getUser()).append(". ").append(image.getType()).append(". ").append(image.getTags());
        holder.textView.setText(description.toString());
        holder.spinner.setVisibility(View.VISIBLE);

        NetworkBackgroundBitmapUtil.downloadBitmap(webformatURL, holder.imageView, holder.spinner);

        holder.itemView.setOnClickListener(view -> {
            Toast.makeText(view.getContext(), "Details", Toast.LENGTH_SHORT).show();
            imageClickListener.openImageDetails(images.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        ProgressBar spinner;

        public ImageViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.imageDescription);
            imageView = itemView.findViewById(R.id.image);
            spinner = itemView.findViewById(R.id.progressBar);
        }
    }
}
