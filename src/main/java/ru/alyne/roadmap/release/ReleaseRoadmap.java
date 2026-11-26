package ru.alyne.roadmap.release;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ReleaseRoadmap {

    private final List<Release> releases = List.of(
        new Release("0.1.0", "Закрытая альфа", LocalDate.of(2027, 2, 15), Release.Status.IN_PROGRESS),
        new Release("0.2.0", "Открытая бета", LocalDate.of(2027, 4, 20), Release.Status.PLANNED)
    );

    public List<Release> listReleases() {
        return releases;
    }
}
