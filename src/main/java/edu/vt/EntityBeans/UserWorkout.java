package edu.vt.EntityBeans;

import java.io.Serializable;
import java.util.Date;

public class UserWorkout implements Serializable {

    private Integer id;
    private User user;
    private Workout workout;
    private Integer duration;
    private Integer calories;
    private Date date;


    public UserWorkout() {
    }

    public UserWorkout( User user, Workout workout, Integer duration, Date date) {
        this.user = user;
        this.workout = workout;
        this.duration = duration;
        this.calories = Math.round(workout.getBurnRate()* duration);
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
