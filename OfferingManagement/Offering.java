package OfferingManagement;

import UserManagement.*;

public class Offering {
    private static int idCounter = 0;  // Static variable to track ID count

    private int offeringId;
    private String name;
    private String offeringType;
    private Location location;
    private Schedule schedule;
    private boolean isAvailable;
    private Instructor instructor;

    public Offering(String name, String offeringType, Location location, Schedule schedule) {
        this.offeringId = ++idCounter;  // Increment and assign unique ID
        this.name = name;
        this.offeringType = offeringType;
        this.location = location;
        this.schedule = schedule;
        this.isAvailable = true;
        this.instructor = null;
    }

    public int getOfferingId() {
        return offeringId;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfferingType() {
        return offeringType;
    }

    public Location getLocation() {
        return location;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void assignInstructor(Instructor instructor) {
        if (isAvailable) {
            this.instructor = instructor;
            this.isAvailable = false;
            System.out.println("Instructor " + instructor.getName() + " assigned to offering '" + name + "'.");
        } else {
            System.out.println("Offering is not available for assignment.");
        }
    }
}
