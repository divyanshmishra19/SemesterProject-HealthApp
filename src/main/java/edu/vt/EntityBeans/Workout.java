package edu.vt.EntityBeans;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
// Name of the database table represented
@Table(name = "Workout")
public class Workout implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    Primary Key id is auto generated by the system as an Integer value
    starting with 1 and incremented by 1, i.e., 1,2,3,...
    A deleted entity object's primary key number is not reused.
     */
    // id INT UNSIGNED NOT NULL AUTO_INCREMENT
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "category")
    private String category;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "youtube_tutorial_video_id")
    private String youtubeTutorialVideoId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "burn_rate")
    private Double burnRate;

    /*
    ===============================================================
    Class constructors for instantiating a Workout entity object to
    represent a row in the Workout table in the HealthTechDB database.
    ===============================================================
     */

    public Workout() {
    }

    public Workout(Integer id) {
        this.id = id;
    }

    public Workout(Integer id, String name, String category, String youtubeTutorialVideoId, Double burnRate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.youtubeTutorialVideoId = youtubeTutorialVideoId;
        this.burnRate = burnRate;
    }

    /*
    ======================================================
     Getter and Setter methods for the attributes (columns)
     of the Workout table in the HealthTechDB database.
    ======================================================
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getYoutubeTutorialVideoId() {
        return youtubeTutorialVideoId;
    }

    public void setYoutubeTutorialVideoId(String youtubeTutorialVideoId) {
        this.youtubeTutorialVideoId = youtubeTutorialVideoId;
    }

    public Double getBurnRate() {
        return burnRate;
    }

    public void setBurnRate(Double burnRate) {
        this.burnRate = burnRate;
    }

    /*
    ================================
    Instance Methods Used Internally
    ================================
     */

    // Generate and return a hash code value for the object with database primary key id
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /*
     Checks if the Workout object identified by 'object' is the same as the Workout object identified by 'id'
     Parameter object = Workout object identified by 'object'
     Returns True if the Workout 'object' and 'id' are the same; otherwise, return False
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Workout)) {
            return false;
        }
        Workout other = (Workout) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    // Return String representation of database primary key id
    @Override
    public String toString() {
        return id.toString();
    }

}
