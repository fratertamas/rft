package nye.rft.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
}
