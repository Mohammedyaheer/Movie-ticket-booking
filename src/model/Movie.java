package model;

public class Movie {
    private int id;
    private String title;
    private String language;
    private String certification;
    
    public Movie(int id, String title, String language, String certification) {
        this.id = id;
        this.title = title;
        this.language = language;
        this.certification = certification;
    }
    
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    
    public String getCertification() { return certification; }
    public void setCertification(String certification) { this.certification = certification; }
    
    @Override
    public String toString() {
        return title + " (" + language + ") - " + certification;
    }
}