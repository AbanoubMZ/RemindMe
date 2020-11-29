package com.abanoub.notes;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.abanoub.notes.alarm.Alarm;
import com.abanoub.notes.fragments.DatePickerFragment;
import com.abanoub.notes.fragments.TimePickerFragment;
import com.abanoub.notes.room.Note;
import com.google.android.material.button.MaterialButton;

import java.text.DateFormat;
import java.util.Calendar;

public class AddNoteActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private EditText noteTitle,noteContent;
    private TextView dateAndTimeTV;
    private RadioGroup radioImportanceGroup;
    private RadioButton radioImportanceButton;
    private MaterialButton timePickerBtn,datePickerBtn;
    private NoteViewModel noteViewModel;
    private ImageView backIV,saveIV;
    private boolean editMode;
    private int noteID;
    private Calendar calendar;
    public static final String EXTRA_ID="com.abanoub.notes.extraid";
    public static final String EXTRA_TITLE="com.abanoub.notes.title";
    public static final String EXTRA_CONTENT="com.abanoub.notes.content";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);
        noteTitle = findViewById(R.id.note_title_et);
        noteContent = findViewById(R.id.note_content_et);
        dateAndTimeTV=findViewById(R.id.date_time_tv);
        timePickerBtn = findViewById(R.id.time_picker);
        datePickerBtn= findViewById(R.id.date_picker);
        calendar=Calendar.getInstance();
        radioImportanceGroup= findViewById(R.id.radioImportance);
        backIV= findViewById(R.id.back_iv);
        saveIV= findViewById(R.id.save_iv);

        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker =new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });

        timePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        saveIV.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        Intent intent= getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            editMode=true;
            noteID=intent.getIntExtra(EXTRA_ID,-1);
            noteTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            noteContent.setText(intent.getStringExtra(EXTRA_CONTENT));

        }else{
            editMode=false;
            setTitle("New Note");
            //Insert New Note
        }
        noteViewModel= ViewModelProviders.of(this).get(NoteViewModel.class);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        Calendar current = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,i);
        calendar.set(Calendar.MINUTE, i1);
        calendar.set(Calendar.SECOND, 0);
        System.out.println("Time -----> "+calendar.toString());
        if (calendar.compareTo(current) < 0) {
            timePicker.requestFocus();
            Toast.makeText(getApplicationContext(), "Invalid Time", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            Calendar current = Calendar.getInstance();
            calendar.set(Calendar.YEAR, i);
            calendar.set(Calendar.MONTH, i1);
            calendar.set(Calendar.DAY_OF_MONTH, i2);
            datePicker.setMinDate(current.getTimeInMillis());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
                saveNote();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void saveNote() {
        String title=noteTitle.getText().toString().trim();
        String content=noteContent.getText().toString().trim();
        //get Importance
        int selectedId = radioImportanceGroup.getCheckedRadioButtonId();
        radioImportanceButton=findViewById(selectedId);
        String importance=radioImportanceButton.getText().toString().trim();
        Log.d("Importance",importance);

        if(title.isEmpty()){
            noteTitle.setError("Please Enter Title");
            noteTitle.requestFocus();
            return;
        }
        if(content.isEmpty()){
            noteTitle.setError("Please Enter Title");
            noteTitle.requestFocus();
            return;
        }
        Calendar c = calendar;
        System.out.println("Note -----> "+c.toString());

        String time= updateTimeText(c);
        String dateAndTime=DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime()) + " - "+time;
        dateAndTimeTV.setText(dateAndTime);
        Note note = new Note(title,content,dateAndTime,importance);

        if(editMode){
            note.setId(noteID);
            noteViewModel.Update(note);

        }else {
            noteViewModel.Insert(note);
        }
        setAlarm(c,note);
        calendar.clear();
        finish();
    }
    private void setAlarm(Calendar c, Note note) {
        //getting the alarm manager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //creating a new intent specifying the broadcast receiver
        Intent intent = new Intent(this, Alarm.class);

        //Broadcasting the note data to Trigger notification with details
        intent.putExtra("noteTitle", note.getNoteTitle());
        intent.putExtra("noteContent", note.getNoteContent());
        intent.putExtra("noteTime", note.getNoteTime());
        intent.putExtra("noteImportance",note.getNoteImportance());
        //creating a pending intent using the intent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1 ,intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        //setting the repeating alarm that will be fired every day
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        }else{
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),AlarmManager.INTERVAL_HALF_HOUR ,pendingIntent);
        }

        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
    }
    private String updateTimeText(Calendar c) {
        String timeText = "";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        return timeText;
    }
}