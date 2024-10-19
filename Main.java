import UserManagement.*;
import OfferingManagement.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create lists to hold offerings, users, and instructors
        List<Offering> offerings = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Instructor> instructors = new ArrayList<>();

        // Create a location and schedule
        Location location = new Location("EV Building Room 7", "Montreal", 20);

        // Create a schedule
        LocalDate startDate = LocalDate.of(2024, 9, 1); // Example start date
        LocalDate endDate = LocalDate.of(2024, 11, 30); // Example end date
        LocalTime startTime = LocalTime.of(12, 0); // 12:00 PM
        LocalTime endTime = LocalTime.of(15, 0); // 3:00 PM
        String dayOfWeek = "Sunday"; // Example day of the week

        Schedule schedule = new Schedule(startDate, endDate, startTime, endTime, dayOfWeek);

        // Create an offering and add it to the offerings list
        Offering offering = new Offering("Judo Class", "Group", location, schedule);
        offerings.add(offering);

        // Create an administrator and manage offerings
        Administrator admin = new Administrator("Admin", "admin@example.com", "securepassword");
        admin.createOffering(offerings, "Judo Class", "Group", location, schedule); // Create offering using list
        Offering offeringToUpdate = offerings.get(0); // Get the first offering
        admin.updateOffering(offerings, offeringToUpdate, "Advanced Judo Class"); // Update offering
        admin.removeOffering(offerings, offeringToUpdate); // Remove offering

        // Create an instructor and add to the instructors list
        Instructor instructor = new Instructor("Grace", "Judo", "Sundays 12:00 PM - 3:00 PM");
        instructors.add(instructor);

        // Instructor selects an offering and updates availability
        instructor.selectOffering(offering); // Instructor selects offering
        instructor.updateAvailability("Saturdays 1:00 PM - 4:00 PM"); // Update instructor availability
    }
}
