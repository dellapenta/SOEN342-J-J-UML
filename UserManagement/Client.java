package UserManagement;

import java.util.List;
import OfferingManagement.*;
public class Client extends User {
  private String phoneNumber;
  private String email;
  private int age;
  private Client guardian;

  public Client(String name, String phoneNumber, String email, int age, Client guardian) {
    super(name);
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.age = age;
    if (age < 18) {
      if (guardian == null) {
        throw new IllegalArgumentException("Guardian is required for clients under 18.");
      }
      if (guardian.getAge() < 18) {
        throw new IllegalArgumentException("Guardian must be at least 18 years old.");
      }
      this.guardian = guardian;
    } else {
      this.guardian = null;
    }
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

  public Booking bookOffering(Offering offering, List<Booking> bookings) {
    if(offering.isAvailable()) {
      for (Booking booking : bookings) {
        Schedule schedule = booking.getOffering().getSchedule();
        if (schedule.getStartDate().isBefore(offering.getSchedule().getEndDate())
          && schedule.getEndDate().isAfter(offering.getSchedule().getStartDate())
          && schedule.getDayOfWeek().equals(offering.getSchedule().getDayOfWeek())
          ) {
            if (schedule.getTimeSlot().equals(offering.getSchedule().getTimeSlot())) {
              System.out.println("You already have a booking for this time slot.");
              return null; // Prevent booking the same time slot
          }
        }
      }
      // If no conflicting booking, proceed with the new booking
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
