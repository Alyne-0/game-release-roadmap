package ru.alyne.roadmap.release;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
class ReleaseRepository {

    private static final RowMapper<Release> RELEASE_MAPPER = (row, index) -> new Release(
        row.getString("version"),
        row.getString("title"),
        row.getObject("planned_date", LocalDate.class),
        Release.Status.valueOf(row.getString("status"))
    );

    private final JdbcClient jdbc;

    ReleaseRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    List<Release> findAll() {
        return jdbc.sql("""
            SELECT version, title, planned_date, status
            FROM releases
            ORDER BY planned_date, version
            """)
            .query(RELEASE_MAPPER)
            .list();
    }

    Optional<Release> findByVersion(String version) {
        return jdbc.sql("""
            SELECT version, title, planned_date, status
            FROM releases
            WHERE version = :version
            """)
            .param("version", version)
            .query(RELEASE_MAPPER)
            .optional();
    }

    void insert(Release release) {
        jdbc.sql("""
            INSERT INTO releases (version, title, planned_date, status)
            VALUES (:version, :title, :plannedDate, :status)
            """)
            .param("version", release.version())
            .param("title", release.title())
            .param("plannedDate", release.plannedDate())
            .param("status", release.status().name())
            .update();
    }
}
