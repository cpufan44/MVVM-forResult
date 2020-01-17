package com.example.mvvm_forresult;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProviders;

import com.example.mvvm_forresult.interfaces.TasksDao;
import com.example.mvvm_forresult.models.TaskModel;
import com.example.mvvm_forresult.viewmodels.TaskViewModel;

import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EditText mDescription;
    private Button mAddTask;
    private Button mDate;
    private TaskViewModel mModel;
    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;
    private String localDate;
    private LocalDateTime date;
    private AlarmManager alarms;
    private PendingIntent operation;
    private final String CHANNEL_ID = "tasks_notifications";
    private final int NOTIFICATION_ID = 001;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);
        mDescription = findViewById(R.id.description_input);
        mDate = findViewById(R.id.date_input);
        mAddTask = findViewById(R.id.add_task_btn);
        date = LocalDateTime.now();

        mModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        alarms = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        Reciever receiver = new Reciever();
        IntentFilter filter = new IntentFilter("ALARM_ACTION");
        registerReceiver(receiver, filter);

        Intent intent1 = new Intent("ALARM_ACTION");
        intent1.putExtra("param", "My scheduled action");
        operation = PendingIntent.getBroadcast(this, 0, intent1, 0);
        // I choose 3s after the launch of my application


        mDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();

            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this, AddTaskActivity.this,
                    year, month, day);
            datePickerDialog.show();
        });


        mAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String desc = mDescription.getText().toString().trim();
                TaskModel taskModel1 = new TaskModel(desc, localDate);

                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... voids) {
                        List<TaskModel> tasksList = new ArrayList<>();
                        tasksList.add(taskModel1);

                        for (TaskModel task : tasksList) {
                            MainActivity.mAppDb.tasksDao().insert(task);
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        if(localDate == null){
                            Toast.makeText(AddTaskActivity.this, "Please choose date", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }.execute();
            }
        });


    }

    public void displayNotification(View view, String description){
        Notification builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_light)
                .setContentTitle("You have task to do")
                .setContentText(""+description)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        createNotificationChannel();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,builder);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_ID;
            String description = "Task notification channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        yearFinal = year;
        monthFinal = month;
        dayFinal = dayOfMonth;

        Calendar c = Calendar.getInstance();

        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddTaskActivity.this, AddTaskActivity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hourFinal = hourOfDay;
        minuteFinal = minute;

        localDate = dayFinal + "/" + monthFinal + "/" + yearFinal + "-" + hourFinal + ":" + minuteFinal;



        alarms.set(AlarmManager.RTC_WAKEUP, date.getLong(ChronoField.NANO_OF_SECOND), operation);

    }
}





