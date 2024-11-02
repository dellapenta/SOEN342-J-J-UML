package UserManagement;

import java.util.List;
import OfferingManagement.*;

public class Administrator extends User {
    private String email;
    private String password;

    public Administrator(String name, String email, String password) {
        super(name);
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void createOffering(List<Offering> offerings, String name, String offeringType, Location location, Schedule schedule) {
        Offering offer = new Offering(name, offeringType, location, schedule);
        offerings.add(offer); // Add the new offering to the passed list
        System.out.println("Offering '" + offer.getName() + "' created by " + getName() + ".");
    }

    public void updateOffering(List<Offering> offerings, Offering offering, String newName) {
        if (offerings.contains(offering)) {
            System.out.println("Offering '" + offering.getName() + "' updated by " + getName() + ".");
            offering.setName(newName);
        } else {
            System.out.println("Offering not found.");
        }
    }

    public void removeOffering(List<Offering> offerings, Offering offering) {
        if (offerings.remove(offering)) {
            System.out.println("Offering '" + offering.getName() + "' removed by " + getName() + ".");
        } else {
            System.out.println("Offering not found.");
        }
    }

    public void deleteUserAccount(List<User> users, User user) {
        if (users.remove(user)) {
            System.out.println(user.getName() + "'s account has been deleted by " + getName() + ".");
        } else {
            System.out.println("User not found.");
        }
    }

    public void viewBooking(List<Booking> bookings) {
        for (Booking booking : bookings) {
            System.out.println(booking.getId() + ". " + booking.getOffering().getName() + " - " + (booking.getUser().getName()));
        }
    }
}
