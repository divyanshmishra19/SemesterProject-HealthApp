package edu.vt.EntityBeans;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
// Name of the database table represented
@Table(name = "UserWorkoutDone")
public class UserWorkoutDone {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    //User-ID -> Foreign Key
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User userId;

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

    public UserWorkoutDone() {
    }

    public UserWorkoutDone(Integer id) {
        this.id = id;
    }

    public UserWorkoutDone(Integer id, User userId, UserWorkout workoutId, Integer duration,
                           Integer calories, Date date) {
        this.id = id;
        this.userId = userId;
        this.workoutId = workoutId;
        this.duration = duration;
        this.calories = calories;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
     Checks if the User object identified by 'object' is the same as the User object identified by 'id'
     Parameter object = User object identified by 'object'
     Returns True if the User 'object' and 'id' are the same; otherwise, return False
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
