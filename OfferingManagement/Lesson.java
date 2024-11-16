package OfferingManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Lesson {
    private int id;
    private String name;
    private String type;
    private int capacity;
    private int locationId;
    private int scheduleId;

    public Lesson(int id, String name, String type, int capacity, int locationId, int scheduleId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.locationId = locationId;
        this.scheduleId = scheduleId;
    }

    public static void saveToDatabase(Connection conn, String name, String type, int capacity, int locationId, int scheduleId) throws SQLException {
        String sql = "INSERT INTO Lessons (name, type, capacity, location_id, schedule_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setString(2, type);
            stmt.setInt(3, capacity);
            stmt.setInt(4, locationId);
            stmt.setInt(5, scheduleId);
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                System.out.println("Lesson: " + name + " is created with ID " + keys.getInt(1)); // Print generated ID
            }
        } catch (SQLException e) {
            System.err.println("Failed to save lesson: " + e.getMessage());
            throw e; // Re-throw exception after logging it
        }
    }
}
