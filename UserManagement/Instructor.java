package UserManagement;

import OfferingManagement.*;

public class Instructor extends User {
    private String specialization;
    private String availability;

    public Instructor(String name, String specialization, String availability) {
        super(name);
        this.specialization = specialization;
        this.availability = availability;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getAvailability() {
        return availability;
    }

    public void selectOffering(Offering offering) {
        offering.assignInstructor(this);
        System.out.println("Instructor " + getName() + " has taken the offering '" + offering.getName() + "'.");
        
    }

    public void updateAvailability(String newAvailability) {
        this.availability = newAvailability;
        System.out.println("Instructor " + getName() + "'s availability updated to: " + newAvailability);
    }
}
