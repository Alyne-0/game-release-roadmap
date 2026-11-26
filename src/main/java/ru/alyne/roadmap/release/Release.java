package ru.alyne.roadmap.release;

import java.time.LocalDate;

public record Release(String version, String title, LocalDate plannedDate, Status status) {

    public enum Status {
        PLANNED,
        IN_PROGRESS,
        RELEASED
    }
}
