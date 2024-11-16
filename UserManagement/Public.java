package UserManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Public {

    // Method to view all public offerings with lesson details
    public static void viewPublicOfferings(Connection conn) {
        String sql = "SELECT o.id AS offering_id, o.lesson_id, l.name AS lesson_name, l.type AS lesson_type, "
                + "l.capacity AS lesson_capacity, l.location_id, l.schedule_id, loc.city AS location_city, "
                + "s.start_date, s.end_date, s.timeSlot, s.day_of_week "
                + "FROM Offerings o "
                + "JOIN Lessons l ON o.lesson_id = l.id "
                + "JOIN Locations loc ON l.location_id = loc.id "
                + "JOIN Schedules s ON l.schedule_id = s.id";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int offeringId = rs.getInt("offering_id");
                int lessonId = rs.getInt("lesson_id");
                String lessonName = rs.getString("lesson_name");
                String lessonType = rs.getString("lesson_type");
                int locationId = rs.getInt("location_id");
                int scheduleId = rs.getInt("schedule_id");
                String locationCity = rs.getString("location_city");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                String timeSlot = rs.getString("timeSlot");
                String dayOfWeek = rs.getString("day_of_week");

                // Output the full details of the offering
                System.out.println(offeringId
                        + ", Name: " + lessonName
                        + " (" + lessonType + ") "
                        + ", City: " + locationCity
                        + ", From " + startDate
                        + " to " + endDate
                        + " (" + timeSlot + ") "
                        + " on " + dayOfWeek);
            }
        } catch (SQLException e) {
            System.err.println("Failed to view offerings: " + e.getMessage());
        }
    }

}