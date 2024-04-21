package com.glakshya2.healthmanager;

import androidx.fragment.app.Fragment;

import com.glakshya2.healthmanager.schema.Nutrition;
import com.glakshya2.healthmanager.schema.Profile;

public interface ChildToHost {
    void setFragment(Fragment fragment);

    void transferData(Profile profile);

    void transferData(Nutrition nutrition);
}
