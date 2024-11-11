package OfferingManagement;

import java.time.LocalDate;

public class Schedule {
    private String timeSlot;
    private String dayOfWeek;
    private LocalDate startDate;
    private LocalDate endDate;

    public Schedule(LocalDate startDate, LocalDate endDate, String timeSlot, String dayOfWeek) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.timeSlot = timeSlot;
        this.dayOfWeek = dayOfWeek;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }
}
