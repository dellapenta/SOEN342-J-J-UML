package OfferingManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Location {
    private String name;
    private String city;

    // Constructor
    public Location(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public static int saveToDatabase(Connection conn, String name, String city) throws SQLException {
        String sql = "INSERT INTO Locations (name, city) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setString(2, city);
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1); // Return generated ID
            }
        }
        throw new SQLException("Failed to insert location");
    }
    
    public String getCity(Connection conn, int id ) throws SQLException {
        String sql = "SELECT city FROM Locations WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("city");
                }
            }
        }
        throw new SQLException("Failed to retrieve city");
    }

}
