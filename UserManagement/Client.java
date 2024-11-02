package UserManagement;

import java.util.List;
import OfferingManagement.*;
public class Client extends User {
  private String phoneNumber;
  private String email;
  private int age;
  private Client guardian;

  // Constructor for clients over 18
  public Client(String name, String phoneNumber, String email, int age) {
    super(name);
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.age = age;;
  }

  // Constructor for clients under 18 with a guardian
  public Client(String name, String phoneNumber, String email, int age, Client guardian) {
    super(name);
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.age = age;
    this.guardian = guardian;;
}
  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public int getAge() {
    return age;
  }

  public Client getGuardian() {
    return guardian;
  }

  public String getUserInfo() {
    return "User ID: " + getUserId() + ", Name: " + getName() + ", Phone Number: " + phoneNumber + ", Email: " + email + ", Age: " + age;
  }

  public Booking bookOffering(Offering offering) {
    if(offering.isAvailable()) {
      offering.setAvailability(false);
      return new Booking(this, offering);
    } else {
      System.out.println("Offering is not available");
      return null;
    }
  }

  public void viewBookings(List<Booking> bookings) {
    System.out.println("Bookings for user " + getName() + ":");
    boolean found = false;
        for ( Booking booking : bookings) {
            if (booking.getUser().getUserId() == this.getUserId()) {
                System.out.println(booking.getId() + ". " + booking.getOffering().getName() );
                found = true;
            }
        }

        if (!found) {
            System.out.println("No bookings found for user " + getName() + ".");
            return;
        }
  }


  public void cancelBooking(List<Booking> bookings, Booking booking) {
    if (bookings.remove(booking)) {
       System.out.println("Booking " + booking.getId() + " for " + booking.getOffering().getName() + " has been cancelled by " + getName() + ".");
    } else {
       System.out.println("Booking not found.");
    }
  }
}
