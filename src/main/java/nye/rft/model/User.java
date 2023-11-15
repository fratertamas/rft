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
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
