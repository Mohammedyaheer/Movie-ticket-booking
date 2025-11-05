package dao;

import model.Ticket;
import model.TicketSeat;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {
    public int createTicket(Ticket ticket, List<TicketSeat> seats) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Insert ticket
            String ticketSql = "INSERT INTO ticket (show_id, user_id, status, total) VALUES (?, ?, ?, ?)";
            int ticketId = -1;
            
            try (PreparedStatement pstmt = conn.prepareStatement(ticketSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, ticket.getShowId());
                pstmt.setInt(2, ticket.getUserId());
                pstmt.setString(3, ticket.getStatus());
                pstmt.setDouble(4, ticket.getTotal());
                
                pstmt.executeUpdate();
                
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    ticketId = rs.getInt(1);
                }
            }
            
            // Insert ticket seats
            if (ticketId != -1) {
                String seatSql = "INSERT INTO ticket_seat (ticket_id, seat_no, price) VALUES (?, ?, ?)";
                
                try (PreparedStatement pstmt = conn.prepareStatement(seatSql)) {
                    for (TicketSeat seat : seats) {
                        pstmt.setInt(1, ticketId);
                        pstmt.setString(2, seat.getSeatNo());
                        pstmt.setDouble(3, seat.getPrice());
                        pstmt.addBatch();
                    }
                    pstmt.executeBatch();
                }
            }
            
            conn.commit();
            return ticketId;
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
    
    public boolean cancelTicket(int ticketId) throws SQLException {
        String sql = "UPDATE ticket SET status = 'CANCELLED' WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, ticketId);
            int rowsAffected = pstmt.executeUpdate();
            
            return rowsAffected > 0;
        }
    }
}