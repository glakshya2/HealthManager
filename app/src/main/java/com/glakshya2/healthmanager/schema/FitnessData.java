package com.glakshya2.healthmanager.schema;

public class FitnessData {
    public static int steps;
    public static float calories;
    public static float distance;
    public static int moveMinutes;

    public FitnessData() {
    }

    public FitnessData(int steps, float calories, float distance, int moveMinutes) {
        FitnessData.steps = steps;
        FitnessData.calories = calories;
        FitnessData.distance = distance;
        FitnessData.moveMinutes = moveMinutes;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        FitnessData.steps = steps;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        FitnessData.calories = calories;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        FitnessData.distance = distance;
    }

    public int getMoveMinutes() {
        return moveMinutes;
    }

    public void setMoveMinutes(int moveMinutes) {
        FitnessData.moveMinutes = moveMinutes;
    }
}
