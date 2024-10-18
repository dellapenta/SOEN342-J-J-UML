package OfferingManagement;

public class Location {
    private static int idCounter = 0; // Static variable to keep track of the last assigned ID
    private int locationId;
    private String name;
    private String address;
    private int capacity;

    // Constructor
    public Location(String name, String address, int capacity) {
        this.locationId = ++idCounter; // Increment and assign the new ID
        this.name = name;
        this.address = address;
        this.capacity = capacity;
    }

    // Getters
    public int getLocationId() {
        return locationId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getCapacity() {
        return capacity;
    }
}
