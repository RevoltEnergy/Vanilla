package com.pk.vanilla.presentation.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.pk.vanilla.domain.model.Image;
import com.pk.vanilla.domain.model.ImageType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class NetworkFragment extends Fragment {
    public static final String TAG = "NetworkFragment";

    private static final String URL_KEY = "UrlKey";

    private DownloadCallback mCallback;
    private DownloadTask mDownloadTask;
    private String mUrlString;

    public void setUrlString(String mUrlString) {
        this.mUrlString = mUrlString;
    }

    public static NetworkFragment getInstance(FragmentManager fragmentManager, String url) {
        NetworkFragment networkFragment = (NetworkFragment) fragmentManager
                .findFragmentByTag(NetworkFragment.TAG);
        if (networkFragment == null) {
            networkFragment = new NetworkFragment();
            Bundle args = new Bundle();
            args.putString(URL_KEY, url);
            networkFragment.setArguments(args);
            fragmentManager.beginTransaction().add(networkFragment, TAG).commit();
        }
        return networkFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mUrlString = getArguments() != null ? getArguments().getString(URL_KEY) : "";
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (DownloadCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onDestroy() {
        cancelDownload();
        super.onDestroy();
    }

    public void startDownload(DownloadCallback downloadCallback) {
        cancelDownload();
        mDownloadTask = new DownloadTask(downloadCallback);
        mDownloadTask.execute(mUrlString);
    }

    public void cancelDownload() {
        if (mDownloadTask != null) {
            mDownloadTask.cancel(true);
        }
    }

    private static class DownloadTask extends AsyncTask<String, Integer, DownloadTask.Result> {

        private DownloadCallback mCallback;

        DownloadTask(DownloadCallback callback) {
            setCallback(callback);
        }

        void setCallback(DownloadCallback callback) {
            mCallback = callback;
        }

        class Result {
            List<Image> mResultValue;
            Exception mException;

            Result(List<Image> resultValue) {
                mResultValue = resultValue;
            }
        }

        @Override
        protected void onPreExecute() {
            if (mCallback != null) {
                NetworkInfo networkInfo = mCallback.getActiveNetworkInfo();
                if (networkInfo == null || !networkInfo.isConnected() ||
                        (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                                && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
                    mCallback.updateFromDownload(null);
                    cancel(true);
                }
            }
        }

        @Override
        protected DownloadTask.Result doInBackground(String... urls) {
            if (!isCancelled() && urls != null && urls.length > 0) {
                String urlString = urls[0];
                try {
                    URL url = new URL(urlString);
                    return new Result(mapResult(downloadUrl(url)));
                } catch (Exception ignored) {
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Result result) {
            if (result != null && mCallback != null) {
                if (result.mException != null || result.mResultValue != null) {
                    mCallback.updateFromDownload(result.mResultValue);
                }
                mCallback.finishDownloading();
            }
        }

        @Override
        protected void onCancelled(Result result) {
        }

        private String downloadUrl(URL url) throws IOException {
            InputStream stream = null;
            HttpsURLConnection connection = null;
            String result = null;
            try {
                connection = (HttpsURLConnection) url.openConnection();
                connection.setReadTimeout(3000);
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                publishProgress(DownloadCallback.Progress.CONNECT_SUCCESS);
                int responseCode = connection.getResponseCode();
                if (responseCode != HttpsURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code: " + responseCode);
                }
                stream = connection.getInputStream();
                publishProgress(DownloadCallback.Progress.GET_INPUT_STREAM_SUCCESS, 0);
                if (stream != null) {
                    result = readStream(stream, 500_000);
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

        String readStream(InputStream stream, int maxReadSize)
                throws IOException {
            Reader reader;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] rawBuffer = new char[maxReadSize];
            int readSize;
            StringBuffer buffer = new StringBuffer();
            while (((readSize = reader.read(rawBuffer)) != -1) && maxReadSize > 0) {
                if (readSize > maxReadSize) {
                    readSize = maxReadSize;
                }
                buffer.append(rawBuffer, 0, readSize);
                maxReadSize -= readSize;
            }
            return buffer.toString();
        }

        private List<Image> mapResult(String resultString) throws JSONException {
            List<Image> images = new ArrayList<>();
            if (!resultString.isEmpty()) {
                JSONObject imageListJson = new JSONObject(resultString);
                JSONArray jsonArray = imageListJson.getJSONArray("hits");
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
                            .setLargeImageURL(imageJson.getString("largeImageURL"))
                            .setUser(imageJson.getString("user"))
                            .build();
                    images.add(image);
                }
            }
            return images;
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
}
