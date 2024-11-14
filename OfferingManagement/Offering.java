package OfferingManagement;

import java.util.List;

import UserManagement.*;
public class Offering {
    private static int idCounter = 0;  // Static variable to track ID count

    private int offeringId;
    private Lesson lesson;
    private Instructor instructor;

    public Offering(Lesson Lesson, Instructor instructor) {
        this.offeringId = ++idCounter;  // Increment and assign unique ID
        this.lesson = Lesson;
        this.instructor = instructor;
    }

    public int getOfferingId() {
        return offeringId;
    }
    
    public Lesson getLesson() {
        return lesson;
    }
    
    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }


    public boolean isAvailable() {
        return this.lesson.getCapacity() != 0;
    }
}
