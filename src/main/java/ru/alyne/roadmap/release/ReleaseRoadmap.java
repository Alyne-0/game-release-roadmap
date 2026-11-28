package ru.alyne.roadmap.release;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ReleaseRoadmap {

    private final ReleaseRepository repository;

    ReleaseRoadmap(ReleaseRepository repository) {
        this.repository = repository;
    }

    public List<Release> listReleases() {
        return repository.findAll();
    }

    public Optional<Release> findRelease(String version) {
        return repository.findByVersion(version);
    }

    public Release createRelease(String version, String title, LocalDate plannedDate) {
        Release release = new Release(version, title.strip(), plannedDate, Release.Status.PLANNED);
        repository.insert(release);
        return release;
    }

    public Optional<Release> updateRelease(String version, String title, LocalDate plannedDate, Release.Status status) {
        Release release = new Release(version, title.strip(), plannedDate, status);
        return repository.update(release) ? Optional.of(release) : Optional.empty();
    }
}
