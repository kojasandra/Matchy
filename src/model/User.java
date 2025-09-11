package model;

import java.time.LocalDateTime;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String bio;
    private String location;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    public User(int id, String name, String email, String password,
                String bio, String location, String role,
                LocalDateTime createdAt, LocalDateTime lastLogin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.location = location;
        this.role = role;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
    }

    // Getteri i setteri
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getBio() { return bio; }
    public String getLocation() { return location; }
    public String getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getLastLogin() { return lastLogin; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setBio(String bio) { this.bio = bio; }
    public void setLocation(String location) { this.location = location; }
    public void setRole(String role) { this.role = role; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", bio='" + bio + '\'' +
                ", location='" + location + '\'' +
                ", role='" + role + '\'' +
                ", createdAt=" + createdAt +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
