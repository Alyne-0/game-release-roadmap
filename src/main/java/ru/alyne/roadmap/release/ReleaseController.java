package ru.alyne.roadmap.release;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/releases")
public class ReleaseController {

    private final ReleaseRoadmap roadmap;

    public ReleaseController(ReleaseRoadmap roadmap) {
        this.roadmap = roadmap;
    }

    @GetMapping
    public List<Release> listReleases() {
        return roadmap.listReleases();
    }

    @GetMapping("/{version}")
    public Release getRelease(@PathVariable String version) {
        return roadmap.findRelease(version)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Релиз не найден"));
    }

    @PostMapping
    public ResponseEntity<Release> createRelease(@Valid @RequestBody CreateReleaseRequest request) {
        Release release;
        try {
            release = roadmap.createRelease(request.version(), request.title(), request.plannedDate());
        } catch (DuplicateKeyException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Релиз с такой версией уже существует", exception);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
            .pathSegment(release.version())
            .build()
            .toUri();
        return ResponseEntity.created(location).body(release);
    }

    public record CreateReleaseRequest(
        @NotNull @Size(max = 32) @Pattern(regexp = "[0-9]+\\.[0-9]+\\.[0-9]+") String version,
        @NotBlank @Size(max = 120) String title,
        @NotNull LocalDate plannedDate
    ) {}
}
