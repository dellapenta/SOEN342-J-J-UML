package OfferingManagement;

import java.util.List;

import UserManagement.*;

public class Offering {
    private static int idCounter = 0;  // Static variable to track ID count

    private int offeringId;
    private String name;
    private String offeringType;
    private Location location;
    private Schedule schedule;
    private boolean availability;
    private Instructor instructor;

    public Offering(String name, String offeringType, Location location, Schedule schedule) {
        this.offeringId = ++idCounter;  // Increment and assign unique ID
        this.name = name;
        this.offeringType = offeringType;
        this.location = location;
        this.schedule = schedule;
        this.availability = true;
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

    public boolean getAvailability() {
        return availability;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void assignInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public boolean isAvailable() {
        return this.instructor != null;
    }

}
