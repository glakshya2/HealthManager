package com.glakshya2.healthmanager.profile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.glakshya2.healthmanager.ChildToHost;
import com.glakshya2.healthmanager.R;
import com.glakshya2.healthmanager.schema.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ProfileFragment extends Fragment {

    Button saveBtn;
    TextView displayNameTv, emailTv;
    EditText ageEt, weightEt;
    ImageView profileIv;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    ChildToHost childToHost;
    Profile profile;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        childToHost = (ChildToHost) context;
    }

    public void receiveData(Profile profile) {
        if (profile != null) {
            this.profile = profile;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        saveBtn = view.findViewById(R.id.saveBtn);
        displayNameTv = view.findViewById(R.id.displayNameTV);
        emailTv = view.findViewById(R.id.emailTv);
        ageEt = view.findViewById(R.id.ageEt);
        weightEt = view.findViewById(R.id.weightEt);
        profileIv = view.findViewById(R.id.profileIv);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        displayNameTv.setText(Objects.requireNonNull(firebaseUser).getDisplayName());
        emailTv.setText(firebaseUser.getEmail());
        Uri profileUri = firebaseUser.getPhotoUrl();

        Picasso.get().load(profileUri).resize(300, 300).transform(new CropCircleTransformation()).into(profileIv);

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseUser.getUid());

        saveBtn.setOnClickListener(v -> {
            childToHost.transferData(new Profile(Integer.parseInt(ageEt.getText().toString()), Float.parseFloat(weightEt.getText().toString())));
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
        if (profile != null) {
            ageEt.setText(String.valueOf(profile.getAge()));
            weightEt.setText(String.valueOf(profile.getWeight()));
        }
    }
}

