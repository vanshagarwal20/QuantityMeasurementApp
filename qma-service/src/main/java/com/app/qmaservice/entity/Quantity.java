package com.app.qmaservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "quantity")
public class Quantity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double value;

    @Column(length = 255)
    private String unit;

    // ── No-arg constructor (required by JPA) ─────────────────────────────
    public Quantity() {
    }

    // ── All-arg constructor ───────────────────────────────────────────────
    public Quantity(Long id, Double value, String unit) {
        this.id = id;
        this.value = value;
        this.unit = unit;
    }

    // ── Convenience constructor (without id — for new records) ───────────
    public Quantity(Double value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    // ── Getters ───────────────────────────────────────────────────────────
    public Long getId() {
        return id;
    }

    public Double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    // ── Setters ───────────────────────────────────────────────────────────
    public void setId(Long id) {
        this.id = id;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    // ── toString ──────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return "Quantity{id=" + id + ", value=" + value + ", unit='" + unit + "'}";
    }
}
