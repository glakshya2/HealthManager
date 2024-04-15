package com.glakshya2.healthmanager.schema;

public class Profile {
    private int age;
    private float weight;

    public Profile(int age, float weight) {
        this.age = age;
        this.weight = weight;
    }

    public Profile() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
