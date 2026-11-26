package ru.alyne.roadmap.release;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
