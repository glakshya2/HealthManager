package com.glakshya2.healthmanager.records;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.glakshya2.healthmanager.R;
import com.glakshya2.healthmanager.schema.Files;
import com.glakshya2.healthmanager.schema.History;
import com.glakshya2.healthmanager.schema.Record;
import com.glakshya2.healthmanager.utils.ChildToHost;
import com.glakshya2.healthmanager.utils.DatePickerFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class AddHealthRecordsFragment extends Fragment implements DatePickerFragment.DateSetListener {
    EditText typeEt, nameEt, descEt;
    TextView dateTv;
    Button addFileBtn, saveBtn;
    ProgressBar progressBar;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    HashMap<String, Uri> files = new HashMap<String, Uri>();
    ArrayList<Files> filesArrayList = new ArrayList<Files>();
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    ChildToHost childToHost;
    public AddHealthRecordsFragment() {
        // Required empty public constructor
    }

    ActivityResultLauncher<Intent> getFile = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        Intent data = result.getData();
        if (data != null) {
            Uri fileUri = data.getData();
            String fileName = getFileNameFromUri(fileUri);
            files.put(fileName, fileUri);
        }
    });

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        childToHost = (ChildToHost) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_health_records, container, false);
        typeEt = view.findViewById(R.id.addRecord_typeEt);
        nameEt = view.findViewById(R.id.addRecord_nameEt);
        dateTv = view.findViewById(R.id.addRecord_dateTv);
        descEt = view.findViewById(R.id.addRecord_descEt);
        addFileBtn = view.findViewById(R.id.addRecord_addFileBtn);
        saveBtn = view.findViewById(R.id.addRecord_saveBtn);
        progressBar = view.findViewById(R.id.progressBar2);

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        calendar = Calendar.getInstance();

        dateTv.setOnClickListener(v -> {
            showDatePicker();
        });

        addFileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            String[] mimeTypes = {"image/*", "application/pdf"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            getFile.launch(intent);
        });

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("user_files");

        saveBtn.setOnClickListener(v -> {
            int totalFiles = files.size();
            final int[] currentUploaded = {0};
            progressBar.setVisibility(View.VISIBLE);
            files.forEach((name, uri) -> {
                StorageReference fileReference = storageReference.child(name);
                UploadTask uploadTask = fileReference.putFile(uri);
                uploadTask.addOnProgressListener(taskSnapshot -> {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    progressBar.setProgress((int) progress);
                });
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        return fileReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        filesArrayList.add(new Files(name, task.getResult().toString()));
                        currentUploaded[0]++;
                        if (currentUploaded[0] == totalFiles) {
                            uploadToRealTime();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            });

        });
        return view;
    }

    void uploadToRealTime() {
        RecordsHelper.recordArrayList.add(new Record(
                nameEt.getText().toString(),
                typeEt.getText().toString(),
                dateTv.getText().toString(),
                descEt.getText().toString(),
                filesArrayList
        ));
        childToHost.transferData(new History(RecordsHelper.recordArrayList));
    }

    private String getFileNameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;

    }

    private void showDatePicker() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setListener(this);
        datePickerFragment.show(getChildFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        dateTv.setText(simpleDateFormat.format(calendar.getTime()));
    }
}