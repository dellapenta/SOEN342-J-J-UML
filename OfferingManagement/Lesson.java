package OfferingManagement;

import java.util.List;

import UserManagement.*;
public class Lesson {
    private static int idCounter = 0;  // Static variable to track ID count

    private int lessonId;
    private String name;
    private String lessonType;
    private int capacity;
    private Location location;
    private Schedule schedule;
    private boolean availability;

    public Lesson(String name, String lessonType, int capacity, Location location, Schedule schedule) {
        this. lessonId = ++idCounter;  // Increment and assign unique ID
        this.name = name;
        this. lessonType =  lessonType;
        this.capacity = capacity;
        this.location = location;
        this.schedule = schedule;
        this.availability = true;
    }

    public int getLessonId() {
        return lessonId;
    }
    
    public String getName() {
        return name;
    }

    public String getLessonType() {
        return lessonType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int nb) {
        this.capacity = this.capacity + nb;
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
    

    public boolean isAvailable() {
        return this.availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public static Lesson createLesson(String name, String type, int capacity, Location location, Schedule schedule, List<Lesson> lessons) {
        for (Lesson lesson : lessons) {
            Schedule lessonSchedule = lesson.getSchedule();
            if (lessonSchedule.getStartDate().isBefore(schedule.getEndDate())
              && lessonSchedule.getEndDate().isAfter(schedule.getStartDate())
              && lessonSchedule.getDayOfWeek().equals(schedule.getDayOfWeek())
              && lessonSchedule.getTimeSlot().equals(schedule.getTimeSlot())
              && lesson.getLocation().getCity().equals(location.getCity())
              ) {
                  return null;
            }
        }

        return new Lesson(name, type, capacity, location, schedule);
    }

}
