package com.abanoub.notes.notification;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.abanoub.notes.R;
import com.abanoub.notes.alarm.Alarm;
import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

public class NotificationView extends Activity {
    private TextView noteTitle,noteContent,noteTime;
    private MaterialButton dismissBtn;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        Intent intent = getIntent();
        noteTitle = findViewById(R.id.note_tv);
        noteContent = findViewById(R.id.content_tv);
        noteTime = findViewById(R.id.date_time_tv);
        dismissBtn = findViewById(R.id.dismiss_btn);
        if (intent.hasExtra("title")&&intent.hasExtra("content")) {
            noteTitle.setText(intent.getStringExtra("title"));
            noteContent.setText(intent.getStringExtra("content"));
            noteTime.setText("It's "+intent.getStringExtra("time")+" you asked me to REMIND you to do the Following :)");
        }

        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelAlarm();
                finish();
            }
        });
    }
    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        //Toast.makeText(this, "Dis", Toast.LENGTH_LONG).show();
    }
}
