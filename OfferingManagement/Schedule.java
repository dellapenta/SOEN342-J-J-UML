package OfferingManagement;

import java.sql.*;
import java.time.LocalDate;

public class Schedule {

    public static int saveToDatabase(Connection conn, LocalDate startDate, LocalDate endDate, String timeSlot, String dayOfWeek) throws SQLException {
        String sql = "INSERT INTO Schedules (start_date, end_date, timeSlot, day_of_week) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));
            stmt.setString(3, timeSlot);
            stmt.setString(4, dayOfWeek);
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1); // Return generated ID
            }
        } catch (SQLException e) {
            System.err.println("Failed to insert schedule: " + e.getMessage());
            throw e;
        }
        throw new SQLException("Failed to insert schedule");
    }
}
