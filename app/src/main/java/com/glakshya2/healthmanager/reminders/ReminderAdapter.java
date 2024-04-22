package com.glakshya2.healthmanager.reminders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.glakshya2.healthmanager.R;
import com.glakshya2.healthmanager.schema.Reminder;
import com.glakshya2.healthmanager.utils.ChildToHost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderViewHolder> {
    @NonNull
    Context context;

    ArrayList<Reminder> reminderArrayList;
    ChildToHost childToHost;

    public ReminderAdapter(@NonNull Context context) {
        this.context = context;
        this.reminderArrayList = ReminderHelper.reminderArrayList;

    }

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reminder_item, parent, false);
        return new ReminderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        holder.title.setText(reminderArrayList.get(position).getTitle());
        holder.toggleButton.setText(reminderArrayList.get(position).isActive() ? "ON" : "OFF");
        holder.time.setText(reminderArrayList.get(position).getDateTime());
        holder.toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ReminderHelper.reminderArrayList.get(position).setActive(isChecked);
            buttonView.setText(isChecked ? "ON" : "OFF");
            saveReminderToDatabase(ReminderHelper.reminderArrayList.get(position), position);
            notifyDataSetChanged();
        });
    }

    private void saveReminderToDatabase(Reminder reminder, int position) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference remindersRef = database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        remindersRef.child("reminderList").child("reminderArrayList").child(String.valueOf(position)).child("active").setValue(reminder.isActive());
        if (reminder.isActive()) {
            setAlarm(reminder);
        } else {
            cancelAlarm(reminder); // Use title as unique identifier
        }
    }

    private void setAlarm(Reminder reminder) {
        // ... Parse reminder.getDateTime() into a Calendar object (similar to previous example) ...
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {
            Date reminderDate = dateFormat.parse(reminder.getDateTime());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(reminderDate);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(context, ReminderBroadcastReceiver.class);
            intent.putExtra("title", reminder.getTitle());
            intent.putExtra("description", reminder.getDescription());

            // Create a unique request code using title and time
            String reminderIdentifier = reminder.getTitle() + "_" + reminder.getDateTime();
            int requestCode = reminderIdentifier.hashCode(); // Generate a hash code

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE);

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void cancelAlarm(Reminder reminder) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (reminder.getTitle() + "_" + reminder.getDateTime()).hashCode(), intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(pendingIntent);
    }

    @Override
    public int getItemCount() {
        return reminderArrayList.size();
    }
}
