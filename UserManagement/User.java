package UserManagement;

public class User {
    private static int idCounter = 0; // Static variable to keep track of the last assigned ID
    private int userId;
    private String name;

    // Constructor
    public User(String name) {
        this.userId = ++idCounter; // Increment and assign the new ID
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getUserInfo() {
        return "User ID: " + userId + ", Name: " + name;
    }
}