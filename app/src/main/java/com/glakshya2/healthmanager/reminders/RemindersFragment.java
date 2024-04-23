package com.glakshya2.healthmanager.reminders;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.glakshya2.healthmanager.R;
import com.glakshya2.healthmanager.schema.Reminder;
import com.glakshya2.healthmanager.schema.ReminderList;
import com.glakshya2.healthmanager.utils.ChildToHost;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RemindersFragment extends Fragment {

    ChildToHost childToHost;
    FloatingActionButton addReminder;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ReminderList reminderList;
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
        recyclerView = view.findViewById(R.id.remindersRecyclerView);
        addReminder = view.findViewById(R.id.addReminderButton);
        addReminder.setOnClickListener(v -> childToHost.setFragment(new AddReminderFragment()));

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstance) {
        if (this.reminderList != null) {
            ReminderHelper.reminderArrayList = this.reminderList.getReminderArrayList();
        } else {
            ReminderHelper.reminderArrayList = new ArrayList<Reminder>();
        }
        adapter = new ReminderAdapter(getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
    }

    public void receiveData(ReminderList reminderList) {
        if (reminderList != null) {
            this.reminderList = reminderList;
        } else {
            this.reminderList = new ReminderList(new ArrayList<Reminder>());
        }
    }
}