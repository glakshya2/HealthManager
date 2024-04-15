package com.glakshya2.healthmanager;

import androidx.fragment.app.Fragment;

import com.glakshya2.healthmanager.schema.Profile;

public interface ChildToHost {
    void transferData(Fragment fragment);

    void transferData(Profile profile);
}
