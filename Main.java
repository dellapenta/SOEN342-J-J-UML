import UserManagement.*;
import OfferingManagement.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Offering> offerings = new ArrayList<>();
    private static List<Instructor> instructors = new ArrayList<>();
    private static List<Client> clients = new ArrayList<>();
    private static Administrator admin;

    public static void main(String[] args) {
        // Initialize admin and pre-populate some data
        admin = new Administrator("Admin", "admin@example.com", "securepassword");
        prepopulateData();

        // Start the main application loop with role selection
        roleSelectionMenu();
    }

    private static void roleSelectionMenu() {
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
                    adminMenu();
                    break;
                case 2:
                    instructorMenu();
                    break;
                case 3:
                    clientMenu();
                    break;
                case 4:
                    publicMenu();
                    break;
                case 5:
                    registerInstructor();
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

    private static void adminMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n*** Administrator Menu ***");
            System.out.println("1. Create Offering");
            System.out.println("2. Update Offering");
            System.out.println("3. Remove Offering");
            System.out.println("4. View Offerings");
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
                    System.out.print("Enter location name: ");
                    String locationName = scanner.nextLine();
                    System.out.print("Enter city: ");
                    String city = scanner.nextLine();
                    System.out.print("Enter capacity: ");
                    int capacity = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    Location location = new Location(locationName, city, capacity);

                    System.out.print("Enter start date (yyyy-mm-dd): ");
                    LocalDate startDate = LocalDate.parse(scanner.nextLine());
                    System.out.print("Enter end date (yyyy-mm-dd): ");
                    LocalDate endDate = LocalDate.parse(scanner.nextLine());
                    System.out.print("Enter start time (HH:mm): ");
                    LocalTime startTime = LocalTime.parse(scanner.nextLine());
                    System.out.print("Enter end time (HH:mm): ");
                    LocalTime endTime = LocalTime.parse(scanner.nextLine());
                    System.out.print("Enter day of the week: ");
                    String dayOfWeek = scanner.nextLine();

                    Schedule schedule = new Schedule(startDate, endDate, startTime, endTime, dayOfWeek);

                    // Delegate to Administrator to create offering
                    admin.createOffering(offerings, name, type, location, schedule);
                    break;

                    case 2:
                    // Input handling for updating an offering
                    if (offerings.isEmpty()) {
                        System.out.println("No offerings to update.");
                    } else {
                        System.out.println("Select offering to update:");
                        int index = 1; // To keep track of the index for user display
                        for (Offering offering : offerings) {
                            System.out.println(offering.getOfferingId() + ". " + offering.getName());
                        }
                        int selection = scanner.nextInt();
                        scanner.nextLine();  // Consume newline
                
                        Offering offeringToUpdate = null;  // Initialize variable to hold selected offering
                        for (Offering offering : offerings) {
                            if (offering.getOfferingId() == selection) { // Compare with the selected ID
                                offeringToUpdate = offering;
                                break;  // Exit loop once found
                            }
                        }
                
                        if (offeringToUpdate != null) {
                            System.out.print("Enter new offering type: ");
                            String newType = scanner.nextLine();
                    
                            // Delegate to Administrator to update offering
                            admin.updateOffering(offerings, offeringToUpdate, newType);
                        } else {
                            System.out.println("No offering found with ID: " + selection);
                        }
                    }
                    break;
                
                case 3:
                    // Input handling for removing an offering
                    if (offerings.isEmpty()) {
                        System.out.println("No offerings to remove.");
                    } else {
                        System.out.println("Select offering to remove:");
                        for (Offering offering : offerings) {
                            System.out.println(offering.getOfferingId() + ". " + offering.getName()); // Display the offering name
                        }
                        int selection = scanner.nextInt();
                        scanner.nextLine();  // Consume newline
                        
                        Offering offeringToRemove = null; // Initialize variable to hold selected offering
                        for (Offering offering : offerings) {
                            if (offering.getOfferingId() == selection) { // Compare with the selected ID
                                offeringToRemove = offering;
                                break;  // Exit loop once found
                            }
                        }
                
                        if (offeringToRemove != null) {
                            // Delegate to Administrator to remove offering
                            admin.removeOffering(offerings, offeringToRemove);
                        } else {
                            System.out.println("No offering found with ID: " + selection);
                        }
                    }
                    break;

                case 4:
                    // View offerings
                    if (offerings.isEmpty()) {
                        System.out.println("No offerings available.");
                    } else {
                        System.out.println("\nAvailable Offerings:");
                        for (Offering offering : offerings) {
                            System.out.println("- " + offering.getOfferingId() + ". " + offering.getName() + " (" + offering.getOfferingType() + " )");
                        }
                    }
                    break;

                case 0:
                    // Back to role selection
                    break;

                default:
                    System.out.println("Invalid choice! Please select a valid option.");
            }
        } while (choice != 0);
    }

    private static void instructorMenu() {
        Scanner scanner = new Scanner(System.in);

        // Prompt for instructor identity selection
        System.out.println("\n*** Select Instructor Identity ***");
        for (int i = 0; i < instructors.size(); i++) {
            System.out.println((i + 1) + ". " + instructors.get(i).getName());
        }
        System.out.print("Select an instructor by number: ");
        int instructorChoice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (instructorChoice < 1 || instructorChoice > instructors.size()) {
            System.out.println("Invalid selection. Returning to role selection.");
            return;
        }

        Instructor selectedInstructor = instructors.get(instructorChoice - 1);
        System.out.println("You are now acting as: " + selectedInstructor.getName());

        int choice;

        do {
            System.out.println("\n*** Instructor Menu ***");
            System.out.println("1. Select Offering");
            System.out.println("0. Back to Role Selection");
            System.out.print("Select an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            System.err.println();

            switch (choice) {
                case 1:
                    // Instructor selects an offering
                    if (offerings.isEmpty()) {
                        System.out.println("No offerings available to select.");
                    } else {
                        System.out.println("Select an offering by number:");
                        for (Offering offering : offerings) {
                            System.out.println(offering.getOfferingId() + ". " + offering.getName() + " (" + offering.getLocation().getAddress() + ")");
                        }

                        System.out.print("Enter the number of the offering: ");
                        int offeringIndex = scanner.nextInt();
                        scanner.nextLine();  // Consume the newline

                        // Validate the input to ensure a valid offering index is selected
                        if (offeringIndex < 1 || offeringIndex > offerings.size()) {
                            System.out.println("Invalid offering selection.");
                        } else {
                            Offering offeringToSelect = null;
    
                            // Find the selected offering
                            for (Offering offering : offerings) {
                                if (offering.getOfferingId() == offeringIndex) {
                                    offeringToSelect = offering;
                                    break;
                                }
                            }
    
                            if (offeringToSelect != null) {
                                selectedInstructor.selectOffering(offeringToSelect);
                            } else {
                                System.out.println("Offering selection failed.");
                            }
                        }
                    }
                    break;

                case 0:
                    // Back to role selection
                    break;

                default:
                    System.out.println("Invalid choice! Please select a valid option.");
            }
        } while (choice != 0);
    }

    private static void clientMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n*** Select Client Identity ***");
        for (int i = 0; i < clients.size(); i++) {
            System.out.println((i + 1) + ". " + clients.get(i).getName());
        }
        System.out.print("Select an client by number: ");
        int ClientChoice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (ClientChoice < 1 || ClientChoice > clients.size()) {
            System.out.println("Invalid selection. Returning to role selection.");
            return;
        }

        Client selectedClient = clients.get(ClientChoice - 1);
        System.out.println("You are now acting as: " + selectedClient.getName());

        int choice;

        do {
            System.out.println("\n*** Client Menu ***");
            System.out.println("1. Book an Offering");
            System.out.println("2. View My Bookings");
            System.out.println("3. Cancel Booking");
            System.out.println("0. Back to Role Selection");

            System.out.print("Select an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Book an offering
                    if (offerings.isEmpty()) {
                        System.out.println("No offerings.");
                    } else {
                        publicMenu(); // Display available offerings
                        System.out.print("Enter the number of the offering to book: ");
                        int offeringIndex = scanner.nextInt();
                        scanner.nextLine();  // Consume the newline

                        // Validate the input to ensure a valid offering index is selected
                        if (offeringIndex < 1 || offeringIndex > offerings.size()) {
                            System.out.println("Invalid offering selection.");
                        } else {
                            Offering selectedOffering = null;
                            // Find the selected offering
                            for (Offering offering : offerings) {
                                if (offering.getOfferingId() == offeringIndex) {
                                    selectedOffering = offering;
                                    break;
                                }
                            }

                            if (selectedOffering != null && selectedOffering.isAvailable()) {
                                selectedClient.bookOffering(selectedOffering);
                            } else {
                                System.out.println("Offering selection failed.");
                            }
                            
                        }
                    }
                    break;

                case 2:
                    // View my bookings
                    selectedClient.viewBookings();
                    break;

                case 3:
                    // Cancel booking
                    if (selectedClient.getBookings().isEmpty()) {
                        System.out.println("No bookings.");
                    } else {
                        selectedClient.viewBookings();
                        System.out.print("Enter the number of the booking to cancel: ");
                        int bookingIndex = scanner.nextInt();
                        scanner.nextLine();  // Consume the newline

                        // Validate the input to ensure a valid offering index is selected
                        if (bookingIndex < 1 || bookingIndex > selectedClient.getBookings().size()) {
                            System.out.println("Invalid booking selection.");
                        } else {
                            selectedClient.cancelBooking(selectedClient.getBookings().get(bookingIndex - 1));
                        }
                    }
                    break;

                case 0:
                    // Back to role selection
                    break;

                default:
                    System.out.println("Invalid choice! Please select a valid option.");
            }
        } while (choice != 0);
    }

    private static void publicMenu() {
        boolean hasInstructorAssigned = false;

        System.out.println("\nAvailable Offerings:");
        for (Offering offering : offerings) {
            if (offering.isAvailable() && offering.getInstructor() != null) {
                hasInstructorAssigned = true;
                System.out.println(offering.getOfferingId() + ". " + offering.getName() 
                    + " (" + offering.getOfferingType() + ")" 
                    + " (" + offering.getLocation().getName() + ")" 
                    + " (" + offering.getSchedule().getStartDate() + " to " 
                    + offering.getSchedule().getEndDate() + ")" 
                    + " Instructor: " + offering.getInstructor().getName());
            }
        }

        // If no available offerings were found, display a message
        if (!hasInstructorAssigned) {
            System.out.println("No offerings available.");
        }
    }

    private static void registerInstructor() {
        Scanner scanner = new Scanner(System.in);
    
        System.out.println("\n*** Instructor Registration ***");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your contact number: ");
        String contactNumber = scanner.nextLine();
        System.out.print("Enter your specialty (e.g., Yoga, Boxing): ");
        String specialty = scanner.nextLine();
        System.out.print("Enter your availabile locations:1 ");
        String availability = scanner.nextLine();
    
        // Create new Instructor object and add to instructors list
        Instructor newInstructor = new Instructor(name, contactNumber, specialty, availability);
        instructors.add(newInstructor);
    
        System.out.println("Registration successful! Welcome, " + newInstructor.getName() + ".");
    }

    private static void registerClient() {
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
            System.out.println("Enter your guardian age: ");
            int guardianAge = scanner.nextInt();
            scanner.nextLine();  // Consume the newline
            Client guardian = null;
            for (Client client : clients) {
                if (client.getEmail().equalsIgnoreCase(guardianEmail)) {
                    guardian = client;
                    break;
                }
            }

            // If guardian not found, create a new guardian client and add to the list
            if (guardian == null) {
                guardian = new Client(guardianName, guardianContactNumber, guardianEmail, guardianAge);
                clients.add(guardian);
            }

            // Register the new client with the found or created guardian
            newClient = new Client(name, contactNumber, email, age, guardian);
        } else {
            // Register a new client who is 18 or older
            newClient = new Client(name, contactNumber, email, age);
        }

        clients.add(newClient);
        System.out.println("Registration successful! Welcome, " + newClient.getName() + ".");
        return;
        
    }

    // Prepopulate with sample data for testing
    private static void prepopulateData() {
        // Sample locations
        Location location1 = new Location("EV Building Room 7", "Montreal", 20);
        Location location2 = new Location("Downtown Fitness Center", "Montreal", 15);
        Location location3 = new Location("Community Gym", "Laval", 25);
        Location location4 = new Location("Sports Complex", "Quebec City", 30);
        Location location5 = new Location("Health Hub", "Ottawa", 10);
    
        // Sample schedules
        Schedule schedule1 = new Schedule(
                LocalDate.of(2024, 9, 1),
                LocalDate.of(2024, 11, 30),
                LocalTime.of(12, 0),
                LocalTime.of(15, 0),
                "Sunday"
        );
    
        Schedule schedule2 = new Schedule(
                LocalDate.of(2024, 10, 1),
                LocalDate.of(2024, 12, 15),
                LocalTime.of(10, 0),
                LocalTime.of(12, 0),
                "Saturday"
        );
    
        Schedule schedule3 = new Schedule(
                LocalDate.of(2024, 11, 5),
                LocalDate.of(2025, 1, 20),
                LocalTime.of(18, 0),
                LocalTime.of(20, 0),
                "Wednesday"
        );
    
        Schedule schedule4 = new Schedule(
                LocalDate.of(2024, 8, 20),
                LocalDate.of(2024, 10, 20),
                LocalTime.of(9, 0),
                LocalTime.of(11, 0),
                "Monday"
        );
    
        Schedule schedule5 = new Schedule(
                LocalDate.of(2024, 7, 15),
                LocalDate.of(2024, 9, 15),
                LocalTime.of(13, 0),
                LocalTime.of(14, 30),
                "Friday"
        );
    
        // Sample offerings
        Offering offering1 = new Offering("Judo Class", "Group", location1, schedule1);
        Offering offering2 = new Offering("Yoga Session", "Individual", location2, schedule2);
        Offering offering3 = new Offering("Boxing Training", "Group", location3, schedule3);
        Offering offering4 = new Offering("Pilates Class", "Group", location1, schedule2);
        Offering offering5 = new Offering("CrossFit", "Individual", location3, schedule1);
        Offering offering6 = new Offering("Swimming Lessons", "Group", location4, schedule4);
        Offering offering7 = new Offering("Karate for Beginners", "Group", location5, schedule5);
        Offering offering8 = new Offering("Advanced Pilates", "Individual", location2, schedule3);
        Offering offering9 = new Offering("Zumba Class", "Group", location1, schedule4);
        Offering offering10 = new Offering("Kickboxing Basics", "Individual", location5, schedule2);
        Offering offering11 = new Offering("Personal Training", "Individual", location3, schedule5);
        Offering offering12 = new Offering("Strength Training", "Group", location4, schedule1);
        Offering offering13 = new Offering("Dance Fitness", "Group", location2, schedule4);
        Offering offering14 = new Offering("Meditation & Relaxation", "Individual", location5, schedule3);
    
        // Add offerings to list
        offerings.add(offering1);
        offerings.add(offering2);
        offerings.add(offering3);
        offerings.add(offering4);
        offerings.add(offering5);
        offerings.add(offering6);
        offerings.add(offering7);
        offerings.add(offering8);
        offerings.add(offering9);
        offerings.add(offering10);
        offerings.add(offering11);
        offerings.add(offering12);
        offerings.add(offering13);
        offerings.add(offering14);
    
        // Sample instructors
        Instructor instructor1 = new Instructor("Grace", "5141234567", "Judo", "Montreal, Laval");
        Instructor instructor2 = new Instructor("Alex", "5142345678", "Yoga", "Montreal");
        Instructor instructor3 = new Instructor("Jordan", "5143456789", "Boxing", "Laval, Quebec City");
        Instructor instructor4 = new Instructor("Taylor", "5144567890", "Pilates", "Quebec City, Ottawa");
        Instructor instructor5 = new Instructor("Morgan", "5145678901", "CrossFit", "Montreal");
        Instructor instructor6 = new Instructor("Jamie", "5146789012", "Swimming", "Ottawa");
        Instructor instructor7 = new Instructor("Parker", "5147890123", "Karate", "Quebec City");
        Instructor instructor8 = new Instructor("Sam", "5148901234", "Dance Fitness", "Laval, Montreal");
    
        // Add instructors to list
        instructors.add(instructor1);
        instructors.add(instructor2);
        instructors.add(instructor3);
        instructors.add(instructor4);
        instructors.add(instructor5);
        instructors.add(instructor6);
        instructors.add(instructor7);
        instructors.add(instructor8);
    
        // Optionally assign instructors to offerings
        offering1.assignInstructor(instructor1);
        offering2.assignInstructor(instructor2);
        offering3.assignInstructor(instructor3);
        offering4.assignInstructor(instructor4);
        offering5.assignInstructor(instructor5);
        offering6.assignInstructor(instructor6);
        offering7.assignInstructor(instructor7);
        offering9.assignInstructor(instructor8);

        // Sample Clients
        Client client1 = new Client("Alice Smith", "123-456-7890", "alice@example.com", 28);
        client1.getBookings().add(offering1);

        Client client2 = new Client("Bob Johnson", "987-654-3210", "bob@example.com", 35);
        client2.getBookings().add(offering2);

        Client client3 = new Client("Catherine Lee", "456-789-1230", "catherine@example.com", 32);
        client3.getBookings().add(offering3);

        Client client4 = new Client("David Brown", "789-123-4567", "david@example.com", 42);


        Client client5 = new Client("Emma Davis", "321-654-9870", "emma@example.com", 26);

        // Add clients to list
        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        clients.add(client4);
        clients.add(client5);
    }
}