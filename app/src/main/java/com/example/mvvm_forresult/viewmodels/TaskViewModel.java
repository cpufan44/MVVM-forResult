package com.example.mvvm_forresult.viewmodels;

import android.content.ContextWrapper;
import android.os.AsyncTask;

import androidx.annotation.ContentView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.mvvm_forresult.models.TaskModel;
import com.example.mvvm_forresult.repositories.AppDatabase;
import com.example.mvvm_forresult.repositories.TasksRepo;

import java.util.List;

public class TaskViewModel extends ViewModel {

    private LiveData<List<TaskModel>> mTasks;
    private TasksRepo mRepo;
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();

    public TaskViewModel() {
        mTasks = TasksRepo.getInstance().getTasks();
    }



//    public void addNewValue(final TaskModel taskModel) {
//
//        mIsUpdating.setValue(true);
//
//        new AsyncTask<Void, Void, Void>() {
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//                List<TaskModel> tasks = mTasks.getValue();
//                tasks.add(taskModel);
//                mTasks.postValue(tasks);
//                mIsUpdating.setValue(false);
//            }
//
//            @Override
//            protected Void doInBackground(Void... voids) {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//        }.execute();
//    }




    public LiveData<List<TaskModel>> getmTasks() {
        return mTasks;
    }

    public MutableLiveData<Boolean> getmIsUpdating() {
        return mIsUpdating;
    }
}
