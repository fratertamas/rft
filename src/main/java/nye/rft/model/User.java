package nye.rft.model;

import java.util.Objects;

public class User {
    private String id;
    private String name;
    private UserRole role;

    public User() {
    }

    public User(String id, String name, UserRole role) {
        if (id == null || name == null || role == null) {
            throw new IllegalArgumentException("id, name, and role must not be null");
        }
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", role=" + role +
                '}';
    }
}

