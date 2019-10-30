package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class UserMeal {
    private final LocalDateTime dateTime;
    private int id;
    private final String description;

    private final int calories;



    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LocalDate getLocalDate(){
        return getDateTime().toLocalDate();
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalTime getLocalTime() {
        return this.dateTime.toLocalTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
