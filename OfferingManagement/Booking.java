package OfferingManagement;

import UserManagement.*;

public class Booking {

  private static int idCounter = 0;
  private int id;
  private User user;
  private Offering offering;

  public Booking(User user, Offering offering) {
    this.id = ++idCounter;
    this.user = user;
    this.offering = offering;
  }

  public int getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public Offering getOffering() {
    return offering;
  }
}
