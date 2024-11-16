package UserManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Client {
  private String name;
  private String phoneNumber;
  private String email;
  private int age;
  private int guardianId;

  public Client(String name, String phoneNumber, String email, int age, int guardianId) {
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.age = age;
    if (age < 18) {
      this.guardianId = guardianId;
    } else {
        this.guardianId = -1;
    }
  }

  public static int saveToDatabase(Connection conn, String name, String phoneNumber, String email, int age, int guardian) throws SQLException {
    String sql = "INSERT INTO Clients (name, phone_number, email, age, guardian_id) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, name);
      stmt.setString(2, phoneNumber);
      stmt.setString(3, email);
      stmt.setInt(4, age);
      if (guardian >= 0 ) {
        stmt.setInt(5, guardian);
      } else {
        stmt.setNull(5, java.sql.Types.INTEGER);
      }
      stmt.executeUpdate();
      ResultSet keys = stmt.getGeneratedKeys();
      if (keys.next()) {
          if (age < 18) {
              System.out.println("Client " + name + " is saved to database with ID: " + keys.getInt(1) + " with guardian ID: " + guardian);
              return -1;
          }
        System.out.println("Client " + name + " is saved to database with ID: " + keys.getInt(1));
        return keys.getInt(1);
      }
    } catch (SQLException e) {
      System.err.println("Failed to save client to database: " + e.getMessage());
      throw e;
    }
    return 0;
  }


public static void bookingOffering(Connection conn, int userId, int offeringId) {
    String checkAvailabilitySql = """
        SELECT o.id, l.capacity, s.start_date, s.end_date, s.day_of_week, s.timeSlot
        FROM Offerings o
        JOIN Lessons l ON o.lesson_id = l.id
        JOIN Schedules s ON l.schedule_id = s.id
        WHERE o.id = ? AND l.capacity > 0
    """;

    String checkConflictSql = """
        SELECT b.id
        FROM Bookings b
        JOIN Offerings o ON b.offering_id = o.id
        JOIN Lessons l ON o.lesson_id = l.id
        JOIN Schedules s ON l.schedule_id = s.id
        WHERE b.client_id = ? AND
              s.day_of_week = ? AND
              s.timeSlot = ? AND
              ((s.start_date <= ? AND s.end_date >= ?) OR
               (s.start_date <= ? AND s.end_date >= ?))
    """;

    String bookSql = "INSERT INTO Bookings (client_id, offering_id) VALUES (?, ?)";
    String updateCapacitySql = "UPDATE Lessons SET capacity = capacity - 1 WHERE id = ?";

    try (PreparedStatement checkAvailabilityStmt = conn.prepareStatement(checkAvailabilitySql);
         PreparedStatement checkConflictStmt = conn.prepareStatement(checkConflictSql);
         PreparedStatement bookStmt = conn.prepareStatement(bookSql);
         PreparedStatement updateCapacityStmt = conn.prepareStatement(updateCapacitySql)) {

        // Check availability
        checkAvailabilityStmt.setInt(1, offeringId);
        ResultSet rs = checkAvailabilityStmt.executeQuery();

        if (!rs.next()) {
            System.out.println("Offering is not available or capacity is full.");
            return;
        }

        // Get schedule details
        String dayOfWeek = rs.getString("day_of_week");
        String timeSlot = rs.getString("timeSlot");
        String startDate = rs.getString("start_date");
        String endDate = rs.getString("end_date");
        int lessonId = rs.getInt("id");

        // Check for conflicts
        checkConflictStmt.setInt(1, userId);
        checkConflictStmt.setString(2, dayOfWeek);
        checkConflictStmt.setString(3, timeSlot);
        checkConflictStmt.setString(4, startDate);
        checkConflictStmt.setString(5, endDate);
        checkConflictStmt.setString(6, startDate);
        checkConflictStmt.setString(7, endDate);

        ResultSet conflictRs = checkConflictStmt.executeQuery();
        if (conflictRs.next()) {
            System.out.println("You already have a booking for this time slot.");
            return;
        }

        // Book the offering
        bookStmt.setInt(1, userId);
        bookStmt.setInt(2, offeringId);
        bookStmt.executeUpdate();

        // Update lesson capacity
        updateCapacityStmt.setInt(1, lessonId);
        updateCapacityStmt.executeUpdate();

        System.out.println("Booking successful!");

    } catch (SQLException e) {
        System.err.println("Failed to book offering: " + e.getMessage());
    }
  }

    public static void viewBookings(Connection conn, int userId) {
        // SQL query with JOINs to get client name and lesson name
        String sql = "SELECT b.id AS booking_id, l.name AS lesson_name " +
                "FROM Bookings b " +
                "JOIN Offerings o ON b.offering_id = o.id " +
                "JOIN Lessons l ON o.lesson_id = l.id " +
                "WHERE b.client_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);  // Set the userId as the client ID in the query
            ResultSet rs = stmt.executeQuery();

            boolean found = false;

            while (rs.next()) {
                int bookingId = rs.getInt("booking_id");
                String lessonName = rs.getString("lesson_name");


                // Display the booking details, including the client and lesson names
                System.out.println(bookingId + ", Lesson: " + lessonName);

                found = true;
            }

            if (!found) {
                System.out.println("No bookings found for this client!");
            }
        } catch (SQLException e) {
            System.err.println("Failed to view bookings: " + e.getMessage());
        }
    }


    public static void cancelBooking(Connection conn, int userId, int bookingId) {
      String sql = "DELETE FROM Bookings WHERE id = ? AND client_id = ?";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
          stmt.setInt(1, bookingId);
          stmt.setInt(2, userId);
          int rowsDeleted = stmt.executeUpdate();
          if (rowsDeleted > 0) {
              System.out.println("Booking canceled successfully.");
          } else {
              System.out.println("Booking not found or you do not have permission to cancel it.");
          }
      } catch (SQLException e) {
          System.err.println("Failed to cancel booking: " + e.getMessage());
      }
  }

    public static int getChild(Connection conn, int userId) {
        String sql = "SELECT id FROM Clients WHERE guardian_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the guardian ID (userId) in the query
            stmt.setInt(1, userId);

            // Execute the query and get the result set
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // If a child is found, return the child's ID
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve child for guardian " + userId + ": " + e.getMessage());
            return -1; // Return -1 to indicate an error
        }
        return -1;
    }

    public static String getChildName(Connection conn, int userId) {
        String sql = "SELECT name FROM Clients WHERE guardian_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the guardian ID (userId) in the query
            stmt.setInt(1, userId);

            // Execute the query and get the result set
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // If a child is found, return the child's name
                    return rs.getString("name");
                } else {
                    // If no child is found, return null or handle accordingly
                    System.out.println("No child found for guardian with ID " + userId);
                    return null;
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve child's name for guardian " + userId + ": " + e.getMessage());
            return null; // Return null to indicate an error
        }
    }
}
