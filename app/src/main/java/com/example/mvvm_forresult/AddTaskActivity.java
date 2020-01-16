package com.example.mvvm_forresult;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.mvvm_forresult.interfaces.TasksDao;
import com.example.mvvm_forresult.models.TaskModel;
import com.example.mvvm_forresult.viewmodels.TaskViewModel;

import java.time.LocalDate;

public class AddTaskActivity extends AppCompatActivity {

    private EditText mDescription;
    private Button mAddTask;
    private EditText mDate;
    private TaskViewModel mModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);
        mDescription = findViewById(R.id.description_input);
        mDate = findViewById(R.id.date_input);
        mAddTask = findViewById(R.id.add_task_btn);

        mModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        mAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String localDate = mDate.getText().toString().trim();
                String desc = mDescription.getText().toString().trim();
                final TaskModel taskModel1 = new TaskModel(desc, localDate);
                Handler uiHandler = new Handler(Looper.getMainLooper());

                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        MainActivity.mAppDb.tasksDao().insert(taskModel1);
                    }
                });


                Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}




