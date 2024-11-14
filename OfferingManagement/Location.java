package OfferingManagement;

public class Location {
    private static int idCounter = 0; // Static variable to keep track of the last assigned ID
    private int locationId;
    private String name;
    private String city;

    // Constructor
    public Location(String name, String city) {
        this.locationId = ++idCounter; // Increment and assign the new ID
        this.name = name;
        this.city = city;
    }

    // Getters
    public int getLocationId() {
        return locationId;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

}
