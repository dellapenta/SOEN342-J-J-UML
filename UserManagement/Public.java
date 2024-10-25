package UserManagement;

import java.util.List;
import OfferingManagement.*;
public class Public {

    // Method to view only public offerings
    public void viewPublicOfferings(List<Offering> offerings) {
        System.out.println("Available Public Offerings:");
        for (Offering offering : offerings) {
            if (offering.isAvailable() == true) {
                System.out.println("Offering: " + offering.getName() +
                                   ", Location: " + offering.getLocation().getName() +
                                   ", Instructor: " + offering.getInstructor().getName() +
                                   ", Schedule: " + offering.getSchedule().getStartDate() +
                                   " to " + offering.getSchedule().getEndDate());
            }
        }
    }
}