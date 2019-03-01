package com.pole6lynn.servicedemocode;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

public class DownLoadService extends Service {
    private DownLoadTask mDownLoadTask;
    private String mDownLoadUri;

    private DownLoadBinder mDownLoadBinder = new DownLoadBinder();
    private DownLoadListener mDownLoadListener = new DownLoadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1, getNotificaton("DownLoad...",
                    progress));
        }

        @Override
        public void onSuccess() {
            mDownLoadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotificaton("DownLoad success.", -1));
            Toast.makeText(DownLoadService.this, "DownLoad success",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            mDownLoadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotificaton("DownLoad Failed.", -1));
            Toast.makeText(DownLoadService.this, "DownLoad Failed",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPause() {
            Log.e("Pole","pause---");
            mDownLoadTask = null;
            Toast.makeText(DownLoadService.this, "DownLoad Pause",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            mDownLoadTask = null;
            stopForeground(true);
            Toast.makeText(DownLoadService.this, "DownLoad Cancel",
                    Toast.LENGTH_SHORT).show();
        }
    };

    class DownLoadBinder extends Binder {
        public void startDownLoad(String uri) {
            if (mDownLoadUri == null) {
                mDownLoadUri = uri;
                mDownLoadTask = new DownLoadTask(mDownLoadListener);
                mDownLoadTask.execute(mDownLoadUri);
                startForeground(1, getNotificaton("DownLoading...", 0));
                Toast.makeText(DownLoadService.this, "DownLoading...",
                        Toast.LENGTH_SHORT).show();
            }

        }

        public void pauseDownLoad() {
            if (mDownLoadTask != null) {
                mDownLoadTask.pauseDownLoad();
            }

        }

        public void cancelDownLoad() {
            if (mDownLoadTask != null) {
                mDownLoadTask.canceledDownLoad();
            } else {
                if (mDownLoadUri != null) {
                    String fileName = mDownLoadUri.substring(
                            mDownLoadUri.lastIndexOf("/"));
                    String directory = Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directory + fileName);
                    if (file.exists()) {
                        file.delete();
                    }
                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    Toast.makeText(DownLoadService.this, "DownLoad Cancel",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public DownLoadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mDownLoadBinder;
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotificaton(String title, int progress) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher));
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle(title);
        if (progress > 0) {
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }
        return builder.build();
    }
}
