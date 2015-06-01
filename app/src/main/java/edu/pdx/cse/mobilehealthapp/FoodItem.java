package edu.pdx.cse.mobilehealthapp;

/**
 * Created by solus on 5/30/2015.
 */
public class FoodItem {

    private String name;
    private int calories;
    private int id;

    public FoodItem(String name, int calories, int id) {
        this.name = name;
        this.calories = calories;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
