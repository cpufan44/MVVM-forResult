package com.example.mvvm_forresult;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm_forresult.R;
import com.example.mvvm_forresult.models.TaskModel;
import com.example.mvvm_forresult.viewmodels.TaskViewModel;

import java.util.ArrayList;
import java.util.List;

public class ItemActivity extends AppCompatActivity {

    private TextView mDescr;
    private TextView mDate;
    private TaskViewModel mTaskView;
    private List<TaskModel> tasksList;
    private Button mDelete;
    private RelativeLayout mRelative;
    private ConstraintLayout mConstraintLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);
        mDescr = findViewById(R.id.task_description);
        mDate = findViewById(R.id.task_date);
        mDelete = findViewById(R.id.btn_delete);
        mRelative = findViewById(R.id.relative_layout);
        mConstraintLayout = findViewById(R.id.constraint_lt);

        tasksList = new ArrayList<>();

        mRelative.setMinimumHeight(3000);


        mRelative.setBackground(ContextCompat.getDrawable(this, R.drawable.noboarder));

        mDelete.setVisibility(View.VISIBLE);

        Bundle extras = getIntent().getExtras();
        int position = extras.getInt("Position");

        mTaskView = ViewModelProviders.of(this).get(TaskViewModel.class);

        mTaskView.getmTasks().observe(this, taskModels -> {
            Toast.makeText(this, "loaded", Toast.LENGTH_LONG).show();
            tasksList.clear();
            tasksList.addAll(taskModels);
            mDate.setText(tasksList.get(position).getDate());
            mDescr.setText(tasksList.get(position).getDescription());
            mDelete.setOnClickListener(v -> new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    MainActivity.mAppDb.tasksDao().delete(tasksList.get(position));

                    return null;
                }
            }.execute());
        });
    }
}
