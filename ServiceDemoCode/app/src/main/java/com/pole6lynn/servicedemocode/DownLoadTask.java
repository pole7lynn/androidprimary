package com.pole6lynn.servicedemocode;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Random;
import java.util.RandomAccess;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownLoadTask extends AsyncTask<String, Integer, Integer> {
    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSE = 2;
    public static final int TYPE_CANCELED = 3;

    private DownLoadListener mDownLoadListener;
    private boolean isCanceled = false;
    private boolean isPaused = false;
    private int mLastProgress;

    public DownLoadTask(DownLoadListener downLoadListener) {
        mDownLoadListener = downLoadListener;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        InputStream inputStream = null;
        RandomAccessFile savedFile = null;
        File file = null;
        try {
            long downLoadedLength = 0;
            String downLoadUri = strings[0];
            String fileName = downLoadUri.substring(downLoadUri.lastIndexOf("/"));
            String directory = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS).getPath();
            file = new File(directory + fileName);
            if (file.exists()) {
                downLoadedLength = file.length();
            }

            long contentLength = getContentLength(downLoadUri);
            if (contentLength == 0) {
                return TYPE_FAILED;
            } else if (contentLength == downLoadedLength) {
                return TYPE_SUCCESS;
            }

            //down.
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("RANGE", "bytes = " + downLoadedLength + "-")
                    .url(downLoadUri)
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null) {
                inputStream = response.body().byteStream();
                savedFile = new RandomAccessFile(file, "rw");
                savedFile.seek(downLoadedLength);

                byte[] b = new byte[1024];
                int total = 0;
                int len = 0;
                while ((len = inputStream.read(b)) != -1) {
                    if (isCanceled) {
                        return TYPE_CANCELED;
                    } else if (isPaused) {
                        return TYPE_PAUSE;
                    } else {
                        total += len;
                        savedFile.write(b, 0, len);
                        int progress = (int) ((total + downLoadedLength) * 100 / contentLength);
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
         try {
             if (inputStream != null) {
                 inputStream.close();
             }
             if (savedFile != null) {
                 savedFile.close();
             }
             if (isCanceled && file != null) {
                 file.delete();
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
        }
        return TYPE_FAILED;
    }

    private long getContentLength(String uri) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(uri)
                .build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()) {
            long contentLength = response.body().contentLength();
            response.close();
            return contentLength;
        }
        return 0;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int progress = values[0];
        if (progress > mLastProgress) {
            mDownLoadListener.onProgress(progress);
            mLastProgress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        switch (integer) {
            case TYPE_SUCCESS:
                mDownLoadListener.onSuccess();
                break;
            case TYPE_FAILED:
                mDownLoadListener.onFailed();
                break;
            case TYPE_PAUSE:
                mDownLoadListener.onPause();
                break;
            case TYPE_CANCELED:
                mDownLoadListener.onCanceled();
                break;
            default:
                break;
        }
    }

    public void pauseDownLoad() {
        isPaused = true;
    }

    public void canceledDownLoad() {
        isCanceled = true;
    }
}
