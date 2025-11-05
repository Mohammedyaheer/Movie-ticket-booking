package main;

import service.BookingService;
import service.SeatMapService;
import service.ShowService;
import dao.DatabaseConnection;
import model.Movie;
import model.Theatre;
import model.Show;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    private ShowService showService;
    private SeatMapService seatMapService;
    private BookingService bookingService;
    private Scanner scanner;
    
    public MainApp() {
        this.scanner = new Scanner(System.in);
        
        // Test database connection first
        System.out.println("Testing database connection...");
        if (!DatabaseConnection.testConnection()) {
            showTroubleshootingGuide();
            System.exit(1);
        }
        
        this.showService = new ShowService();
        this.seatMapService = new SeatMapService();
        this.bookingService = new BookingService();
        
        System.out.println("Database connection established successfully.");
    }
    
    private void showTroubleshootingGuide() {
        System.out.println("\n=== TROUBLESHOOTING GUIDE ===");
        System.out.println("1. Make sure MySQL server is running");
        System.out.println("2. Check if database 'movie_ticket_system' exists");
        System.out.println("3. Verify user 'movie_user' with password 'movie_pass' has privileges");
        System.out.println("4. Ensure MySQL JDBC driver is in classpath:");
        System.out.println("   - Right-click project → Build Path → Configure Build Path");
        System.out.println("   - Add mysql-connector-java-8.0.x.jar");
        System.out.println("5. Run the SQL script in MySQL Workbench to create tables");
    }
    
    public void start() {
        System.out.println("\nWelcome to Movie Ticket Booking System!");
        
        while (true) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Browse Movies");
            System.out.println("2. View Theaters by City");
            System.out.println("3. View Shows");
            System.out.println("4. View Seat Map");
            System.out.println("5. Book Tickets");
            System.out.println("6. Cancel Ticket");
            System.out.println("7. Exit");
            
            int choice = getIntInput("Enter your choice: ");
            
            try {
                switch (choice) {
                    case 1:
                        browseMovies();
                        break;
                    case 2:
                        viewTheatersByCity();
                        break;
                    case 3:
                        viewShows();
                        break;
                    case 4:
                        viewSeatMap();
                        break;
                    case 5:
                        bookTickets();
                        break;
                    case 6:
                        cancelTicket();
                        break;
                    case 7:
                        System.out.println("Thank you for using Movie Ticket Booking System!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
                System.out.println("Please check your database connection.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    
    private void browseMovies() throws SQLException {
        System.out.println("\nAvailable Movies:");
        List<Movie> movies = showService.getAllMovies();
        if (movies.isEmpty()) {
            System.out.println("No movies found.");
        } else {
            for (Movie movie : movies) {
                System.out.println(movie.getId() + ". " + movie);
            }
        }
    }
    
    private void viewTheatersByCity() throws SQLException {
        // Show available cities first
        System.out.println("\nAvailable Cities:");
        List<String> cities = showService.getAvailableCities();
        if (cities.isEmpty()) {
            System.out.println("No cities found in database.");
            return;
        }
        
        for (String city : cities) {
            System.out.println("- " + city);
        }
        
        String city = getStringInput("\nEnter city: ");
        System.out.println("\nTheaters in " + city + ":");
        List<Theatre> theatres = showService.getTheatresByCity(city);
        if (theatres.isEmpty()) {
            System.out.println("No theaters found in " + city);
            
            // Suggest similar cities
            List<String> similarCities = showService.findSimilarCities(city);
            if (!similarCities.isEmpty() && similarCities.size() > 1) {
                System.out.println("Did you mean one of these cities?");
                for (String similarCity : similarCities) {
                    if (!similarCity.equalsIgnoreCase(city)) {
                        System.out.println("- " + similarCity);
                    }
                }
            }
        } else {
            for (Theatre theatre : theatres) {
                System.out.println(theatre.getId() + ". " + theatre);
            }
        }
    }
    
    private void viewShows() throws SQLException {
        // First show available movies
        System.out.println("\nAvailable Movies:");
        List<Movie> movies = showService.getAllMovies();
        if (movies.isEmpty()) {
            System.out.println("No movies found.");
            return;
        }
        
        for (Movie movie : movies) {
            System.out.println(movie.getId() + ". " + movie);
        }
        
        int movieId = getIntInput("\nEnter movie ID: ");
        
        // Verify movie exists
        Movie selectedMovie = showService.getMovieById(movieId);
        if (selectedMovie == null) {
            System.out.println("Invalid movie ID. Please try again.");
            return;
        }
        
        // Show available cities to help user
        System.out.println("\nAvailable Cities:");
        List<String> cities = showService.getAvailableCities();
        if (cities.isEmpty()) {
            System.out.println("No cities found in database.");
            return;
        }
        
        for (String city : cities) {
            System.out.println("- " + city);
        }
        
        String city = getStringInput("\nEnter city: ");
        
        // Allow date selection
        LocalDateTime date;
        String dateInput = getStringInput("Enter date (YYYY-MM-DD) or press Enter for today: ");
        
        if (dateInput.isEmpty()) {
            date = LocalDateTime.now();
        } else {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                date = LocalDateTime.parse(dateInput + "T00:00:00");
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Using today's date.");
                date = LocalDateTime.now();
            }
        }
        
        System.out.println("\nSearching for shows for '" + selectedMovie.getTitle() + "' in '" + city + "' on " + date.toLocalDate() + ":");
        List<Show> shows = showService.getShowsByMovieAndCity(movieId, city, date);
        
        if (shows.isEmpty()) {
            System.out.println("No shows found for the selected criteria.");
            System.out.println("Possible reasons:");
            System.out.println("1. The movie may not be showing in this city");
            System.out.println("2. No shows scheduled for the selected date");
            
            // Check if the city exists in database
            boolean cityExists = false;
            for (String availableCity : cities) {
                if (availableCity.equalsIgnoreCase(city)) {
                    cityExists = true;
                    break;
                }
            }
            
            if (!cityExists) {
                System.out.println("3. The city name might be misspelled");
                // Suggest alternative cities
                List<String> similarCities = showService.findSimilarCities(city);
                if (!similarCities.isEmpty()) {
                    System.out.println("Did you mean one of these cities?");
                    for (String similarCity : similarCities) {
                        System.out.println("- " + similarCity);
                    }
                }
            }
        } else {
            System.out.println("=== " + selectedMovie.getTitle() + " ===");
            System.out.println("Language: " + selectedMovie.getLanguage() + " | Certification: " + selectedMovie.getCertification());
            System.out.println("----------------------------------------");
            
            for (Show show : shows) {
                System.out.println("Show ID: " + show.getId() + " | Time: " + show.getShowTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            }
            
            System.out.println("\nUse 'Book Tickets' option with the Show ID to make a booking.");
        }
    }
    
    private void viewSeatMap() {
        int screenId = getIntInput("Enter screen ID: ");
        seatMapService.displaySeatMap(screenId);
    }
    
    private void bookTickets() throws SQLException {
        int showId = getIntInput("Enter show ID: ");
        int userId = getIntInput("Enter user ID: ");
        
        // Get the screen ID for the show (using the same logic as in BookingService)
        int screenId = (showId % 5) + 1;
        seatMapService.displaySeatMap(screenId);
        
        String seatsInput = getStringInput("Enter seat numbers (comma-separated, e.g., A1,B2): ");
        List<String> seatNumbers = Arrays.asList(seatsInput.split(","));
        
        // Validate seat numbers before proceeding
        boolean allSeatsValid = true;
        List<String> upperSeatNumbers = new ArrayList<>();
        
        for (String seatNo : seatNumbers) {
            // Convert to uppercase for case-insensitive handling
            String upperSeatNo = seatNo.toUpperCase().trim();
            upperSeatNumbers.add(upperSeatNo);
            
            if (!isValidSeatFormat(upperSeatNo)) {
                System.out.println("Invalid seat format: " + seatNo + ". Please use format like A1, B12, etc.");
                allSeatsValid = false;
                break;
            }
            
            if (!seatMapService.isSeatAvailable(screenId, upperSeatNo)) {
                System.out.println("Seat " + upperSeatNo + " is not available. Please choose different seats.");
                allSeatsValid = false;
                break;
            }
        }
        
        if (!allSeatsValid) {
            return;
        }
        
        double totalPrice = upperSeatNumbers.size() * 10.0; // $10 per seat
        
        System.out.println("Selected seats: " + upperSeatNumbers);
        System.out.println("Total price: $" + totalPrice);
        String confirm = getStringInput("Confirm booking? (yes/no): ");
        
        if (confirm.equalsIgnoreCase("yes")) {
            int ticketId = bookingService.bookTickets(showId, userId, upperSeatNumbers, totalPrice);
            System.out.println("Booking successful! Your ticket ID is: " + ticketId);
        } else {
            System.out.println("Booking cancelled.");
        }
    }
    
    private boolean isValidSeatFormat(String seatNo) {
        if (seatNo == null || seatNo.length() < 2) return false;
        
        // First character should be a letter
        char firstChar = seatNo.charAt(0);
        if (!Character.isLetter(firstChar)) return false;
        
        // The rest should be numbers
        try {
            String numberPart = seatNo.substring(1);
            int seatNumber = Integer.parseInt(numberPart);
            
            // Validate that seat number is positive
            if (seatNumber <= 0) return false;
            
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private void cancelTicket() throws SQLException {
        int ticketId = getIntInput("Enter ticket ID to cancel: ");
        boolean success = bookingService.cancelTicket(ticketId);
        
        if (success) {
            System.out.println("Ticket cancelled successfully.");
        } else {
            System.out.println("Failed to cancel ticket. It may not exist or already be cancelled.");
        }
    }
    
    private int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                return input;
            } else {
                System.out.println("Please enter a valid number.");
                scanner.next(); // Clear invalid input
            }
        }
    }
    
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
    
    public static void main(String[] args) {
        MainApp app = new MainApp();
        app.start();
    }
}