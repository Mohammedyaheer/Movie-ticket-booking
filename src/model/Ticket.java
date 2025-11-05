package model;

import java.time.LocalDateTime;

public class Ticket {
    private int id;
    private int showId;
    private int userId;
    private String status;
    private double total;
    private LocalDateTime bookingTime;
    
    public Ticket(int id, int showId, int userId, String status, double total, LocalDateTime bookingTime) {
        this.id = id;
        this.showId = showId;
        this.userId = userId;
        this.status = status;
        this.total = total;
        this.bookingTime = bookingTime;
    }
    
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getShowId() { return showId; }
    public void setShowId(int showId) { this.showId = showId; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    
    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }
    
    @Override
    public String toString() {
        return "Ticket ID: " + id + " - Total: $" + total + " - Status: " + status;
    }
}