import OfferingManagement.*;
import UserManagement.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Lesson> lessons = new ArrayList<>();
    private static List<Offering> offerings = new ArrayList<>();
    private static List<Instructor> instructors = new ArrayList<>();
    private static List<Client> clients = new ArrayList<>();
    private static List<Booking> bookings = new ArrayList<>();
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
                case 6:
                    registerClient();
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
    private static void adminMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n*** Administrator Menu ***");
            System.out.println("1. Create Lesson");
            System.out.println("2. Reomve Lesson");
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
                    System.out.println("Enter the capacity:");
                    int capacity = scanner.nextInt();
                    System.out.print("Enter location name: ");
                    String locationName = scanner.nextLine();
                    System.out.print("Enter city: ");
                    String city = scanner.nextLine();
                    scanner.nextLine();  // Consume newline
                    Location location = new Location(locationName, city);

                    System.out.print("Enter start date (yyyy-mm-dd): ");
                    LocalDate startDate = LocalDate.parse(scanner.nextLine());
                    System.out.print("Enter end date (yyyy-mm-dd): ");
                    LocalDate endDate = LocalDate.parse(scanner.nextLine());
                    System.out.print("Enter start time (HH:mm): ");
                    String startTime = scanner.nextLine();
                    System.out.print("Enter end time (HH:mm): ");
                    String endTime = scanner.nextLine();
                    System.out.print("Enter day of the week: ");
                    String dayOfWeek = scanner.nextLine();

                    String timeSlot = startTime + " - " + endTime;
                    Schedule schedule = new Schedule(startDate, endDate, timeSlot, dayOfWeek);

                    // Delegate to Administrator to create lesson
                    admin.createLesson(lessons, name, type, capacity, location, schedule);
                    break;

                case 2:
                    // Input handling for removing an lesson
                    if (lessons.isEmpty()) {
                        System.out.println("No lessons to remove.");
                    } else {
                        System.out.println("Select lesson to remove:");
                        for (Lesson lesson : lessons) {
                            System.out.println(lesson.getLessonId() + ". " + lesson.getName()); // Display the lesson name
                        }
                        int selection = scanner.nextInt();
                        scanner.nextLine();  // Consume newline
                        
                        Lesson lessonToRemove = null; // Initialize variable to hold selected lesson
                        for (Lesson lesson : lessons) {
                            if (lesson.getLessonId() == selection) { // Compare with the selected ID
                                lessonToRemove = lesson;
                                break;  // Exit loop once found
                            }
                        }
                
                        if (lessonToRemove != null) {
                            // Delegate to Administrator to remove lesson
                            admin.removeLesson(lessons, lessonToRemove);
                            for (Offering offering : offerings) {
                                if (offering.getLesson() == lessonToRemove) {
                                    offerings.remove(offering);
                                }
                            }
                        } else {
                            System.out.println("No offering found with ID: " + selection);
                        }
                    }
                    break;

                case 3:
                    // Input handling for removing a client
                    if (clients.isEmpty()) {
                        System.out.println("No clients to remove.");
                    } else {
                        System.out.println("Select client to remove:");
                        for (Client client : clients) {
                            System.out.println("- " + client.getUserId() + ". " + client.getName());
                        }
                        int clientId = scanner.nextInt();
                        scanner.nextLine();
                        admin.removeUser(clients, clientId);
                    }
                    break;

                case 4:
                    // Input handling for removing an instructor
                    if (instructors.isEmpty()) {
                        System.out.println("No instructors to remove.");
                    } else {
                        System.out.println("Select instructor to remove:");
                        for (Instructor instructor : instructors) {
                            System.out.println("- " + instructor.getUserId() + ". " + instructor.getName());
                        }
                        int instructorId = scanner.nextInt();
                        scanner.nextLine();
                        admin.removeUser(instructors, instructorId);
                    }
                    break;

                case 5:
                    // View lessons
                    if (lessons.isEmpty()) {
                        System.out.println("No lessons available.");
                    } else {
                        System.out.println("\nAvailable Lessons:");
                        for (Lesson lesson : lessons) {
                            System.out.println("- " + lesson.getName() + " (" + lesson.getLessonType() + " )");
                        }
                    }
                    break;

                case 6:
                    // View offerings
                    if (offerings.isEmpty()) {
                        System.out.println("No offerings available.");
                    } else {
                        System.out.println("\nAvailable Offerings:");
                        for (Offering offering : offerings) {
                            System.out.println("- " + offering.getOfferingId() + ". " + offering.getLesson().getName() + " (" + offering.getLesson().getLessonType() + " )");
                        }
                    }
                    break;

                case 7:
                    // View Clients
                    if (clients.isEmpty()) {
                        System.out.println("No clients available.");
                    } else {
                        System.out.println("\nAvailable Clients:");
                        for (Client client : clients) {
                            System.out.println("- " + client.getName());
                        }
                    }
                    break;

                case 8:
                    // View Instructors
                    if (instructors.isEmpty()) {
                        System.out.println("No instructors available.");
                    } else {
                        System.out.println("\nAvailable Instructors:");
                        for (Instructor instructor : instructors) {
                            System.out.println("- " + instructor.getName());
                        }
                    }
                    break;

                case 9:
                    // View Bookings
                    if (bookings.isEmpty()) {
                        System.out.println("No bookings available.");
                    } else {
                        System.out.println("\nAll Bookings:");
                        for (Booking booking : bookings) {
                            System.out.println("- " + booking.getOffering().getLesson().getName() + " - " + booking.getUser().getName());
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


    // Instructor menu
    private static void instructorMenu() {
        Scanner scanner = new Scanner(System.in);

        // Prompt for instructor identity selection
        System.out.println("\n*** Select Instructor Identity ***");
        for (int i = 0; i < instructors.size(); i++) {
            System.out.println((i) + ". " + instructors.get(i).getName());
        }
        System.out.print("Select an instructor by number: ");
        int instructorChoice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (instructorChoice < 1 || instructorChoice > instructors.size()) {
            System.out.println("Invalid selection. Returning to role selection.");
            return;
        }

        Instructor selectedInstructor = instructors.get(instructorChoice);
        System.out.println("You are now acting as: " + selectedInstructor.getName());

        int choice;

        do {
            System.out.println("\n*** Instructor Menu ***");
            System.out.println("1. Select Offering");
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
                    if (lessons.isEmpty()) {
                        System.out.println("No lessons available to select.");
                    } else {
                        System.out.println("Select an lesson by number:");
                        for (Lesson lesson : lessons) {
                            System.out.println(lesson.getLessonId() + ". " + lesson.getName() + " (" + lesson.getLocation().getCity() + ")");
                        }

                        System.out.print("Enter the number of the lesson: ");
                        int lessonIndex = scanner.nextInt();
                        scanner.nextLine();  // Consume the newline

                        Lesson lessonToSelect = null;

                        // Find the selected lesson
                        for (Lesson lesson : lessons) {
                            if (lesson.getLessonId() == lessonIndex) {
                                lessonToSelect = lesson;
                                break;
                            }
                        }

                        if (lessonToSelect != null) {
                            Offering newOffering = selectedInstructor.createOffering(lessonToSelect);
                            offerings.add(newOffering);
                        } else {
                            System.out.println("Lesson selection failed.");
                        }
                    }
                    break;
                case 2:
                    // cancel an offering
                    List<Offering> myOfferings = selectedInstructor.viewMyOfferings(offerings);

                    if (myOfferings.isEmpty()) {
                        break;
                    }
                    System.out.print("Enter the number of the offering: ");
                    int offeringIndexToRemove = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline

                    Offering offeringToRemove = null;

                    // Find the cancel to remove
                    for (Offering offering : myOfferings) {
                        if (offering.getOfferingId() == offeringIndexToRemove) {
                            offeringToRemove = offering;
                            selectedInstructor.unassignOffering(offerings, offeringToRemove);
                            break;
                        }
                    }

                    System.out.println("Failed to remove offering.");

                case 3:
                    // View my offerings
                    selectedInstructor.viewMyOfferings(offerings);

                case 0:
                    // Back to role selection
                    break;

                default:
                    System.out.println("Invalid choice! Please select a valid option.");
            }
        } while (choice != 0);
    }


    // Client menu
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
                        Offering selectedOffering = null;
                        // Find the selected offering
                        for (Offering offering : offerings) {
                            if (offering.getOfferingId() == offeringIndex) {
                                selectedOffering = offering;
                                break;
                            }
                        }

                        if (selectedOffering != null && selectedOffering.isAvailable()) {
                            Booking booking =selectedClient.bookOffering(selectedOffering, bookings);
                            bookings.add(booking);
                            System.out.println("Booking " + booking.getId() + " for " + selectedOffering.getLesson().getName() + " has been made by " + selectedClient.getName() + ".");
                        } else {
                            System.out.println("Offering selection failed.");
                        }

                    }
                    break;

                case 2:
                    // View my bookings
                    selectedClient.viewBookings(bookings);
                    break;

                case 3:
                    // Cancel booking
                    List<Booking> myBookings = selectedClient.viewBookings(bookings);

                    if (myBookings.isEmpty()) {
                        break;
                    }

                    System.out.print("Enter the number of the booking to cancel (1 to " + myBookings.size() + "): ");
                    int bookingIndex = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline

                    // Validate the input to ensure a valid booking ID is selected
                    Booking bookingToCancel = null;

                    // Check if the selected booking index corresponds to a valid booking ID
                    for (Booking booking : myBookings) {
                        if (booking.getId() == bookingIndex) {
                            bookingToCancel = booking;
                            break;
                        }
                    }

                    // Check if a valid booking was found
                    if (bookingToCancel != null) {
                        selectedClient.cancelBooking(bookings, bookingToCancel);
                        System.out.println("Booking canceled: " + bookingToCancel.getOffering().getLesson().getName());
                    } else {
                        System.out.println("Invalid booking selection.");
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


    // Public menu
    private static void publicMenu() {
        boolean hasInstructorAssigned = false;

        System.out.println("\nAvailable Offerings:");
        for (Offering offering : offerings) {
            if (offering.isAvailable() && offering.getInstructor() != null) {
                Lesson lesson = offering.getLesson();
                hasInstructorAssigned = true;
                System.out.println(offering.getOfferingId() + ". " + lesson.getName() 
                    + " (" + lesson.getLessonType() + ")" 
                    + " (" + lesson.getLocation().getName() + ")" 
                    + " (" + lesson.getSchedule().getStartDate() + " to " 
                    + lesson.getSchedule().getEndDate()
                    + " at " + lesson.getSchedule().getTimeSlot() + ")"
                    + " Instructor: " + offering.getInstructor().getName());
            }
        }

        // If no available offerings were found, display a message
        if (!hasInstructorAssigned) {
            System.out.println("No offerings available.");
        }
    }


    // Instructor registration
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


    // Client registration
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
                guardian = new Client(guardianName, guardianContactNumber, guardianEmail, guardianAge, null);
                clients.add(guardian);
            }

            // Register the new client with the found or created guardian
            newClient = new Client(name, contactNumber, email, age, guardian);
        } else {
            // Register a new client who is 18 or older
            newClient = new Client(name, contactNumber, email, age, null);
        }

        clients.add(newClient);
        System.out.println("Registration successful! Welcome, " + newClient.getName() + ".");
        return;
        
    }

    // Prepopulate with sample data for testing
    private static void prepopulateData() {
        // Sample locations
        Location location1 = new Location("EV Building Room 7", "Montreal");
        Location location2 = new Location("Downtown Fitness Center", "Montreal");
        Location location3 = new Location("Community Gym", "Laval");
        Location location4 = new Location("Sports Complex", "Quebec City");
        Location location5 = new Location("Health Hub", "Ottawa");
    
        // Sample schedules
        Schedule schedule1 = new Schedule(
                LocalDate.of(2024, 9, 1),
                LocalDate.of(2024, 11, 30),
                "12:00 - 1:00",
                "Sunday"
        );
    
        Schedule schedule2 = new Schedule(
                LocalDate.of(2024, 10, 1),
                LocalDate.of(2024, 12, 15),
                "10:00 - 12:00",
                "Saturday"
        );
    
        Schedule schedule3 = new Schedule(
                LocalDate.of(2024, 11, 5),
                LocalDate.of(2025, 1, 20),
                "18:00 - 20:00",
                "Wednesday"
        );
    
        Schedule schedule4 = new Schedule(
                LocalDate.of(2024, 8, 20),
                LocalDate.of(2024, 10, 20),
                "09:00 - 11:00",
                "Monday"
        );
    
        Schedule schedule5 = new Schedule(
                LocalDate.of(2024, 7, 15),
                LocalDate.of(2024, 9, 15),
                "13:00 - 14:30",
                "Friday"
        );
    
        // Sample lessons
        Lesson lesson1 = new Lesson("Judo Class", "Group", 5, location1, schedule1);
        Lesson lesson2 = new Lesson("Yoga Session", "Private", 1, location2, schedule2);
        Lesson lesson3 = new Lesson("Boxing Training", "Group", 3, location3, schedule3);
        Lesson lesson4 = new Lesson("Pilates Class", "Group", 2, location1, schedule2);
        Lesson lesson5 = new Lesson("CrossFit", "Private", 1, location3, schedule1);
        Lesson lesson6 = new Lesson("Swimming Lessons", "Group", 3, location4, schedule4);
        Lesson lesson7 = new Lesson("Karate for Beginners", "Group", 2, location5, schedule5);
        Lesson lesson8 = new Lesson("Advanced Pilates", "Private", 1, location2, schedule3);
        Lesson lesson9 = new Lesson("Zumba Class", "Group", 3, location1, schedule4);
        Lesson lesson10 = new Lesson("Kickboxing Basics", "Private", 1, location5, schedule2);
        Lesson lesson11 = new Lesson("Personal Training", "Private", 1, location3, schedule5);
        Lesson lesson12 = new Lesson("Strength Training", "Group", 3, location4, schedule1);
        Lesson lesson13 = new Lesson("Dance Fitness", "Group", 4, location2, schedule4);
        Lesson lesson14 = new Lesson("Meditation & Relaxation", "Private", 1, location5, schedule3);
    
        // Add olessons to list
        lessons.add(lesson1);
        lessons.add(lesson2);
        lessons.add(lesson3);
        lessons.add(lesson4);
        lessons.add(lesson5);
        lessons.add(lesson6);
        lessons.add(lesson7);
        lessons.add(lesson8);
        lessons.add(lesson9);
        lessons.add(lesson10);
        lessons.add(lesson11);
        lessons.add(lesson12);
        lessons.add(lesson13);
        lessons.add(lesson14);
    
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
    
        // Sample offerings
        Offering offering1 = new Offering(lesson1, instructor1);
        Offering offering2 = new Offering(lesson2, instructor2);
        Offering offering3 = new Offering(lesson3, instructor3);
        Offering offering4 = new Offering(lesson4, instructor4);
        Offering offering5 = new Offering(lesson5, instructor5);

        // Add offerings to list
        offerings.add(offering1);
        offerings.add(offering2);
        offerings.add(offering3);
        offerings.add(offering4);
        offerings.add(offering5);

        // Sample Clients
        Client client1 = new Client("Alice Smith", "123-456-7890", "alice@example.com", 28, null);
        Client client2 = new Client("Bob Johnson", "987-654-3210", "bob@example.com", 35, null);
        Client client3 = new Client("Catherine Lee", "456-789-1230", "catherine@example.com", 32, null);
        Client client4 = new Client("David Brown", "789-123-4567", "david@example.com", 42, null);
        Client client5 = new Client("Emma Davis", "321-654-9870", "emma@example.com", 26, null);

        // Add clients to list
        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        clients.add(client4);
        clients.add(client5);
    }
}