package com.glakshya2.healthmanager.reminders;

import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.glakshya2.healthmanager.R;

public class ReminderViewHolder extends RecyclerView.ViewHolder {
    TextView title, time;
    ToggleButton toggleButton;


    public ReminderViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.reminderItemTitleTv);
        time = itemView.findViewById(R.id.reminderItemTimeTv);
        toggleButton = itemView.findViewById(R.id.reminderItemToggleButton);
    }
}
