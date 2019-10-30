package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class UserMealWithExceed {
    private final LocalDateTime dateTime;

    private final String description;

    private  int calories;

    private  boolean exceed;

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }



    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;

    }

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, Integer integer, Integer maxColor) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        if(integer> maxColor)
        this.exceed = true;
        else
            this.exceed = false;
    }


    public boolean isExceed() {
        return exceed;
    }

    public void setExceed(boolean exceed) {
        this.exceed = exceed;
    }

    public LocalTime getLocalTime() {
        return this.dateTime.toLocalTime();
    }


    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }


    public LocalDate getLocalDate(){
        return getDateTime().toLocalDate();
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }



}
