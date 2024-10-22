import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Flight class to represent a flight
class Flight {
    private String flightNumber; // Flight number
    private String destination;   // Destination of the flight
    private int availableSeats;   // Number of available seats

    // Constructor to initialize flight details
    public Flight(String flightNumber, String destination, int availableSeats) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.availableSeats = availableSeats;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDestination() {
        return destination;
    }

    // Method to book a seat; decreases available seats
    public boolean bookSeat() {
        if (availableSeats > 0) {
            availableSeats--;
            return true; // Booking successful
        }
        return false; // Booking failed (no available seats)
    }

    // Method to cancel a seat; increases available seats
    public void cancelSeat() {
        availableSeats++;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    @Override
    public String toString() {
        return "Flight " + flightNumber + " to " + destination + " has " + availableSeats + " seats available.";
    }
}

// User class to represent a user
class User {
    private String username; // Username for login
    private String password; // Password for login

    // Constructor to initialize user details
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    // Method to check if the provided password matches
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}

// Main class for the Airplane Reservation System
public class AirplaneReservationSystem {
    // List to hold available flights
    private static final List<Flight> flights = new ArrayList<>();
    // Map to hold registered users
    private static final Map<String, User> users = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initializeFlights(); // Initialize flight data

        while (true) {
            // Display main menu options
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1: // Register a new user
                    registerUser(scanner);
                    break;
                case 2: // Login an existing user
                    loginUser(scanner);
                    break;
                case 3: // Exit the application
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Method to initialize flights
    private static void initializeFlights() {
        flights.add(new Flight("101", "USA", 50)); // Flight to USA
        flights.add(new Flight("202", "London", 30)); // Flight to London
        flights.add(new Flight("303", "Delhi", 10)); // Flight to Delhi
    }

    // Method to register a new user
    private static void registerUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        users.put(username, new User(username, password)); // Store user details
        System.out.println("User registered: " + username);
    }

    // Method to handle user login
    private static void loginUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        User user = users.get(username); // Fetch user details

        // Check if login credentials are valid
        if (user != null && user.checkPassword(password)) {
            System.out.println("Login successful for: " + username);
            manageReservations(scanner); // Proceed to reservation management
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    // Method to manage flight reservations
    private static void manageReservations(Scanner scanner) {
        while (true) {
            // Display available flights
            System.out.println("\nAvailable Flights:");
            flights.forEach(System.out::println);
            System.out.print("Enter flight number to book, or 'cancel' to cancel a reservation, or 'exit' to logout: ");
            String input = scanner.nextLine();

            // Check user input
            if (input.equalsIgnoreCase("exit")) {
                break; // Logout
            } else if (input.equalsIgnoreCase("cancel")) {
                System.out.print("Enter flight number to cancel: ");
                String flightNumber = scanner.nextLine();
                cancelReservation(flightNumber); // Cancel a reservation
            } else {
                bookReservation(input); // Book a reservation
            }
        }
    }

    // Method to book a flight reservation
    private static void bookReservation(String flightNumber) {
        Flight selectedFlight = flights.stream()
                .filter(flight -> flight.getFlightNumber().equalsIgnoreCase(flightNumber))
                .findFirst()
                .orElse(null); // Find the flight

        // Check if the flight is valid and has available seats
        if (selectedFlight != null && selectedFlight.getAvailableSeats() > 0) {
            if (selectedFlight.bookSeat()) {
                System.out.println("Booking successful for flight: " + flightNumber);
                System.out.println(selectedFlight.getAvailableSeats() + " seats remaining."); // Show remaining seats
            }
        } else {
            System.out.println("Invalid flight number or no available seats.");
        }
    }

    // Method to cancel a flight reservation
    private static void cancelReservation(String flightNumber) {
        Flight selectedFlight = flights.stream()
                .filter(flight -> flight.getFlightNumber().equalsIgnoreCase(flightNumber))
                .findFirst()
                .orElse(null); // Find the flight

        // Check if the flight is valid
        if (selectedFlight != null) {
            selectedFlight.cancelSeat(); // Cancel the seat
            System.out.println("Cancellation successful for flight: " + flightNumber);
            System.out.println(selectedFlight.getAvailableSeats() + " seats available after cancellation."); // Show remaining seats
        } else {
            System.out.println("Invalid flight number.");
        }
    }
}

