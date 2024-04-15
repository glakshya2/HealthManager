package com.glakshya2.healthmanager.schema;

public class User {
    private Profile profile;

    public User(Profile profile) {
        this.profile = profile;
    }

    public User() {
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
