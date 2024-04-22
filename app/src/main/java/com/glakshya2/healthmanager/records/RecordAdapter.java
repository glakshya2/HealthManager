package com.glakshya2.healthmanager.records;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.glakshya2.healthmanager.R;
import com.glakshya2.healthmanager.schema.Record;
import com.glakshya2.healthmanager.utils.ChildToHost;

import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter<RecordViewHolder> implements View.OnClickListener {

    @NonNull
    Context context;

    ArrayList<Record> recordArrayList;
    ChildToHost childToHost;

    public RecordAdapter(@NonNull Context context, ChildToHost childToHost) {
        this.context = context;
        recordArrayList = RecordsHelper.recordArrayList;
        this.childToHost = childToHost;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.record_datacard, parent, false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        holder.nameTv.setText(recordArrayList.get(position).getName());
        holder.dateTv.setText(recordArrayList.get(position).getDate());
        holder.descTv.setText(recordArrayList.get(position).getDescription());
        holder.viewButton.setOnClickListener(this);
        holder.viewButton.setTag(position);
    }

    @Override
    public int getItemCount() {
        return recordArrayList.size();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag(); // Retrieve the position
        ViewRecord viewRecordFragment = new ViewRecord();
        Bundle args = new Bundle();
        args.putInt("position", position);
        viewRecordFragment.setArguments(args);
        childToHost.setFragment(viewRecordFragment);
    }
}
