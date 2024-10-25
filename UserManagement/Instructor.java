package UserManagement;

import java.util.Arrays;
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

    public void selectOffering(Offering offering) {
        // Split the available cities by comma and trim any extra spaces
        String[] availableCitiesArray = availablecity.split(",");

        // Iterate through the available cities
        for (String city : availableCitiesArray) {
            if (city.trim().equalsIgnoreCase(offering.getLocation().getAddress())) {
                offering.assignInstructor(this);
                System.out.println("Instructor " + getName() + " assigned to offering '" + offering.getName() + "'.");
                return;
            }
        }

        // If no matching city was found
        System.out.println("Instructor " + getName() + " cannot assign to offering '" + offering.getName() + "' because it is not available in " + availablecity + ".");
    }

}
