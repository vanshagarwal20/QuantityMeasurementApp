package com.app.qmaservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "quantity_operations")
public class QuantityOperation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String operation;

    @Column(name = "measurement_type")
    private String measurementType;

    @Column(name = "value1", nullable = false)
    private Double value1;

    @Column(name = "unit1")
    private String unit1;

    @Column(name = "value2", nullable = false)
    private Double value2;

    @Column(name = "unit2")
    private String unit2;

    @Column(nullable = false)
    private Double result;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // ── Lifecycle callback ────────────────────────────────────────────────
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // ── No-arg constructor (required by JPA) ─────────────────────────────
    public QuantityOperation() {
    }

    // ── All-arg constructor ───────────────────────────────────────────────
    public QuantityOperation(Long id, String operation, String measurementType,
                             Double value1, String unit1, Double value2,
                             String unit2, Double result, LocalDateTime createdAt) {
        this.id = id;
        this.operation = operation;
        this.measurementType = measurementType;
        this.value1 = value1;
        this.unit1 = unit1;
        this.value2 = value2;
        this.unit2 = unit2;
        this.result = result;
        this.createdAt = createdAt;
    }

    // ── Getters ───────────────────────────────────────────────────────────
    public Long getId() {
        return id;
    }

    public String getOperation() {
        return operation;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public Double getValue1() {
        return value1;
    }

    public String getUnit1() {
        return unit1;
    }

    public Double getValue2() {
        return value2;
    }

    public String getUnit2() {
        return unit2;
    }

    public Double getResult() {
        return result;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // ── Setters ───────────────────────────────────────────────────────────
    public void setId(Long id) {
        this.id = id;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    public void setValue1(Double value1) {
        this.value1 = value1;
    }

    public void setUnit1(String unit1) {
        this.unit1 = unit1;
    }

    public void setValue2(Double value2) {
        this.value2 = value2;
    }

    public void setUnit2(String unit2) {
        this.unit2 = unit2;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // ── toString ──────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return "QuantityOperation{" +
                "id=" + id +
                ", operation='" + operation + '\'' +
                ", measurementType='" + measurementType + '\'' +
                ", value1=" + value1 +
                ", unit1='" + unit1 + '\'' +
                ", value2=" + value2 +
                ", unit2='" + unit2 + '\'' +
                ", result=" + result +
                ", createdAt=" + createdAt +
                '}';
    }
}
