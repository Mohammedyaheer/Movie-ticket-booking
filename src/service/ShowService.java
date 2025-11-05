package service;

import dao.MovieDAO;
import dao.ShowDAO;
import dao.TheatreDAO;
import model.Movie;
import model.Show;
import model.Theatre;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShowService {
    private MovieDAO movieDAO;
    private TheatreDAO theatreDAO;
    private ShowDAO showDAO;
    
    public ShowService() {
        this.movieDAO = new MovieDAO();
        this.theatreDAO = new TheatreDAO();
        this.showDAO = new ShowDAO();
    }
    
    public List<Movie> getAllMovies() throws SQLException {
        return movieDAO.getAllMovies();
    }
    
    public Movie getMovieById(int id) throws SQLException {
        return movieDAO.getMovieById(id);
    }
    
    public List<Theatre> getTheatresByCity(String city) throws SQLException {
        return theatreDAO.getTheatresByCity(city);
    }
    
    public List<String> getAvailableCities() throws SQLException {
        return theatreDAO.getAllCities();
    }
    
    public List<Show> getShowsByMovieAndCity(int movieId, String city, LocalDateTime date) throws SQLException {
        return showDAO.getShowsByMovieAndCity(movieId, city, date);
    }
    
    public Show getShowById(int id) throws SQLException {
        return showDAO.getShowById(id);
    }
    
    public List<Show> getShowsByTheatre(int theatreId, LocalDateTime date) throws SQLException {
        return showDAO.getShowsByTheatre(theatreId, date);
    }
    
    public List<Show> getShowsByScreen(int screenId, LocalDateTime date) throws SQLException {
        return showDAO.getShowsByScreen(screenId, date);
    }
    
    public boolean addShow(Show show) throws SQLException {
        return showDAO.addShow(show);
    }
    
    public boolean updateShow(Show show) throws SQLException {
        return showDAO.updateShow(show);
    }
    
    public boolean deleteShow(int id) throws SQLException {
        return showDAO.deleteShow(id);
    }
    
    // Helper method to find similar city names
    public List<String> findSimilarCities(String inputCity) throws SQLException {
        List<String> similarCities = new ArrayList<>();
        List<String> allCities = getAvailableCities();
        String lowerInput = inputCity.toLowerCase();
        
        for (String city : allCities) {
            if (city.toLowerCase().contains(lowerInput) || 
                lowerInput.contains(city.toLowerCase())) {
                similarCities.add(city);
            }
        }
        return similarCities;
    }
}