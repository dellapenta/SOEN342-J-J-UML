package UserManagement;

import java.util.ArrayList;
import java.util.List;
import OfferingManagement.*;
public class Client extends User {
  private String phoneNumber;
  private String email;
  private int age;
  private List<Offering> bookings;

  public Client(String name, String phoneNumber, String email, int age) {
    super(name);
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.age = age;
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
