package com.app.qmaservice.dto;

public class OperationResponse {

    private String operation;
    private String measurementType;
    private Double value1;
    private String unit1;
    private Double value2;
    private String unit2;
    private Double result;
    private String resultString;   // used for COMPARE: "GREATER" / "LESS" / "EQUAL"
    private boolean error;
    private String errorMessage;

    // ── No-arg constructor ────────────────────────────────────────────────
    public OperationResponse() {
    }

    // ── All-arg constructor ───────────────────────────────────────────────
    public OperationResponse(String operation, String measurementType,
                             Double value1, String unit1,
                             Double value2, String unit2,
                             Double result, String resultString,
                             boolean error, String errorMessage) {
        this.operation = operation;
        this.measurementType = measurementType;
        this.value1 = value1;
        this.unit1 = unit1;
        this.value2 = value2;
        this.unit2 = unit2;
        this.result = result;
        this.resultString = resultString;
        this.error = error;
        this.errorMessage = errorMessage;
    }

    // ── Getters ───────────────────────────────────────────────────────────
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

    public String getResultString() {
        return resultString;
    }

    public boolean isError() {
        return error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    // ── Setters (all properly typed — no Object parameter) ────────────────
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

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // ── toString ──────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return "OperationResponse{" +
                "operation='" + operation + '\'' +
                ", measurementType='" + measurementType + '\'' +
                ", value1=" + value1 +
                ", unit1='" + unit1 + '\'' +
                ", value2=" + value2 +
                ", unit2='" + unit2 + '\'' +
                ", result=" + result +
                ", resultString='" + resultString + '\'' +
                ", error=" + error +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
