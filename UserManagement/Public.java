package UserManagement;

import java.util.List;
import OfferingManagement.*;
public class Public {

    // Method to view only public offerings
    public void viewPublicOfferings(List<Offering> offerings) {
        System.out.println("Available Public Offerings:");
        for (Offering offering : offerings) {
            if (offering.isAvailable() == true) {
                Lesson lesson = offering.getLesson();
                System.out.println(lesson.getName() + " class is available" +
                                   " at " + lesson.getLocation().getName() +
                                   " in " + lesson.getLocation().getAddress() +
                                   " on " + lesson.getSchedule().getDayOfWeek() +
                                   " from " + lesson.getSchedule().getTimeSlot() +
                                   ", from " + lesson.getSchedule().getStartDate() +
                                   " to " + lesson.getSchedule().getEndDate());

            }
        }
    }
}