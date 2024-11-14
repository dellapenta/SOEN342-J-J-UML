package UserManagement;

import java.util.ArrayList;
import java.util.List;

import OfferingManagement.*;

public class Instructor extends User {
    private String phoneNumber;
    private String specialization;
    private String availablecity; // Variable can now contain multiple cities separated by a comma

    public Instructor(String name, String phoneNumber, String specialization, String availablecity) {
        super(name);
        this.phoneNumber = phoneNumber;
        this.specialization = specialization;
        this.availablecity = availablecity; // Initialize the available cities
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getAvailableCity() {
        return availablecity; // Return the available cities
    }

    public Offering createOffering(Lesson lesson) {
        // Split the available cities by comma and trim any extra spaces
        String[] availableCitiesArray = availablecity.split(",");

        // Iterate through the available cities
        for (String city : availableCitiesArray) {
            if (city.trim().equalsIgnoreCase(lesson.getLocation().getCity())) {
                if (lesson.getAvailability() == true) {
                    System.out.println("Instructor " + getName() + " assigned to offering '" + lesson.getName() + "'.");
                    return new Offering(lesson, this);
                } else {
                    System.out.println("This offering is already assigned to another instructor.");
                    return null;
                }

            }
        }

        // If no matching city was found
        System.out.println("Instructor " + getName() + " cannot assign to offering '" + lesson.getName() + "' because it is not available in " + availablecity + ".");
        return null;
    }

    public void unassignOffering(List<Offering> offerings, Offering offering) {
        if (offering.getInstructor() == this) {
            offering.setInstructor(null);
            System.out.println("Instructor " + getName() + " unassigned from offering '" + offering.getLesson().getName() + "'.");
            offerings.remove(offering);
        } else {
            System.out.println("Instructor " + getName() + " is not assigned to offering '" + offering.getLesson().getName() + "'.");
        }
    }

    public List<Offering> viewMyOfferings(List<Offering> offerings) {
    List<Offering> assignedOfferings = new ArrayList<>();
    System.out.println("Offerings for user " + getName() + ":");
    boolean found = false;
        for ( Offering offering : offerings) {
            if ( offering.getInstructor() != null && offering.getInstructor().getUserId() == this.getUserId()) {
                System.out.println(offering.getLesson().getLessonId() + ". " + offering.getLesson().getName() );
                assignedOfferings.add(offering);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No offerings found for user " + getName() + ".");
            return null;
        } else {
            return assignedOfferings;
        }
  }
}
