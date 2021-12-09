/*
 * Created by Team 10 on 2021.12.08
 * Copyright © 2021 Team 10. All rights reserved.
 */
package edu.vt.EntityBeans;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/*
The @Entity annotation designates this class as a JPA Entity POJO class
representing the UserWorkoutConsumed table in the HealthTechDB database.
 */
@Entity
// Name of the database table represented
@Table(name = "UserWorkoutDone")
public class UserWorkoutDone {
    /*
    ========================================================
    Instance variables representing the attributes (columns)
    of the UserWorkoutDone table in the HealthTechDB database.

   CREATE TABLE UserWorkoutDone
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
    user_workout_id INT UNSIGNED,
    duration INT NOT NULL,
    calories INT NOT NULL,
    date DATE NOT NULL,
    FOREIGN KEY (user_workout_id) REFERENCES UserWorkout(id) ON DELETE CASCADE
);

    ========================================================
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    //UserWorkout-ID -> Foreign Key
    @JoinColumn(name = "user_workout_id", referencedColumnName = "id")
    @ManyToOne
    private UserWorkout workoutId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "duration")
    private Integer duration;

    @Basic(optional = false)
    @NotNull
    @Column(name = "calories")
    private Integer calories;

    @NotNull
    @Column(name = "date")
    @Basic(optional = false)
    private Date date;

    /*
    ==========================================================================
    Class constructors for instantiating a UserWorkoutDone entity object to
    represent a row in the UserWorkoutDone table in the HealthTechDB database.
    ==========================================================================
     */
    public UserWorkoutDone() {
    }

    public UserWorkoutDone(Integer id) {
        this.id = id;
    }

    public UserWorkoutDone(Integer id, UserWorkout workoutId, Integer duration,
                           Integer calories, Date date) {
        this.id = id;
        this.workoutId = workoutId;
        this.duration = duration;
        this.calories = calories;
        this.date = date;
    }

    /*
    ======================================================
    Getter and Setter methods for the attributes (columns)
    of the UserWorkoutDone table in the HealthTechDB database.
    ======================================================
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserWorkout getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(UserWorkout workoutId) {
        this.workoutId = workoutId;
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

    // Generate and return a hash code value for the object with database primary key id
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /*
     Checks if the UserWorkoutDone object identified by 'object' is the same as the UserWorkoutDone object identified by 'id'
     Parameter object = UserWorkoutDone object identified by 'object'
     Returns True if the UserWorkoutDone 'object' and 'id' are the same; otherwise, return False
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UserWorkoutDone)) {
            return false;
        }
        UserWorkoutDone other = (UserWorkoutDone) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    // Return String representation of database primary key id
    @Override
    public String toString() {
        return id.toString();
    }
}
