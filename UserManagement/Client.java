package UserManagement;

import java.util.ArrayList;
import java.util.List;
import OfferingManagement.*;
public class Client extends User {
  private String phoneNumber;
  private String email;
  private int age;
  private Client guardian;
  private List<Offering> bookings;

  // Constructor for clients over 18
  public Client(String name, String phoneNumber, String email, int age) {
    super(name);
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.age = age;
    this.bookings = new ArrayList<>();
  }

  // Constructor for clients under 18 with a guardian
  public Client(String name, String phoneNumber, String email, int age, Client guardian) {
    super(name);
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.age = age;
    this.guardian = guardian;
    this.bookings = new ArrayList<>();
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

  public List<Offering> getBookings() {
    return bookings;
  }

  public String getUserInfo() {
    return "User ID: " + getUserId() + ", Name: " + getName() + ", Phone Number: " + phoneNumber + ", Email: " + email + ", Age: " + age;
  }

  public void bookOffering(Offering booking) {
    bookings.add(booking);
    booking.setAvailability(false);
  }

  public void viewBookings() {
    System.out.println("Bookings for user " + getName() + ":");
        for ( Offering booking : bookings) {
            System.out.println(booking.getName() + " (" + booking.getOfferingType() + ")"
                    + " (" + booking.getSchedule().getStartDate() + " to " + booking.getSchedule().getEndDate() + ")");
        }
  }
  public void cancelBooking(Offering booking) {
    bookings.remove(booking);
    booking.setAvailability(true);
  }
}
