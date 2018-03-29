package com.pk.vanilla.presentation.details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pk.vanilla.R;
import com.pk.vanilla.util.NetworkBackgroundBitmapUtil;

import java.io.IOException;
import java.net.URL;

public class ImageDetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView textView = view.findViewById(R.id.imageDetailsText);
        ImageView imageView = view.findViewById(R.id.imageDetails);
        Bundle args = getArguments();
        if (args != null) {
            NetworkBackgroundBitmapUtil.downloadBitmap(args.getString("URL"), imageView);
            textView.setText(args.getString("Description"));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
