package com.example.mvvm_forresult.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm_forresult.R;
import com.example.mvvm_forresult.models.TaskModel;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TaskModel> mTasks;
    private Context mContext;

    public RecyclerAdapter(List<TaskModel> mTasks, Context mContext) {
        this.mTasks = mTasks;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        ((ViewHolder) holder).mDescription.setText(mTasks.get(i).getDescription());
        ((ViewHolder) holder).mDate.setText(mTasks.get(i).getDate().toString());
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mDescription;
        private TextView mDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mDescription = itemView.findViewById(R.id.task_description);
            mDate = itemView.findViewById(R.id.task_date);
        }
    }
}
