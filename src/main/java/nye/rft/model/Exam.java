package nye.rft.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Exam {
    private String id;
    private Date date;
    private String course;
    private String location;
    private User user;
    private int maxStudents;
    private List<User> registeredStudents;

    public Exam(String id, Date date, String course, String location, User user, int maxStudents) {
        this.id = id;
        this.date = date;
        this.course = course;
        this.location = location;
        this.user = user;
        this.maxStudents = maxStudents;
        this.registeredStudents = new ArrayList<>();
    }

    public Date getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public String getCourse() {
        return course;
    }

    public String getLocation() {
        return location;
    }

    public User getUser() {
        return user;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public List<User> getRegisteredStudents() {
        return registeredStudents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return maxStudents  == exam.maxStudents  &&
                Objects.equals(id, exam.id) &&
                Objects.equals(date, exam.date) &&
                Objects.equals(course, exam.course) &&
                Objects.equals(location, exam.location) &&
                Objects.equals(user, exam.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, course, location, user, maxStudents);
    }
}
