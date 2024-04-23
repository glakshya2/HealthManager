package com.glakshya2.healthmanager.records;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.glakshya2.healthmanager.R;
import com.glakshya2.healthmanager.schema.Files;
import com.glakshya2.healthmanager.schema.Record;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;

public class ViewRecord extends Fragment {
    Record record;
    TextView nameTv, typeTv, descriptionTv, dateTv;
    LinearLayout filesLayout;
    FirebaseStorage firebaseStorage;

    public ViewRecord() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_record, container, false);
        nameTv = view.findViewById(R.id.view_record_name);
        typeTv = view.findViewById(R.id.view_record_type);
        descriptionTv = view.findViewById(R.id.view_record_description);
        dateTv = view.findViewById(R.id.view_record_date);

        filesLayout = view.findViewById(R.id.view_record_linearLayout);
        Bundle args = getArguments();
        if (args != null) {
            int position = args.getInt("position", -1);
            if (position != -1) {
                record = RecordsHelper.recordArrayList.get(position);
                // Use record details to populate your view
            }
        }
        firebaseStorage = FirebaseStorage.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameTv.setText(record.getName());
        typeTv.setText(record.getType());
        descriptionTv.setText(record.getDescription());
        dateTv.setText(record.getDate());
        for (Files file : record.getFiles()) {
            TableRow row = (TableRow) LayoutInflater.from(getContext()).inflate(R.layout.file_row, null);
            TextView fileNameTv = row.findViewById(R.id.file_name);
            ImageButton viewButton = row.findViewById(R.id.imageButton);
            ImageButton downloadButton = row.findViewById(R.id.download_button);

            fileNameTv.setText(file.getName());
            downloadButton.setOnClickListener(v -> downloadFile(file.getUri(), file.getName()));

            viewButton.setOnClickListener(v -> {
                String fileUri = file.getUri();
                String fileExtension = MimeTypeMap.getFileExtensionFromUrl(fileUri);
                if (isImageFile(fileExtension)) {
                    viewImage(fileUri);
                } else if (fileExtension.equalsIgnoreCase("pdf")) {
                    viewPdf(fileUri);
                } else {
                    Toast.makeText(getContext(), "Unsupported file type", Toast.LENGTH_SHORT).show();
                }
            });
            filesLayout.addView(row);
        }
    }

    private void downloadFile(String downloadUrl, String fileName) {
        Uri downloadUri = Uri.parse(downloadUrl);
        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(downloadUrl);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
        DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI)
                .setAllowedOverRoaming(true)
                .setTitle(fileName)
                .setMimeType(mimeType)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, File.separator + fileName);

        downloadManager.enqueue(request);
    }

    private boolean isImageFile(String fileExtension) {
        return fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("jpeg") ||
                fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("gif");
    }

    private void viewImage(String imageUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(imageUrl), "image/*");
        startActivity(intent);
    }

    private void viewPdf(String pdfUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(pdfUrl), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "No PDF viewer app found", Toast.LENGTH_SHORT).show();
        }
    }
}