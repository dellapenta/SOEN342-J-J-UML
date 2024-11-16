package UserManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Instructor {
    private String name;
    private String phoneNumber;
    private String specialization;
    private String availablecity; // Variable can now contain multiple cities separated by a comma

    public Instructor(String name, String phoneNumber, String specialization, String availablecity) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.specialization = specialization;
        this.availablecity = availablecity;
    }

    public static void saveToDatabase(Connection conn, String name, String phoneNumber, String specialization, String availablecity) throws SQLException {
        String sql = "INSERT INTO Instructors (name, phone_number, specialization, available_cities) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setString(2, phoneNumber);
            stmt.setString(3, specialization);
            stmt.setString(4, availablecity);
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                System.out.println("Instructor " + name + " is saved to database with ID: " + keys.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Failed to save instructor to database: " + e.getMessage());
            throw e;
        }
    }


    public static void createOffering(Connection conn, int instructorId, int lessonId) {
        // SQL to get the instructor's city
        String instructorCitySql = "SELECT available_cities FROM Instructors WHERE id = ?";
        String lessonLocationCitySql = "SELECT l.city FROM Locations l JOIN Lessons le ON l.id = le.location_id WHERE le.id = ?";

        try {
            // Step 1: Retrieve the instructor's city
            String instructorCity = null;
            try (PreparedStatement stmt = conn.prepareStatement(instructorCitySql)) {
                stmt.setInt(1, instructorId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        instructorCity = rs.getString("available_cities");
                    }
                }
            }

            // Step 2: Retrieve the location's city for the given lesson
            String locationCity = null;
            try (PreparedStatement stmt = conn.prepareStatement(lessonLocationCitySql)) {
                stmt.setInt(1, lessonId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        locationCity = rs.getString("city");
                    }
                }
            }

            // Step 3: Compare the cities by splitting the instructor's city
            if (instructorCity != null && locationCity != null) {
                // Split the instructor's city by commas
                String[] instructorCityParts = instructorCity.split(",");

                // Check if any part of the instructor's city matches the location's city
                boolean cityMatches = false;
                for (String part : instructorCityParts) {
                    if (part.trim().equalsIgnoreCase(locationCity.trim())) {
                        cityMatches = true;
                        break;
                    }
                }

                // Step 4: Proceed based on the city match
                if (cityMatches) {
                    // Cities match, so proceed to create the offering
                    String sql = "INSERT INTO Offerings (lesson_id, instructor_id) VALUES (?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, lessonId);
                        stmt.setInt(2, instructorId);
                        stmt.executeUpdate();
                        System.out.println("Offering created successfully.");
                    } catch (SQLException e) {
                        System.err.println("Failed to create offering: " + e.getMessage());
                    }
                } else {
                    // Cities do not match
                    System.out.println("Instructor's city does not match the location's city. Offering creation aborted.");
                }
            } else {
                System.out.println("Instructor's city or location's city is null.");
            }

        } catch (SQLException e) {
            System.err.println("Failed to create offering due to an error: " + e.getMessage());
        }
    }


    public static void cancelOffering(Connection conn, int instructorId, int offeringId) {
        String sql = "DELETE FROM Offerings WHERE id = ? AND instructor_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, offeringId);
            stmt.setInt(2, instructorId);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Offering canceled successfully.");
            } else {
                System.out.println("Offering not found.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to cancel offering: " + e.getMessage());
        }
    }

    public static void viewOfferings(Connection conn, int instructorId) {
        // SQL query with JOINs to fetch offering details along with lesson and instructor names
        String sql = "SELECT o.id AS offering_id, o.lesson_id, l.name AS lesson_name, i.name AS instructor_name " +
                "FROM Offerings o " +
                "JOIN Lessons l ON o.lesson_id = l.id " +
                "JOIN Instructors i ON o.instructor_id = i.id " +
                "WHERE o.instructor_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, instructorId);  // Set the instructorId as the filter in the query
            ResultSet rs = stmt.executeQuery();

            boolean found = false;

            while (rs.next()) {
                int offeringId = rs.getInt("offering_id");
                int lessonId = rs.getInt("lesson_id");
                String lessonName = rs.getString("lesson_name");
                String instructorName = rs.getString("instructor_name");

                // Display the offering details along with lesson name and instructor name
                System.out.println(offeringId +
                        ", Lesson Name: " + lessonName);

                found = true;
            }

            if (!found) {
                System.out.println("No offerings found for this instructor.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to view offerings: " + e.getMessage());
        }
    }

}
