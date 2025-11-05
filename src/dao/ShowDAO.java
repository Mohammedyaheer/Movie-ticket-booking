package dao;

import model.Show;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShowDAO {
    
    public List<Show> getShowsByMovieAndCity(int movieId, String city, LocalDateTime date) throws SQLException {
        List<Show> shows = new ArrayList<>();
        String sql = "SELECT s.* FROM show_table s " +
                     "JOIN screen sc ON s.screen_id = sc.id " +
                     "JOIN theatre t ON sc.theatre_id = t.id " +
                     "WHERE s.movie_id = ? AND LOWER(t.city) = LOWER(?) AND DATE(s.show_ts) = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, movieId);
            pstmt.setString(2, city);
            pstmt.setDate(3, Date.valueOf(date.toLocalDate()));
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Show show = new Show(
                    rs.getInt("id"),
                    rs.getInt("screen_id"),
                    rs.getInt("movie_id"),
                    rs.getTimestamp("show_ts").toLocalDateTime(),
                    rs.getString("price_map")
                );
                shows.add(show);
            }
        }
        return shows;
    }
    
    public Show getShowById(int id) throws SQLException {
        String sql = "SELECT * FROM show_table WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Show(
                    rs.getInt("id"),
                    rs.getInt("screen_id"),
                    rs.getInt("movie_id"),
                    rs.getTimestamp("show_ts").toLocalDateTime(),
                    rs.getString("price_map")
                );
            }
        }
        return null;
    }
    
    public List<Show> getShowsByTheatre(int theatreId, LocalDateTime date) throws SQLException {
        List<Show> shows = new ArrayList<>();
        String sql = "SELECT s.* FROM show_table s " +
                     "JOIN screen sc ON s.screen_id = sc.id " +
                     "WHERE sc.theatre_id = ? AND DATE(s.show_ts) = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, theatreId);
            pstmt.setDate(2, Date.valueOf(date.toLocalDate()));
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Show show = new Show(
                    rs.getInt("id"),
                    rs.getInt("screen_id"),
                    rs.getInt("movie_id"),
                    rs.getTimestamp("show_ts").toLocalDateTime(),
                    rs.getString("price_map")
                );
                shows.add(show);
            }
        }
        return shows;
    }
    
    public List<Show> getShowsByScreen(int screenId, LocalDateTime date) throws SQLException {
        List<Show> shows = new ArrayList<>();
        String sql = "SELECT * FROM show_table WHERE screen_id = ? AND DATE(show_ts) = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, screenId);
            pstmt.setDate(2, Date.valueOf(date.toLocalDate()));
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Show show = new Show(
                    rs.getInt("id"),
                    rs.getInt("screen_id"),
                    rs.getInt("movie_id"),
                    rs.getTimestamp("show_ts").toLocalDateTime(),
                    rs.getString("price_map")
                );
                shows.add(show);
            }
        }
        return shows;
    }
    
    public boolean addShow(Show show) throws SQLException {
        String sql = "INSERT INTO show_table (screen_id, movie_id, show_ts, price_map) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, show.getScreenId());
            pstmt.setInt(2, show.getMovieId());
            pstmt.setTimestamp(3, Timestamp.valueOf(show.getShowTime()));
            pstmt.setString(4, show.getPriceMap());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    public boolean updateShow(Show show) throws SQLException {
        String sql = "UPDATE show_table SET screen_id = ?, movie_id = ?, show_ts = ?, price_map = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, show.getScreenId());
            pstmt.setInt(2, show.getMovieId());
            pstmt.setTimestamp(3, Timestamp.valueOf(show.getShowTime()));
            pstmt.setString(4, show.getPriceMap());
            pstmt.setInt(5, show.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    public boolean deleteShow(int id) throws SQLException {
        String sql = "DELETE FROM show_table WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}