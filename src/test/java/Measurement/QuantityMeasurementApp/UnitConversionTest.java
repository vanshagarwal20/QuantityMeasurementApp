package Measurement.QuantityMeasurementApp;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UnitConversionTest {

    private static final double EPSILON = 1e-6;

    @Test
    void testConvertFeetToInches() {

        UnitConversion.QuantityLength q =
                new UnitConversion.QuantityLength(1.0, LengthUnit.FEET);

        UnitConversion.QuantityLength result =
                q.convertTo(LengthUnit.INCHES);

        assertEquals(
                new UnitConversion.QuantityLength(12.0, LengthUnit.INCHES),
                result);
    }

    @Test
    void testConvertInchesToFeet() {

        UnitConversion.QuantityLength q =
                new UnitConversion.QuantityLength(12.0, LengthUnit.INCHES);

        UnitConversion.QuantityLength result =
                q.convertTo(LengthUnit.FEET);

        assertEquals(
                new UnitConversion.QuantityLength(1.0, LengthUnit.FEET),
                result);
    }

    @Test
    void testConvertYardsToFeet() {

        UnitConversion.QuantityLength q =
                new UnitConversion.QuantityLength(1.0, LengthUnit.YARDS);

        UnitConversion.QuantityLength result =
                q.convertTo(LengthUnit.FEET);

        assertEquals(
                new UnitConversion.QuantityLength(3.0, LengthUnit.FEET),
                result);
    }

    @Test
    void testConvertCentimetersToInches() {

        UnitConversion.QuantityLength q =
                new UnitConversion.QuantityLength(2.54, LengthUnit.CENTIMETERS);

        UnitConversion.QuantityLength result =
                q.convertTo(LengthUnit.INCHES);

        assertEquals(
                new UnitConversion.QuantityLength(1.0, LengthUnit.INCHES),
                result);
    }

    @Test
    void testAdditionSameUnit() {

        UnitConversion.QuantityLength q1 =
                new UnitConversion.QuantityLength(1.0, LengthUnit.FEET);

        UnitConversion.QuantityLength q2 =
                new UnitConversion.QuantityLength(1.0, LengthUnit.FEET);

        UnitConversion.QuantityLength result = q1.add(q2);

        assertEquals(
                new UnitConversion.QuantityLength(2.0, LengthUnit.FEET),
                result);
    }

    @Test
    void testAdditionDifferentUnits() {

        UnitConversion.QuantityLength q1 =
                new UnitConversion.QuantityLength(1.0, LengthUnit.FEET);

        UnitConversion.QuantityLength q2 =
                new UnitConversion.QuantityLength(12.0, LengthUnit.INCHES);

        UnitConversion.QuantityLength result = q1.add(q2);

        assertEquals(
                new UnitConversion.QuantityLength(2.0, LengthUnit.FEET),
                result);
    }

    @Test
    void testAdditionWithTargetUnit() {

        UnitConversion.QuantityLength q1 =
                new UnitConversion.QuantityLength(1.0, LengthUnit.FEET);

        UnitConversion.QuantityLength q2 =
                new UnitConversion.QuantityLength(12.0, LengthUnit.INCHES);

        UnitConversion.QuantityLength result =
                UnitConversion.QuantityLength.add(q1, q2, LengthUnit.INCHES);

        assertEquals(
                new UnitConversion.QuantityLength(24.0, LengthUnit.INCHES),
                result);
    }

    @Test
    void testEqualsForSameValues() {

        UnitConversion.QuantityLength q1 =
                new UnitConversion.QuantityLength(1.0, LengthUnit.FEET);

        UnitConversion.QuantityLength q2 =
                new UnitConversion.QuantityLength(12.0, LengthUnit.INCHES);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testNotEquals() {

        UnitConversion.QuantityLength q1 =
                new UnitConversion.QuantityLength(1.0, LengthUnit.FEET);

        UnitConversion.QuantityLength q2 =
                new UnitConversion.QuantityLength(2.0, LengthUnit.FEET);

        assertFalse(q1.equals(q2));
    }

    @Test
    void testInvalidValue() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new UnitConversion.QuantityLength(Double.NaN, LengthUnit.FEET)
        );
    }

    @Test
    void testNullUnit() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new UnitConversion.QuantityLength(1.0, null)
        );
    }
}
