package OfferingManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Offering {
    private int lesson_id;
    private int instructor_id;

    public Offering(int lesson_id, int instructor_id) {
        this.lesson_id = lesson_id;
        this.instructor_id = instructor_id;
    }

    public void saveToDatabase(Connection conn, int lessonId, int instructorId) throws SQLException {
        String sql = "INSERT INTO Offerings (lesson_id, instructor_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lessonId);
            stmt.setInt(2, instructorId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to insert offering: " + e.getMessage());
            throw e;
        }
    }

    public void removeFromDatabase(Connection conn, int lessonId, int instructorId) throws SQLException {
        String sql = "DELETE FROM Offerings WHERE lesson_id = ? AND instructor_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lessonId);
            stmt.setInt(2, instructorId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to delete offering: " + e.getMessage());
            throw e;
        }
    }
}
