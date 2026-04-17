package com.app.qmaservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class QuantityDTO {

    @NotNull(message = "Value is required")
    private Double value;

    @NotBlank(message = "Unit is required")
    private String unit;

    @NotBlank(message = "measurementType is required")
    private String measurementType;

    // ── No-arg constructor ────────────────────────────────────────────────
    public QuantityDTO() {
    }

    // ── All-arg constructor ───────────────────────────────────────────────
    public QuantityDTO(Double value, String unit, String measurementType) {
        this.value = value;
        this.unit = unit;
        this.measurementType = measurementType;
    }

    // ── Getters ───────────────────────────────────────────────────────────
    public Double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    // ── Setters ───────────────────────────────────────────────────────────
    public void setValue(Double value) {
        this.value = value;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    // ── toString ──────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return "QuantityDTO{value=" + value + ", unit='" + unit
                + "', measurementType='" + measurementType + "'}";
    }
}
