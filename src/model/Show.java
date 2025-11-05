package model;

import java.time.LocalDateTime;

public class Show {
    private int id;
    private int screenId;
    private int movieId;
    private LocalDateTime showTime;
    private String priceMap;
    
    public Show(int id, int screenId, int movieId, LocalDateTime showTime, String priceMap) {
        this.id = id;
        this.screenId = screenId;
        this.movieId = movieId;
        this.showTime = showTime;
        this.priceMap = priceMap;
    }
    
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getScreenId() { return screenId; }
    public void setScreenId(int screenId) { this.screenId = screenId; }
    
    public int getMovieId() { return movieId; }
    public void setMovieId(int movieId) { this.movieId = movieId; }
    
    public LocalDateTime getShowTime() { return showTime; }
    public void setShowTime(LocalDateTime showTime) { this.showTime = showTime; }
    
    public String getPriceMap() { return priceMap; }
    public void setPriceMap(String priceMap) { this.priceMap = priceMap; }
    
    @Override
    public String toString() {
        return "Show ID: " + id + " at " + showTime;
    }
}