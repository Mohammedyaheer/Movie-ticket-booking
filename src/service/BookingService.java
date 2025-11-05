package service;

import dao.TicketDAO;
import model.Ticket;
import model.TicketSeat;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private TicketDAO ticketDAO;
    private SeatMapService seatMapService;
    
    public BookingService() {
        this.ticketDAO = new TicketDAO();
        this.seatMapService = new SeatMapService();
    }
    
    public int bookTickets(int showId, int userId, List<String> seatNumbers, double totalPrice) throws SQLException {
        Ticket ticket = new Ticket(0, showId, userId, "BOOKED", totalPrice, LocalDateTime.now());
        
        List<TicketSeat> ticketSeats = new ArrayList<>();
        int screenId = getScreenIdForShow(showId);
        
        for (String seatNo : seatNumbers) {
            // Check if seat is available using the new method
            if (!seatMapService.isSeatAvailable(screenId, seatNo)) {
                throw new SQLException("Seat " + seatNo + " is not available");
            }
            
            // Book the seat using the new method
            if (!seatMapService.bookSeat(screenId, seatNo)) {
                throw new SQLException("Failed to book seat " + seatNo);
            }
            
            // For simplicity, using a fixed price per seat
            ticketSeats.add(new TicketSeat(0, 0, seatNo, 10.0));
        }
        
        return ticketDAO.createTicket(ticket, ticketSeats);
    }
    
    public boolean cancelTicket(int ticketId) throws SQLException {
        // In a real application, you would also release the seats
        return ticketDAO.cancelTicket(ticketId);
    }
    
    // Helper method - in a real application, you would get this from the database
    private int getScreenIdForShow(int showId) {
        // For simplicity, return a fixed screen ID based on show ID
        return (showId % 5) + 1;
    }
    
    // Method to get seat map service for access in main app
    public SeatMapService getSeatMapService() {
        return seatMapService;
    }
}