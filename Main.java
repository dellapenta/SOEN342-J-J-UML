import OfferingManagement.*;
import UserManagement.*;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {


    public static void main(String[] args) {
        // SQLite connection string
        DatabaseInitializer.initializeDatabase();

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:my.db")) {
            // Start the main application loop with role selection
        roleSelectionMenu(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private static void roleSelectionMenu(Connection conn) throws SQLException {
        
        Scanner scanner = new Scanner(System.in);
        int roleChoice;

        do {
            System.out.println("\n*** Select Your Role ***");
            System.out.println("1. Administrator");
            System.out.println("2. Instructor");
            System.out.println("3. Client");
            System.out.println("4. Public");
            System.out.println("5. Register as a New Instructor");
            System.out.println("6. Register as a New Client");
            System.out.println("0. Exit");
            System.out.print("Select your role: ");
            roleChoice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (roleChoice) {
                case 1:
                    adminMenu(conn);
                    break;
                case 2:
                    instructorMenu(conn);
                    break;
                case 3:
                    clientMenu(conn);
                    break;
                case 4:
                    publicMenu(conn);
                    break;
                case 5:
                    registerInstructor(conn);
                    break;
                case 6:
                    registerClient(conn);
                    break;
                case 0:
                    System.out.println("Exiting the system...");
                    break;
                default:
                    System.out.println("Invalid role! Please select a valid option.");
            }
        } while (roleChoice != 0);

        scanner.close();
    }


    // Administrator menu
    private static void adminMenu(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n*** Administrator Menu ***");
            System.out.println("1. Create Lesson");
            System.out.println("2. Remove Lesson");
            System.out.println("3. Remove Client");
            System.out.println("4. Remove Instructor");
            System.out.println("5. View Lessons");
            System.out.println("6. View Offerings");
            System.out.println("7. View Clients");
            System.out.println("8. View Instructors");
            System.out.println("9. View Bookings");
            System.out.println("0. Back to Role Selection");
            System.out.print("Select an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            
            switch (choice) {
                case 1:
                    // Input handling for creating an offering (same as before)
                    System.out.print("Enter offering name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter offering Type: ");
                    String type = scanner.nextLine();
                    System.out.print("Enter the capacity: ");
                    int capacity = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter location name: ");
                    String locationName= scanner.nextLine();
                    System.out.print("Enter city: ");
                    String city = scanner.nextLine();
                    int locationId = Location.saveToDatabase(conn, locationName, city);

                    System.out.print("Enter start date (yyyy-mm-dd): ");
                    String startDateInput = scanner.nextLine();
                    System.out.print("Enter end date (yyyy-mm-dd): ");
                    String endDateInput = scanner.nextLine();
                    System.out.print("Enter start time (HH:mm): ");
                    String startTime = scanner.nextLine();
                    System.out.print("Enter end time (HH:mm): ");
                    String endTime = scanner.nextLine();
                    System.out.print("Enter day of the week: ");
                    String dayOfWeek = scanner.nextLine();

                    // Parse start and end dates as LocalDate
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate startDate = LocalDate.parse(startDateInput, formatter);
                    LocalDate endDate = LocalDate.parse(endDateInput, formatter);

                    String timeSlot = startTime + " - " + endTime;
                    int scheduleId = Schedule.saveToDatabase(conn,startDate, endDate, timeSlot, dayOfWeek);

                    // Delegate to Administrator to create lesson
                    Administrator.createLesson(conn ,name, type, capacity, locationId, scheduleId);
                    break;

                case 2:
                    // Input handling for removing an lesson
                    Administrator.viewLessons(conn);
                    System.out.println("Select lesson to remove:");
                    int selection = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    Administrator.removeLesson(conn, selection);

                    break;

                case 3:
                    // Input handling for removing a client
                    
                    Administrator.viewClients(conn);
                    System.out.println("Select client to remove:");
                    int clientId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    Administrator.removeClient(conn, clientId);
                    break;

                case 4:
                    // Input handling for removing an instructor
                    Administrator.viewInstructors(conn);
                    System.out.println("Select Instructor to remove:");
                    int instructorId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    Administrator.removeInstructor(conn, instructorId);
                    break;

                case 5:
                    // View lessons
                    Administrator.viewLessons(conn);
                    break;

                case 6:
                    // View offerings
                    Administrator.viewOfferings(conn);
                    break;

                case 7:
                    // View Clients
                    Administrator.viewClients(conn);
                    break;

                case 8:
                    // View Instructors
                    Administrator.viewInstructors(conn);
                    break;

                case 9:
                    // View Bookings
                    Administrator.viewBookings(conn);
                    break;

                case 0:
                    // Back to role selection
                    break;

                default:
                    System.out.println("Invalid choice! Please select a valid option.");
            }
        } while (choice != 0);
    }


    // Instructor menu
    private static void instructorMenu(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        // Prompt for instructor identity selection
        System.out.println("\n*** Select Instructor Identity ***");
        Administrator.viewInstructors(conn);
        System.out.print("Select an instructor by number: ");
        int instructorChoice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        int choice;

        do {
            System.out.println("\n*** Instructor Menu ***");
            System.out.println("1. Create Offering");
            System.out.println("2. Cancel Offering");
            System.out.println("3. View My Offerings");
            System.out.println("0. Back to Role Selection");
            System.out.print("Select an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            System.err.println();

            switch (choice) {
                case 1:
                    // Instructor selects an lesson
                    Administrator.viewLessons(conn);
                    System.out.print("Select a lesson by number: ");
                    int lessonChoice = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    Instructor.createOffering(conn, instructorChoice, lessonChoice);
                    break;
                case 2:
                    // cancel an offering
                    Instructor.viewOfferings(conn, instructorChoice);
                    System.out.print("Select an offering by number: ");
                    int offeringChoice = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    Instructor.cancelOffering(conn, instructorChoice, offeringChoice);
                    break;

                case 3:
                    // View my offerings
                    Instructor.viewOfferings(conn, instructorChoice);

                case 0:
                    // Back to role selection
                    break;

                default:
                    System.out.println("Invalid choice! Please select a valid option.");
            }
        } while (choice != 0);
    }


    // Client menu
    private static void clientMenu(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n*** Select Client Identity ***");
        Administrator.viewClients(conn);
        System.out.print("Select a client by number: ");
        int ClientChoice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        int childId = Client.getChild(conn, ClientChoice);
        String childName = Client.getChildName(conn, ClientChoice);

        int choice;

        do {
            System.out.println("\n*** Client Menu ***");
            System.out.println("1. Book an Offering");
            System.out.println("2. View My Bookings");
            System.out.println("3. Cancel Booking");
            if (childId != -1) {
                System.out.println("4. Book an Offering for " + childName);
                System.out.println("5. View My Bookings for " + childName);
                System.out.println("6. Cancel Booking for " + childName);
            }
            System.out.println("0. Back to Role Selection");

            System.out.print("Select an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Book an offering
                    Administrator.viewOfferings(conn);
                    System.out.print("Select an offering by number: ");
                    int offeringChoice = scanner.nextInt();
                    scanner.nextLine();

                    Client.bookingOffering(conn, ClientChoice, offeringChoice);
                    break;

                case 2:
                    // View my bookings
                    Client.viewBookings(conn, ClientChoice);
                    break;

                case 3:
                    // Cancel booking
                    Client.viewBookings(conn, ClientChoice);
                    System.out.print("Select a booking by number: ");
                    int bookingChoice = scanner.nextInt();
                    scanner.nextLine();

                    Client.cancelBooking(conn, ClientChoice, bookingChoice);

                    break;

                 case 4:
                     // Book for guardian's bookings
                     if (childId == -1) {
                         System.out.println("Invalid choice! Please select a valid option.");
                         break;
                     }
                     Administrator.viewOfferings(conn);
                     System.out.print("Select an offering by number: ");
                     int offeringSelected = scanner.nextInt();
                     scanner.nextLine();

                     Client.bookingOffering(conn, childId, offeringSelected);
                     break;

                 case 5:
                     // View guardian's bookings
                     if (childId == -1) {
                         System.out.println("Invalid choice! Please select a valid option.");
                         break;
                     }
                     Client.viewBookings(conn, childId);
                     break;

                 case 6:
                     // Cancel guardian's bookings
                     if (childId == -1) {
                         System.out.println("Invalid choice! Please select a valid option.");
                         break;
                     }
                     // Cancel booking
                     Client.viewBookings(conn, childId);
                     System.out.print("Select a booking by number: ");
                     int bookingSelected = scanner.nextInt();
                     scanner.nextLine();

                     Client.cancelBooking(conn, childId, bookingSelected);
                     break;

                case 0:
                    // Back to role selection
                    break;

                default:
                    System.out.println("Invalid choice! Please select a valid option.");
            }
        } while (choice != 0);
    }


    // Public menu
    private static void publicMenu(Connection conn) {
        Public.viewPublicOfferings(conn);
    }


    // Instructor registration
    private static void registerInstructor(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);
    
        System.out.println("\n*** Instructor Registration ***");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your contact number: ");
        String contactNumber = scanner.nextLine();
        System.out.print("Enter your specialty (e.g., Yoga, Boxing): ");
        String specialty = scanner.nextLine();
        System.out.print("Enter your availabile Cities (separated by commas): ");
        String availability = scanner.nextLine();


        Instructor.saveToDatabase(conn, name, contactNumber, specialty, availability);
    }


    // Client registration
    private static void registerClient(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Client newClient;

        System.out.println("\n***Client Registration ***");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your contact number: ");
        String contactNumber = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your age: ");
        int age = scanner.nextInt();
        if (age < 18) {
            System.out.println("Your age is below 18, guardian information is required.");
            scanner.nextLine();  // Consume the newline
            System.out.print("Enter your guardian name: ");
            String guardianName = scanner.nextLine();
            System.out.print("Enter your guardian contact number: ");
            String guardianContactNumber = scanner.nextLine();
            System.out.print("Enter your guardian email: ");
            String guardianEmail = scanner.nextLine();
            System.out.print("Enter your guardian age: ");
            int guardianAge = scanner.nextInt();
            scanner.nextLine();  // Consume the newline
            Client guardian = null;
            int guardianId = Client.saveToDatabase(conn, guardianName, guardianContactNumber, guardianEmail, guardianAge, -1);
            Client.saveToDatabase(conn, name, contactNumber, email, age, guardianId);
        } else {
            // Register a new client who is 18 or older
            Client.saveToDatabase(conn, name, contactNumber, email, age, -1);
        }

        return;

    }

}





