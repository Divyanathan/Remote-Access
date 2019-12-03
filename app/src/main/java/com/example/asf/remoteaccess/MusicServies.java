package com.example.asf.remoteaccess;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by ASF on 03-May-16.
 */
public class MusicServies extends android.app.Service {
    MediaPlayer mediaPlayer;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(MusicServies.this, " Music started ", Toast.LENGTH_SHORT).show();
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        super.onStart(intent, startId);
    }




    @Override
    public void onDestroy() {
        Toast.makeText(MusicServies.this, "music stoped ", Toast.LENGTH_SHORT).show();

        mediaPlayer.stop();
        super.onDestroy();
    }

    @Nullable

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.alarm_sound);
        mediaPlayer.start();

        return super.onStartCommand(intent, flags, startId);
    }

}

