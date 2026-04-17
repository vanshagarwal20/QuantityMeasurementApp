package com.app.qmaservice.controller;

import com.app.qmaservice.dto.OperationResponse;

import com.app.qmaservice.dto.QuantityInputDTO;
import com.app.qmaservice.entity.QuantityOperation;
import com.app.qmaservice.service.IQuantityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quantities")
@Tag(name = "Quantities", description = "QMA Core Operations — Arithmetic, Compare, Convert, History")
public class QuantityController {

    // Spring injects this — previously was set to null which caused NullPointerException
    private final IQuantityService service;

    @Autowired
    public QuantityController(IQuantityService service) {
        this.service = service;
    }

    // ── Arithmetic Operations ─────────────────────────────────────────────

    @Operation(summary = "Add two quantities (auto-converts units)")
    @PostMapping("/add")
    public ResponseEntity<OperationResponse> add(
            @Valid @RequestBody QuantityInputDTO in) {
        return ResponseEntity.ok(service.add(in));
    }

    @Operation(summary = "Subtract second quantity from first")
    @PostMapping("/subtract")
    public ResponseEntity<OperationResponse> subtract(
            @Valid @RequestBody QuantityInputDTO in) {
        return ResponseEntity.ok(service.subtract(in));
    }

    @Operation(summary = "Multiply two quantities")
    @PostMapping("/multiply")
    public ResponseEntity<OperationResponse> multiply(
            @Valid @RequestBody QuantityInputDTO in) {
        return ResponseEntity.ok(service.multiply(in));
    }

    @Operation(summary = "Divide first quantity by second")
    @PostMapping("/divide")
    public ResponseEntity<OperationResponse> divide(
            @Valid @RequestBody QuantityInputDTO in) {
        return ResponseEntity.ok(service.divide(in));
    }

    // ── Compare ───────────────────────────────────────────────────────────

    @Operation(summary = "Compare two quantities — returns GREATER / LESS / EQUAL")
    @PostMapping("/compare")
    public ResponseEntity<OperationResponse> compare(
            @Valid @RequestBody QuantityInputDTO in) {
        return ResponseEntity.ok(service.compare(in));
    }

    // ── Convert ───────────────────────────────────────────────────────────

    @Operation(summary = "Convert a value from one unit to another")
    @PostMapping("/convert")
    public ResponseEntity<OperationResponse> convert(
            @Valid @RequestBody QuantityInputDTO in) {
        return ResponseEntity.ok(service.convert(in));
    }

    // ── History ───────────────────────────────────────────────────────────

    @Operation(summary = "Get past operations filtered by operation type (e.g. ADD, CONVERT)")
    @GetMapping("/history/operation/{operation}")
    public ResponseEntity<List<QuantityOperation>> historyByOperation(
            @PathVariable String operation) {
        return ResponseEntity.ok(service.getHistoryByOperation(operation));
    }

    @Operation(summary = "Get past operations filtered by measurement type (e.g. Length, Weight)")
    @GetMapping("/history/type/{type}")
    public ResponseEntity<List<QuantityOperation>> historyByType(
            @PathVariable String type) {
        return ResponseEntity.ok(service.getHistoryByType(type));
    }

    @Operation(summary = "Count successful operations by type")
    @GetMapping("/count/{operation}")
    public ResponseEntity<Long> count(
            @PathVariable String operation) {
        return ResponseEntity.ok(service.countByOperation(operation));
    }
}
