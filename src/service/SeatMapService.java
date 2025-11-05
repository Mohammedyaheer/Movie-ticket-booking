package service;

import java.util.HashMap;
import java.util.Map;

public class SeatMapService {
    private Map<Integer, boolean[][]> seatMaps; // Screen ID to seat availability map
    
    public SeatMapService() {
        this.seatMaps = new HashMap<>();
        // Initialize with some sample data
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        // For demonstration, create seat maps for screens
        for (int screenId = 1; screenId <= 5; screenId++) {
            int rows = 10;
            int cols = 15;
            boolean[][] seats = new boolean[rows][cols];
            
            // Mark some seats as occupied for demonstration
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    seats[i][j] = false; // All seats initially available
                }
            }
            
            // Mark some random seats as occupied
            for (int i = 0; i < 20; i++) {
                int row = (int) (Math.random() * rows);
                int col = (int) (Math.random() * cols);
                seats[row][col] = true;
            }
            
            seatMaps.put(screenId, seats);
        }
    }
    
    public boolean[][] getSeatMap(int screenId) {
        return seatMaps.get(screenId);
    }
    
    public boolean isSeatAvailable(int screenId, String seatNo) {
        int[] coordinates = parseSeatNumber(seatNo);
        if (coordinates == null) return false;
        
        int row = coordinates[0];
        int col = coordinates[1];
        
        boolean[][] seats = seatMaps.get(screenId);
        if (seats != null && row < seats.length && col < seats[0].length) {
            return !seats[row][col];
        }
        return false;
    }
    
    public synchronized boolean bookSeat(int screenId, String seatNo) {
        int[] coordinates = parseSeatNumber(seatNo);
        if (coordinates == null) return false;
        
        int row = coordinates[0];
        int col = coordinates[1];
        
        boolean[][] seats = seatMaps.get(screenId);
        if (seats != null && row < seats.length && col < seats[0].length && !seats[row][col]) {
            seats[row][col] = true;
            return true;
        }
        return false;
    }
    
    public synchronized void releaseSeat(int screenId, String seatNo) {
        int[] coordinates = parseSeatNumber(seatNo);
        if (coordinates == null) return;
        
        int row = coordinates[0];
        int col = coordinates[1];
        
        boolean[][] seats = seatMaps.get(screenId);
        if (seats != null && row < seats.length && col < seats[0].length) {
            seats[row][col] = false;
        }
    }
    
    // Helper method to parse seat numbers like "A1", "B12", etc. (case-insensitive)
    private int[] parseSeatNumber(String seatNo) {
        if (seatNo == null || seatNo.length() < 2) return null;
        
        try {
            // Convert to uppercase to handle both "A1" and "a1"
            String upperSeatNo = seatNo.toUpperCase();
            
            // Extract row letter (first character)
            char rowChar = upperSeatNo.charAt(0);
            int row = rowChar - 'A'; // Convert 'A' to 0, 'B' to 1, etc.
            
            if (row < 0 || row > 25) return null;
            
            // Extract column number (remaining characters)
            String colStr = upperSeatNo.substring(1);
            int col = Integer.parseInt(colStr) - 1; // Convert to 0-based index
            
            return new int[]{row, col};
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    public void displaySeatMap(int screenId) {
        boolean[][] seats = seatMaps.get(screenId);
        if (seats == null) {
            System.out.println("No seat map available for screen ID: " + screenId);
            return;
        }
        
        System.out.println("Screen " + screenId + " Seat Map:");
        System.out.print("   ");
        for (int j = 0; j < seats[0].length; j++) {
            System.out.printf("%2d ", j + 1);
        }
        System.out.println();
        
        for (int i = 0; i < seats.length; i++) {
            System.out.printf("%2c ", 'A' + i);
            for (int j = 0; j < seats[i].length; j++) {
                System.out.print(seats[i][j] ? " X " : " O ");
            }
            System.out.println();
        }
        System.out.println("Legend: O = Available, X = Occupied");
        System.out.println("Note: Seat names are case-insensitive (A1 = a1)");
    }
    
    // Method to get the number of rows in a screen
    public int getRowCount(int screenId) {
        boolean[][] seats = seatMaps.get(screenId);
        return seats != null ? seats.length : 0;
    }
    
    // Method to get the number of columns in a screen
    public int getColumnCount(int screenId) {
        boolean[][] seats = seatMaps.get(screenId);
        return (seats != null && seats.length > 0) ? seats[0].length : 0;
    }
}