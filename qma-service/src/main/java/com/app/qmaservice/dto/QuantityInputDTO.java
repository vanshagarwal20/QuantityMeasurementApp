package com.app.qmaservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class QuantityInputDTO {

    @Valid
    @NotNull(message = "thisQuantity must not be null")
    private QuantityDTO thisQuantity;

    @Valid
    @NotNull(message = "thatQuantity must not be null")
    private QuantityDTO thatQuantity;

    // ── No-arg constructor ────────────────────────────────────────────────
    public QuantityInputDTO() {
    }

    // ── All-arg constructor ───────────────────────────────────────────────
    public QuantityInputDTO(QuantityDTO thisQuantity, QuantityDTO thatQuantity) {
        this.thisQuantity = thisQuantity;
        this.thatQuantity = thatQuantity;
    }

    // ── Getters (return correct type QuantityDTO — not Object) ────────────
    public QuantityDTO getThisQuantity() {
        return thisQuantity;
    }

    public QuantityDTO getThatQuantity() {
        return thatQuantity;
    }

    // ── Setters ───────────────────────────────────────────────────────────
    public void setThisQuantity(QuantityDTO thisQuantity) {
        this.thisQuantity = thisQuantity;
    }

    public void setThatQuantity(QuantityDTO thatQuantity) {
        this.thatQuantity = thatQuantity;
    }

    // ── toString ──────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return "QuantityInputDTO{thisQuantity=" + thisQuantity
                + ", thatQuantity=" + thatQuantity + "}";
    }
}
