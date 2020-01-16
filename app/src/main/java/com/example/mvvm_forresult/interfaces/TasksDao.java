package com.example.mvvm_forresult.interfaces;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mvvm_forresult.models.TaskModel;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface TasksDao {
    @Query("SELECT * FROM taskmodel")
    LiveData<List<TaskModel>> getAll();

    @Query("SELECT * FROM taskmodel WHERE uid IN (:userIds)")
    List<TaskModel> loadAllByIds(int[] userIds);



    @Insert
    void insert(TaskModel task);

    @Delete
    void delete(TaskModel taskModel);
}
