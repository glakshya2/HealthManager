package com.glakshya2.healthmanager.profile;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.glakshya2.healthmanager.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class Profile extends Fragment {

    Button saveBtn;
    TextView displayNameTv, emailTv;
    EditText ageEt, weightEt;
    ImageView profileIv;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    public Profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        saveBtn = view.findViewById(R.id.saveBtn);
        displayNameTv = view.findViewById(R.id.displayNameTV);
        emailTv = view.findViewById(R.id.emailTv);
        ageEt = view.findViewById(R.id.ageEt);
        weightEt = view.findViewById(R.id.weightEt);
        profileIv = view.findViewById(R.id.profileIv);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        displayNameTv.setText(firebaseUser.getDisplayName());
        emailTv.setText(firebaseUser.getEmail());
        Uri profileUri = firebaseUser.getPhotoUrl();

        Picasso.get().load(profileUri).resize(300, 300).transform(new CropCircleTransformation()).into(profileIv);
        return view;
    }
}