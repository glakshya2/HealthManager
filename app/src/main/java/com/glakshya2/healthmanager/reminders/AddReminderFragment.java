package com.glakshya2.healthmanager.reminders;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.glakshya2.healthmanager.R;
import com.glakshya2.healthmanager.schema.Reminder;
import com.glakshya2.healthmanager.schema.ReminderList;
import com.glakshya2.healthmanager.utils.ChildToHost;
import com.glakshya2.healthmanager.utils.DatePickerFragment;
import com.glakshya2.healthmanager.utils.TimePickerFragment;


public class AddReminderFragment extends Fragment {

    public AddReminderFragment() {
        // Required empty public constructor
    }

    EditText titleEt, descriptionEt;
    TextView dateTv, timeTv;
    Switch recurringSwitch;
    Spinner recurrenceTypeSpinner;
    Button saveButton;
    ChildToHost childToHost;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        childToHost = (ChildToHost) context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_reminder, container, false);

        titleEt = view.findViewById(R.id.addReminderTitleEt);
        descriptionEt = view.findViewById(R.id.addReminderDescriptionEt);
        dateTv = view.findViewById(R.id.addReminderDateTv);
        timeTv = view.findViewById(R.id.addReminderTimeTv);
        recurringSwitch = view.findViewById(R.id.addReminderRecurringSwitch);
        recurrenceTypeSpinner = view.findViewById(R.id.addReminderRecurrenceTypeSpinner);
        saveButton = view.findViewById(R.id.addReminderSaveButton);
        dateTv.setOnClickListener(v -> showDatePicker());
        timeTv.setOnClickListener(v -> showTimePicker());
        recurrenceTypeSpinner.setVisibility(View.GONE);
        recurringSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                recurrenceTypeSpinner.setVisibility(View.VISIBLE);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                        getContext(),
                        R.array.recurrence_types,
                        android.R.layout.simple_spinner_item
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                recurrenceTypeSpinner.setAdapter(adapter);
            } else {
                recurrenceTypeSpinner.setVisibility(View.GONE);
            }
        });

        saveButton.setOnClickListener(v -> {
            String title = titleEt.getText().toString();
            String description = descriptionEt.getText().toString();
            String dateTime = dateTv.getText().toString() + " " + timeTv.getText().toString();
            boolean isRecurring = recurringSwitch.isChecked();
            String recurrenceType = isRecurring ? recurrenceTypeSpinner.getSelectedItem().toString() : null;
            boolean isActive = true;

            ReminderHelper.reminderArrayList.add(new Reminder(title, description, dateTime, isRecurring, recurrenceType, isActive));
            childToHost.transferData(new ReminderList(ReminderHelper.reminderArrayList));

        });
        return view;
    }

    private void showDatePicker() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setListener((year, month, dayOfMonth) -> {
            String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
            dateTv.setText(selectedDate);
        });
        datePickerFragment.show(getChildFragmentManager(), "datePicker");
    }

    private void showTimePicker() {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setListener((view, hourOfDay, minute) -> {
            String selectedTime = String.format("%02d:%02d", hourOfDay, minute);
            timeTv.setText(selectedTime);
        });
        timePickerFragment.show(getChildFragmentManager(), "timePicker");
    }
}