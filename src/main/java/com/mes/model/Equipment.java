package com.mes.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "equipment")
public class Equipment {

    public enum Status { RUNNING, IDLE, MAINTENANCE, FAULT, OFFLINE }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.IDLE;

    private String location;

    private LocalDate lastMaintenance;

    private LocalDate nextMaintenance;

    public Equipment() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public LocalDate getLastMaintenance() { return lastMaintenance; }
    public void setLastMaintenance(LocalDate lastMaintenance) { this.lastMaintenance = lastMaintenance; }
    public LocalDate getNextMaintenance() { return nextMaintenance; }
    public void setNextMaintenance(LocalDate nextMaintenance) { this.nextMaintenance = nextMaintenance; }
}
