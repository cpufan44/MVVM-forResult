package com.example.mvvm_forresult.repositories;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;
import androidx.work.OneTimeWorkRequest;

import com.example.mvvm_forresult.MainActivity;
import com.example.mvvm_forresult.adapters.RecyclerAdapter;
import com.example.mvvm_forresult.models.TaskModel;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TasksRepo {

    private static TasksRepo instance;
    private List<TaskModel> dataset = new ArrayList<>();

    public static TasksRepo getInstance() {
        if (instance == null) {
            instance = new TasksRepo();
        }
        return instance;
    }

    public LiveData<List<TaskModel>> getTasks() {
        setTasks();
//        MutableLiveData<List<TaskModel>> data = new MutableLiveData<>();
//        data.setValue(dataset);

        return MainActivity.mAppDb.tasksDao().getAll();
    }


    public void setTasks() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

            }

            @Override
            protected Void doInBackground(Void... voids) {
                List<TaskModel> tasksList = new ArrayList<>();
                tasksList.add(new TaskModel("New task", "2020-01-01"));
                tasksList.add(new TaskModel("New task", "2020-01-01"));
                tasksList.add(new TaskModel("New task", "2020-01-01"));
                tasksList.add(new TaskModel("New task", "2020-01-01"));
                tasksList.add(new TaskModel("New task", "2020-01-01"));

                for (TaskModel task : tasksList) {
                    MainActivity.mAppDb.tasksDao().insert(task);
                }
                return null;
            }

        }.execute();
    }
}
