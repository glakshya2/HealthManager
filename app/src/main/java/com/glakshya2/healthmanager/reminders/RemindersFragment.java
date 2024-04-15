package com.glakshya2.healthmanager.reminders;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.glakshya2.healthmanager.ChildToHost;
import com.glakshya2.healthmanager.R;

public class RemindersFragment extends Fragment {

    ChildToHost childToHost;
    Button addReminder;

    public RemindersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        childToHost = (ChildToHost) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reminders, container, false);
        addReminder = view.findViewById(R.id.button5);
        addReminder.setOnClickListener(v -> childToHost.transferData(new AddReminderFragment()));
        return view;
    }
}