package ru.javawebinar.topjava.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.GET, query = "SELECT e FROM Meal e WHERE e.id =?1 and e.user.id =?2"),
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal e WHERE e.id =?1 and e.user.id =?2"),
        @NamedQuery(name = Meal.GET_ALL, query = "SELECT e FROM Meal e WHERE e.user.id =?1 ORDER BY e.dateTime DESC"),
        @NamedQuery(name = Meal.GET_BETWEEN, query = "SELECT e FROM Meal e WHERE e.user.id =?1 and e.dateTime BETWEEN ?2 and  ?3 ORDER BY e.dateTime DESC")
})
@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"date_time", "user_id"}, name = "users_unique_email_idx")})
public class Meal extends AbstractBaseEntity {

    public static final String GET = "Meal.get";
    public static final String DELETE = "Meal.delete";
    public static final String GET_ALL = "Meal.getAll";
    public static final String GET_BETWEEN = "Meal.getBetween";
    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;
    @Column(name = "description")
    private String description;
    @Column(name = "calories", nullable = false)
    private int calories;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Meal() {
    }
    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return "Meal{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", id=" + id +
                '}';
    }
}
