package OfferingManagement;

import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule {
    private LocalTime startTime;
    private LocalTime endTime;
    private String dayOfWeek;
    private LocalDate startDate;
    private LocalDate endDate;

    public Schedule(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, String dayOfWeek) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }
}
