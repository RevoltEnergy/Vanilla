package com.pk.vanilla.presentation.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pk.vanilla.R;
import com.pk.vanilla.domain.model.Image;

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
        holder.textView.setText(images.get(position).getPageURL());
        holder.textView.setCompoundDrawables(null, null, null, null);

        holder.itemView.setOnClickListener(view -> {
            Toast.makeText(view.getContext(), "pressed", Toast.LENGTH_SHORT).show();
            imageClickListener.openImageDetails(images.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.imageDetails);
        }
    }
}
