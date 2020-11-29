package com.abanoub.notes.notification;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.abanoub.notes.room.Note;
import com.abanoub.notes.R;

public class NotificationHelper extends ContextWrapper {
    public static final String CHANNEL_ID="com.abanoub.notes.channelid";
    public static final String CHANNEL_NAME="com.abanoub.notes.channelname";
    public static final String CHANNEL_DESC="com.abanoub.notes.channeldesc ";

    private NotificationManager notificationManager;

    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }
    public NotificationCompat.Builder getChannelNotification(Note note) {
        Intent resultIntent = new Intent(this,NotificationView.class);
        resultIntent.putExtra("title",note.getNoteTitle());
        resultIntent.putExtra("content",note.getNoteContent());
        resultIntent.putExtra("time",note.getNoteTime());
        resultIntent.putExtra("importance",note.getNoteImportance());

        PendingIntent resultPendingIntent=PendingIntent.getActivity(this,1,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle(note.getNoteTitle())
                .setContentText(note.getNoteContent())
                .setSmallIcon(R.drawable.ic_note)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent);
    }

}
