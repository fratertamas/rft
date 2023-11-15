package nye.rft.model;

public class User {
    private String id;
    private String name;
    private UserRole role;

    public User() {
    }

    public User(String id, String name, UserRole role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

}
