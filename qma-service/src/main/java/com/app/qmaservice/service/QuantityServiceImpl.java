package com.app.qmaservice.service;

import com.app.qmaservice.dto.OperationResponse;
import com.app.qmaservice.dto.QuantityInputDTO;
import com.app.qmaservice.entity.QuantityOperation;
import com.app.qmaservice.exception.QmaException;
import com.app.qmaservice.repository.QuantityOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuantityServiceImpl implements IQuantityService {

    private final QuantityOperationRepository opRepo;

    @Autowired
    public QuantityServiceImpl(QuantityOperationRepository opRepo) {
        this.opRepo = opRepo;
    }

    // ═════════════════════════════════════════════════════════════════════
    //  UNIT CONVERSION HELPERS
    // ═════════════════════════════════════════════════════════════════════

    /**
     * Normalise a measurement type string so callers can send any case.
     * "length", "LENGTH", "Length" → "Length"
     */
    private String normaliseType(String type) {
        if (type == null) throw new QmaException("measurementType must not be null");
        // Capitalise first letter, lower-case the rest
        String t = type.trim();
        if (t.isEmpty()) throw new QmaException("measurementType must not be blank");
        return Character.toUpperCase(t.charAt(0)) + t.substring(1).toLowerCase();
    }

    /**
     * Convert a value in the given unit to the "base" unit for that type.
     * Base units: Meter (Length), Kilogram (Weight), Liter (Volume), Celsius (Temperature)
     *
     * FIX: both `unit` and `type` are normalised before the switch so the
     * cases always match regardless of what the caller sends.
     */
    private double toBase(double value, String unit, String type) {
        unit = unit.trim().toUpperCase();   // "Kilometer" → "KILOMETER"
        type = normaliseType(type);         // "length"    → "Length"

        switch (type) {
            case "Length":
                switch (unit) {
                    case "KILOMETER":  return value * 1000.0;
                    case "METER":      return value;
                    case "CENTIMETER": return value * 0.01;
                    case "MILLIMETER": return value * 0.001;
                    case "MILE":       return value * 1609.34;
                    case "YARD":       return value * 0.9144;
                    case "FOOT":       return value * 0.3048;
                    case "INCH":       return value * 0.0254;
                    default: throw new QmaException("Invalid Length unit: " + unit);
                }

            case "Weight":
                switch (unit) {
                    case "KILOGRAM":  return value;
                    case "GRAM":      return value * 0.001;
                    case "MILLIGRAM": return value * 0.000001;
                    case "TONNE":     return value * 1000.0;
                    case "POUND":     return value * 0.453592;
                    case "OUNCE":     return value * 0.0283495;
                    default: throw new QmaException("Invalid Weight unit: " + unit);
                }

            case "Volume":
                switch (unit) {
                    case "LITER":      return value;
                    case "MILLILITER": return value * 0.001;
                    case "GALLON":     return value * 3.78541;
                    default: throw new QmaException("Invalid Volume unit: " + unit);
                }

            case "Temperature":
                switch (unit) {
                    case "CELSIUS":    return value;
                    case "FAHRENHEIT": return (value - 32.0) * 5.0 / 9.0;
                    case "KELVIN":     return value - 273.15;
                    default: throw new QmaException("Invalid Temperature unit: " + unit);
                }

            default:
                throw new QmaException("Invalid measurement type: " + type
                        + ". Supported: Length, Weight, Volume, Temperature");
        }
    }

    /**
     * Convert a value from the base unit back to the requested unit.
     *
     * FIX: same normalisation as toBase.
     */
    private double fromBase(double base, String unit, String type) {
        unit = unit.trim().toUpperCase();
        type = normaliseType(type);

        switch (type) {
            case "Length":
                switch (unit) {
                    case "KILOMETER":  return base / 1000.0;
                    case "METER":      return base;
                    case "CENTIMETER": return base / 0.01;
                    case "MILLIMETER": return base / 0.001;
                    case "MILE":       return base / 1609.34;
                    case "YARD":       return base / 0.9144;
                    case "FOOT":       return base / 0.3048;
                    case "INCH":       return base / 0.0254;
                    default: throw new QmaException("Invalid Length unit: " + unit);
                }

            case "Weight":
                switch (unit) {
                    case "KILOGRAM":  return base;
                    case "GRAM":      return base / 0.001;
                    case "MILLIGRAM": return base / 0.000001;
                    case "TONNE":     return base / 1000.0;
                    case "POUND":     return base / 0.453592;
                    case "OUNCE":     return base / 0.0283495;
                    default: throw new QmaException("Invalid Weight unit: " + unit);
                }

            case "Volume":
                switch (unit) {
                    case "LITER":      return base;
                    case "MILLILITER": return base / 0.001;
                    case "GALLON":     return base / 3.78541;
                    default: throw new QmaException("Invalid Volume unit: " + unit);
                }

            case "Temperature":
                switch (unit) {
                    case "CELSIUS":    return base;
                    case "FAHRENHEIT": return (base * 9.0 / 5.0) + 32.0;
                    case "KELVIN":     return base + 273.15;
                    default: throw new QmaException("Invalid Temperature unit: " + unit);
                }

            default:
                throw new QmaException("Invalid measurement type: " + type);
        }
    }

    /**
     * Guard: both quantities must belong to the same measurement type.
     * FIX: compare normalised types so "Length" == "length" is treated as equal.
     */
    private void checkSameType(QuantityInputDTO in) {
        String type1 = normaliseType(in.getThisQuantity().getMeasurementType());
        String type2 = normaliseType(in.getThatQuantity().getMeasurementType());
        if (!type1.equals(type2)) {
            throw new QmaException(
                    "Cannot mix measurement types: " + type1 + " vs " + type2);
        }
    }

    /** Persist the operation record and return the saved entity. */
    private QuantityOperation saveOp(String op, QuantityInputDTO in, double result) {
        QuantityOperation entity = new QuantityOperation();
        entity.setOperation(op);
        entity.setMeasurementType(normaliseType(in.getThisQuantity().getMeasurementType()));
        entity.setValue1(in.getThisQuantity().getValue());
        entity.setUnit1(in.getThisQuantity().getUnit());
        entity.setValue2(in.getThatQuantity().getValue());
        entity.setUnit2(in.getThatQuantity().getUnit());
        entity.setResult(result);
        return opRepo.save(entity);
    }

    private void validateInput(QuantityInputDTO in) {
        if (in == null ||
            in.getThisQuantity() == null ||
            in.getThatQuantity() == null ||
            in.getThisQuantity().getUnit() == null ||
            in.getThatQuantity().getUnit() == null ||
            in.getThisQuantity().getMeasurementType() == null ||
            in.getThatQuantity().getMeasurementType() == null) {

            throw new QmaException("Invalid input: missing required fields");
        }
    }

    /** Map a persisted QuantityOperation to the API response DTO. */
    private OperationResponse toResponse(QuantityOperation op) {
        OperationResponse response = new OperationResponse();
        response.setOperation(op.getOperation());
        response.setMeasurementType(op.getMeasurementType());
        response.setValue1(op.getValue1());
        response.setUnit1(op.getUnit1());
        response.setValue2(op.getValue2());
        response.setUnit2(op.getUnit2());
        response.setResult(op.getResult());
        return response;
    }

    // ═════════════════════════════════════════════════════════════════════
    //  OPERATIONS
    // ═════════════════════════════════════════════════════════════════════

    @Override
    @CacheEvict(value = "history", allEntries = true)
    public OperationResponse add(QuantityInputDTO in) {
        validateInput(in);
        checkSameType(in);
        String type = normaliseType(in.getThisQuantity().getMeasurementType());

        double base1 = toBase(in.getThisQuantity().getValue(),
                in.getThisQuantity().getUnit(), type);
        double base2 = toBase(in.getThatQuantity().getValue(),
                in.getThatQuantity().getUnit(), type);

        double resultBase = base1 + base2;
        double result = fromBase(resultBase, in.getThisQuantity().getUnit(), type);

        return toResponse(saveOp("ADD", in, result));
    }

    @Override
    @CacheEvict(value = "history", allEntries = true)
    public OperationResponse subtract(QuantityInputDTO in) {
        validateInput(in);
        checkSameType(in);
        String type = normaliseType(in.getThisQuantity().getMeasurementType());

        double base1 = toBase(in.getThisQuantity().getValue(),
                in.getThisQuantity().getUnit(), type);
        double base2 = toBase(in.getThatQuantity().getValue(),
                in.getThatQuantity().getUnit(), type);

        double resultBase = base1 - base2;
        double result = fromBase(resultBase, in.getThisQuantity().getUnit(), type);

        return toResponse(saveOp("SUBTRACT", in, result));
    }

    @Override
    @CacheEvict(value = "history", allEntries = true)
    public OperationResponse multiply(QuantityInputDTO in) {
        validateInput(in);
        checkSameType(in);
        String type = normaliseType(in.getThisQuantity().getMeasurementType());

        double base1 = toBase(in.getThisQuantity().getValue(),
                in.getThisQuantity().getUnit(), type);
        double base2 = toBase(in.getThatQuantity().getValue(),
                in.getThatQuantity().getUnit(), type);

        double resultBase = base1 * base2;
        double result = fromBase(resultBase, in.getThisQuantity().getUnit(), type);

        return toResponse(saveOp("MULTIPLY", in, result));
    }

    @Override
    @CacheEvict(value = "history", allEntries = true)
    public OperationResponse divide(QuantityInputDTO in) {
        validateInput(in);
        checkSameType(in);
        String type = normaliseType(in.getThisQuantity().getMeasurementType());

        double base1 = toBase(in.getThisQuantity().getValue(),
                in.getThisQuantity().getUnit(), type);
        double base2 = toBase(in.getThatQuantity().getValue(),
                in.getThatQuantity().getUnit(), type);

        if (base2 == 0.0) {
            throw new QmaException("Cannot divide by zero");
        }

        double result = base1 / base2;
        return toResponse(saveOp("DIVIDE", in, result));
    }

    @Override
    @Cacheable(value = "compare",
            key = "#in.thisQuantity.value + '_' + #in.thisQuantity.unit + '_' + #in.thatQuantity.value + '_' + #in.thatQuantity.unit")
    public OperationResponse compare(QuantityInputDTO in) {
        validateInput(in);
        checkSameType(in);
        String type = normaliseType(in.getThisQuantity().getMeasurementType());

        double base1 = toBase(in.getThisQuantity().getValue(),
                in.getThisQuantity().getUnit(), type);
        double base2 = toBase(in.getThatQuantity().getValue(),
                in.getThatQuantity().getUnit(), type);

        String cmp;
        if      (base1 > base2) cmp = "GREATER";
        else if (base1 < base2) cmp = "LESS";
        else                    cmp = "EQUAL";

        QuantityOperation op = saveOp("COMPARE", in, base1 - base2);
        OperationResponse response = toResponse(op);
        response.setResultString(cmp);
        return response;
    }

    @Override
    @Cacheable(value = "convert",
            key = "#in.thisQuantity.value + '_' + #in.thisQuantity.unit + '_to_' + #in.thatQuantity.unit")
    public OperationResponse convert(QuantityInputDTO in) {
        validateInput(in);
        String type = normaliseType(in.getThisQuantity().getMeasurementType());

        double base  = toBase(in.getThisQuantity().getValue(),
                in.getThisQuantity().getUnit(), type);
        double result = fromBase(base, in.getThatQuantity().getUnit(), type);

        return toResponse(saveOp("CONVERT", in, result));
    }

    // ═════════════════════════════════════════════════════════════════════
    //  HISTORY & COUNT — Redis cached
    // ═════════════════════════════════════════════════════════════════════

    @Override
    @Cacheable(value = "history", key = "'op_' + #operation")
    public List<QuantityOperation> getHistoryByOperation(String operation) {
        return opRepo.findByOperation(operation.toUpperCase());
    }

    @Override
    @Cacheable(value = "history", key = "'type_' + #type")
    public List<QuantityOperation> getHistoryByType(String type) {
        return opRepo.findByMeasurementType(type);
    }

    @Override
    @Cacheable(value = "count", key = "#operation")
    public long countByOperation(String operation) {
        return opRepo.countByOperation(operation.toUpperCase());
    }
}
