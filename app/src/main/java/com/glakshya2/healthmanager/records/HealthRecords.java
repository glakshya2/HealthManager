package com.glakshya2.healthmanager.records;

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


public class HealthRecords extends Fragment {


    ChildToHost childToHost;
    Button addButton;

    public HealthRecords() {
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

        addButton = view.findViewById(R.id.addHealthRecord);
        addButton.setOnClickListener(v -> childToHost.transferData(new AddHealthRecords()));
        return view;
    }
}