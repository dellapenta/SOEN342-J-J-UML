package UserManagement;

import java.util.List;
import OfferingManagement.*;
import java.util.Iterator;

public class Administrator extends User {
    private String email;
    private String password;

    public Administrator(String name, String email, String password) {
        super(name);
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void createLesson(List<Lesson> lessons, String name, String lessonType, int capacity, Location location, Schedule schedule) {
        Lesson lesson = Lesson.createLesson(name, lessonType, capacity, location, schedule, lessons);
        if (lesson == null) {
            System.out.println("Lesson is not unique.");
            return;
        }
        lessons.add(lesson); // Add the new lesson to the passed list
        System.out.println("Lesson '" + lesson.getName() + "' created by " + getName() + ".");
    }

    public void removeLesson(List<Lesson> lessons, Lesson lesson) {
        if (lessons.remove(lesson)) {
            System.out.println("Lesson '" + lesson.getName() + "' removed by " + getName() + ".");
        } else {
            System.out.println("Lesson not found.");
        }
    }

    public void removeUser(List<? extends User> users, int id) {
        boolean found = false;

        // Use an iterator to safely remove elements during iteration
        Iterator<? extends User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getUserId() == id) {
                iterator.remove();
                System.out.println(user.getName() + "'s account has been removed by " + getName() + ".");
                found = true;
                break;
            }
        }
    
        if (!found) {
            System.out.println("User with ID " + id + " not found.");
        }
    }

    public void viewBooking(List<Booking> bookings) {
        for (Booking booking : bookings) {
            System.out.println(booking.getId() + ". " + booking.getOffering().getLesson().getName() + " - " + (booking.getUser().getName()));
        }
    }
}
