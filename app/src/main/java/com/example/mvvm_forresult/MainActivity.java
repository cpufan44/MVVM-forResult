package com.example.mvvm_forresult;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvvm_forresult.adapters.RecyclerAdapter;
import com.example.mvvm_forresult.models.TaskModel;
import com.example.mvvm_forresult.repositories.AppDatabase;
import com.example.mvvm_forresult.viewmodels.TaskViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mAdd;
    private RecyclerView mRecycler;
    private ProgressBar mProgress;
    private TaskViewModel mModel;
    private RecyclerAdapter mAdapter;
    public static AppDatabase mAppDb;


    private List<TaskModel> tasksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdd = findViewById(R.id.floating_btn);
        mRecycler = findViewById(R.id.tasks_recycler);
        mProgress = findViewById(R.id.progress_tasks);
        mAppDb = Room.databaseBuilder(MainActivity.this, AppDatabase.class, "tasks-database").build();



        mRecycler.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                Toast.makeText(MainActivity.this, "Touched", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


        mModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        tasksList = new ArrayList<>();

        mAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });

        mModel.getmTasks().observe(this, taskModels -> {
            Toast.makeText(this, "loaded", Toast.LENGTH_LONG).show();
            tasksList.clear();
            tasksList.addAll(taskModels);
            mAdapter.notifyDataSetChanged();
        });


//        mModel.getmIsUpdating().observe(this, aBoolean -> {
//
//            if (aBoolean) {
//
//                showProgressBar();
//            } else {
//                hideProgressBar();
//                mRecycler.smoothScrollToPosition(mModel.getmTasks().getValue().size() - 1);
//            }
//        });

        initRecyclerView();

    }




    public void initRecyclerView() {

        mAdapter = new RecyclerAdapter(MainActivity.this, tasksList, MainActivity.this);
        RecyclerView.LayoutManager linear = new LinearLayoutManager(MainActivity.this);
        mRecycler.setLayoutManager(linear);
        mRecycler.setAdapter(mAdapter);
    }

    public void showProgressBar() {
        mProgress.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        mProgress.setVisibility(View.GONE);
    }
}
