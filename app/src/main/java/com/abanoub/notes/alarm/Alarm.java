package com.abanoub.notes.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.abanoub.notes.room.Note;
import com.abanoub.notes.notification.NotificationHelper;

import org.jetbrains.annotations.NotNull;

public class Alarm extends BroadcastReceiver {
    private MediaPlayer mediaPlayer;
    @Override
    public void onReceive(Context context, @NotNull Intent intent)
    {
        Log.d("alarmR","onReceive ================================");
        mediaPlayer=MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);

        Note note = new Note(intent.getStringExtra("noteTitle"),intent.getStringExtra("noteContent"),
                intent.getStringExtra("noteTime"),intent.getStringExtra("noteImportance"));
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(note);
        notificationHelper.getManager().notify(1, nb.build());
        Log.d("Abanoub","Alarm is Fired");
        mediaPlayer.start();


    }
}
