package UserManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import OfferingManagement.Booking;
import OfferingManagement.Lesson;
import OfferingManagement.Schedule;

public class Administrator {
    private String name;
    private String email;
    private String password;

    public Administrator(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static void saveToDatabase(Connection conn, String name, String email, String password) throws SQLException {
        String sql = "INSERT INTO Administrators (name, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                System.out.println("Administrator saved to database with ID: " + keys.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Failed to save administrator to database: " + e.getMessage());
            throw e;
    }
    }

    public static void createLesson(Connection conn, String name, String type, int capacity, int location_id, int schedule_id) throws SQLException {
        Lesson.saveToDatabase(conn, name, type, capacity, location_id, schedule_id);
    }
            
    public static void removeLesson(Connection conn, int id) throws SQLException {
        // Check if the lesson exists
        String checkSql = "SELECT COUNT(*) FROM Lessons WHERE id = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, id);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    // No lesson found with the given ID
                    System.out.println("No lesson found with ID " + id);
                    return;  // Exit the method if no lesson is found
                }
            }
        }
        // Proceed to remove the lesson if it exists
        String deleteSql = "DELETE FROM Lessons WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Lesson with ID " + id + " has been removed.");
            } else {
                System.out.println("Failed to remove lesson with ID " + id);
            }
        } catch (SQLException e) {
            System.err.println("Failed to remove lesson with ID " + id + ": " + e.getMessage());
            throw e;
        }
    }

    public static void removeClient(Connection conn, int id) throws SQLException {
        // Check if the client exists
        String checkSql = "SELECT COUNT(*) FROM Clients WHERE id = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, id);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    // No client found with the given ID
                    System.out.println("No client found with ID " + id);
                    return;  // Exit the method if no client is found
                }
            }
        }
    
        // Proceed to remove the client if it exists
        String deleteSql = "DELETE FROM Clients WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Client with ID " + id + " has been removed.");
            } else {
                System.out.println("Failed to remove client with ID " + id);
            }
        } catch (SQLException e) {
            System.err.println("Failed to remove client with ID " + id + ": " + e.getMessage());
            throw e;
        }
    }

    public static void removeInstructor(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM Instructors WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Instructor with ID " + id + " has been removed.");
        } catch (SQLException e) {
            System.err.println("Failed to remove instructor with ID " + id + ": " + e.getMessage());
            throw e;
        }
    }

    public static void viewLessons(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Lessons";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String type = rs.getString("type");
                int capacity = rs.getInt("capacity");
                int location_id = rs.getInt("location_id");
                int schedule_id = rs.getInt("schedule_id");
                System.out.println(id + ", Name: " + name + ", Type: " + type + ", Capacity: " + capacity + ", Location ID: " + location_id + ", Schedule ID: " + schedule_id);
            }
        } catch (SQLException e) {
            System.err.println("Failed to view lessons: " + e.getMessage());
            throw e;
        }
    }

    public static void viewOfferings(Connection conn) throws SQLException {
        // SQL query with JOINs to fetch details from Offerings, Lessons, and Instructors
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

    public static void viewClients(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Clients";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                System.out.println(id + ", Name: " + name + ", Email: " + email);
            }
        } catch (SQLException e) {
            System.err.println("Failed to view clients: " + e.getMessage());
            throw e;
        }
    }

    public static void viewInstructors(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Instructors";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                System.out.println(id + ", Name: " + name);
            }
        } catch (SQLException e) {
            System.err.println("Failed to view instructors: " + e.getMessage());
            throw e;
        }
    }

    public static void viewBookings(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Bookings";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            boolean hasBookings = false;

            while (rs.next()) {
                int id = rs.getInt("id");
                int client_id = rs.getInt("client_id");
                int offering_id = rs.getInt("offering_id");
                System.out.println(id + ", Client ID: " + client_id + ", Offering ID: " + offering_id);
                hasBookings = true;
            }

            if (!hasBookings) {
                System.out.println("No bookings found.");
            }

        } catch (SQLException e) {
            System.err.println("Failed to view bookings: " + e.getMessage());
            throw e;
        }
    }

}
