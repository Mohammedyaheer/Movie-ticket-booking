package dao;

import model.Theatre;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TheatreDAO {
    public List<Theatre> getTheatresByCity(String city) throws SQLException {
        List<Theatre> theatres = new ArrayList<>();
        String sql = "SELECT * FROM theatre WHERE LOWER(city) = LOWER(?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, city);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Theatre theatre = new Theatre(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("city")
                );
                theatres.add(theatre);
            }
        }
        return theatres;
    }
    
    // Additional method to get all cities
    public List<String> getAllCities() throws SQLException {
        List<String> cities = new ArrayList<>();
        String sql = "SELECT DISTINCT city FROM theatre ORDER BY city";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                cities.add(rs.getString("city"));
            }
        }
        return cities;
    }
    
    // Additional method to get theatre by ID
    public Theatre getTheatreById(int id) throws SQLException {
        String sql = "SELECT * FROM theatre WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Theatre(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("city")
                );
            }
        }
        return null;
    }
}