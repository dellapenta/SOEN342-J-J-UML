package UserManagement;

import java.util.ArrayList;
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
        Schedule schedule = booking.getOffering().getLesson().getSchedule();
        if (schedule.getStartDate().isBefore(offering.getLesson().getSchedule().getEndDate())
          && schedule.getEndDate().isAfter(offering.getLesson().getSchedule().getStartDate())
          && schedule.getDayOfWeek().equals(offering.getLesson().getSchedule().getDayOfWeek())
          ) {
            if (schedule.getTimeSlot().equals(offering.getLesson().getSchedule().getTimeSlot())) {
              System.out.println("You already have a booking for this time slot.");
              return null; // Prevent booking the same time slot
          }
        }
      }
      offering.getLesson().setCapacity(-1);
      return new Booking(this, offering);
    } else {
      System.out.println("Offering is not available");
      return null;
    }
  }

  public List<Booking> viewBookings(List<Booking> bookings) {
    List<Booking> myBookings = new ArrayList<>();
    System.out.println("Bookings for user " + getName() + ":");
    boolean found = false;
        for ( Booking booking : bookings) {
            if (booking.getUser().getUserId() == this.getUserId()) {
                System.out.println(booking.getId() + ". " + booking.getOffering().getLesson().getName() );
                myBookings.add(booking);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No bookings found for user " + getName() + ".");
            return null;
        } else {
            return myBookings;
        }
  }


  public void cancelBooking(List<Booking> bookings, Booking booking) {
    if (bookings.remove(booking)) {
       booking.getOffering().getLesson().setCapacity(1);
    } else {
       System.out.println("Booking not found.");
    }
  }
}
