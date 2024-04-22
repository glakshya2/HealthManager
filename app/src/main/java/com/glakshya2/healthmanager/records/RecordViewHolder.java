package com.glakshya2.healthmanager.records;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.glakshya2.healthmanager.R;

public class RecordViewHolder extends RecyclerView.ViewHolder {
    TextView nameTv, dateTv, descTv;
    ImageButton viewButton;

    public RecordViewHolder(@NonNull View itemView) {
        super(itemView);
        nameTv = itemView.findViewById(R.id.record_datacard_name);
        dateTv = itemView.findViewById(R.id.record_datacard_date);
        descTv = itemView.findViewById(R.id.record_datacard_desc);
        viewButton = itemView.findViewById(R.id.imageButton);
    }
}
