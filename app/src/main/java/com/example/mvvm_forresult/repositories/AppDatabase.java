package com.example.mvvm_forresult.repositories;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mvvm_forresult.interfaces.TasksDao;
import com.example.mvvm_forresult.models.TaskModel;

@Database(entities = {TaskModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TasksDao tasksDao();
}