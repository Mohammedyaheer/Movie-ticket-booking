package model;

public class TicketSeat {
    private int id;
    private int ticketId;
    private String seatNo;
    private double price;
    
    public TicketSeat(int id, int ticketId, String seatNo, double price) {
        this.id = id;
        this.ticketId = ticketId;
        this.seatNo = seatNo;
        this.price = price;
    }
    
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getTicketId() { return ticketId; }
    public void setTicketId(int ticketId) { this.ticketId = ticketId; }
    
    public String getSeatNo() { return seatNo; }
    public void setSeatNo(String seatNo) { this.seatNo = seatNo; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    @Override
    public String toString() {
        return "Seat: " + seatNo + " - Price: $" + price;
    }
}