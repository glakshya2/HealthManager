package com.glakshya2.healthmanager.records;

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
import com.glakshya2.healthmanager.schema.History;
import com.glakshya2.healthmanager.schema.Record;
import com.glakshya2.healthmanager.utils.ChildToHost;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class HealthRecordsFragment extends Fragment {

    ChildToHost childToHost;
    FloatingActionButton addButton;
    RecyclerView recyclerView;
    History history;
    RecyclerView.Adapter adapter;
    public HealthRecordsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_health_records, container, false);
        addButton = view.findViewById(R.id.records_addHealthRecord);
        recyclerView = view.findViewById(R.id.records_recyclerView);
        addButton.setOnClickListener(v -> childToHost.setFragment(new AddHealthRecordsFragment()));
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstance) {
        if (this.history != null) {
            RecordsHelper.recordArrayList = this.history.getRecordArrayList();
        } else {
            RecordsHelper.recordArrayList = new ArrayList<Record>();
        }
        adapter = new RecordAdapter(getActivity().getApplicationContext(), childToHost);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
    }

    public void receiveData(History history) {
        if (history != null) {
            this.history = history;
        } else {
            this.history = new History(new ArrayList<Record>());
        }
    }
}