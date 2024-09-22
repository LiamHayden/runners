package com.liamhayden.runners.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class RunRepository {

    private static final Logger log = LoggerFactory.getLogger(RunRepository.class);
    private final JdbcClient jdbcClient;

    public RunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Run> findAll() {
        return jdbcClient.sql("select * from run")
                .query(Run.class)
                .list();
    }

    public Optional<Run> findById(Integer id) {
        return jdbcClient.sql("select * from run where id = ?")
                .params("id", id)
                .query(Run.class)
                .optional();
    }

    public void create(Run run) {
        if (run == null) {
            throw new IllegalArgumentException("Run cannot be null");
        }
        var updated = jdbcClient.sql("INSERT INTO Run(id,title,started_on,completed_on, kilometers,location) VALUES(?,?,?,?,?,?)")
                .params(List.of(
                        run.id() != null ? run.id().toString() : null,
                        run.title() != null ? run.title().toString() : null,
                        run.startedOn() != null ? run.startedOn().toString() : null,
                        run.completedOn() != null ? run.completedOn().toString() : null,
                        run.kilometers() != null ? run.kilometers().toString() : null,
                        run.location() != null ? run.location().toString() : null))
                .update();

        Assert.state(updated == 1, "Failed to create run " + run.title());
    }

    public void update(Run run, Integer id) {
        var updated = jdbcClient.sql("UPDATE Run(id = ?,title = ?,started_on = ?,completed_on = ?, kilometers = ?,location = ?) WHERE id = ?")
                .params(List.of(run.id(),run.title(),run.startedOn(),run.completedOn(),run.kilometers(),run.location().toString(), id))
                .update();

        Assert.state(updated == 1, "Failed to update run " + run.title());
    }

    public void delete(Integer id) {
        var upload = jdbcClient.sql("DELETE FROM Run WHERE id = :id")
                .params("id", id)
                .update();

        Assert.state(upload == 1, "Failed to delete run " + id);
    }

    public int count() {
        return jdbcClient.sql("SELECT * FROM run")
                .query()
                .listOfRows()
                .size();
    }

    public void saveALL(List<Run> runs) {
        if (runs != null) {
            runs.stream().forEach(this::create);
        } else {
            System.out.println("runs is null");
        }

    }

    public List<Run> findByLocation(String location) {
        return jdbcClient.sql("SELECT * FROM run WHERE location = :location")
                .params("location", location)
                .query(Run.class)
                .list();
    }

}
