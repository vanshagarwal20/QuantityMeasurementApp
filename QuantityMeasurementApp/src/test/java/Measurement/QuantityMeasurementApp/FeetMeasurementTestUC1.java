package Measurement.QuantityMeasurementApp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FeetMeasurementTestUC1 {

    // UC1 - Feet Tests
    @Test
    void testFeetEquality_SameValue() {
        FeetMeasurementUC1.Feet f1 = new FeetMeasurementUC1.Feet(1.0);
        FeetMeasurementUC1.Feet f2 = new FeetMeasurementUC1.Feet(1.0);
        assertTrue(f1.equals(f2));
    }

    @Test
    void testFeetEquality_DifferentValue() {
        assertFalse(
            new FeetMeasurementUC1.Feet(1.0)
                .equals(new FeetMeasurementUC1.Feet(2.0))
        );
    }

    @Test
    void testFeetEquality_Null() {
        assertFalse(new FeetMeasurementUC1.Feet(1.0).equals(null));
    }

    @Test
    void testFeetEquality_TypeMismatch() {
        assertFalse(new FeetMeasurementUC1.Feet(1.0).equals("abc"));
    }

    @Test
    void testFeetEquality_SameReference() {
    	FeetMeasurementUC1.Feet f = new FeetMeasurementUC1.Feet(1.0);
        assertTrue(f.equals(f));
    }

    // UC2 - Inch Tests
    @Test
    void testInchEquality_SameValue() {
        assertTrue(
            new FeetMeasurementUC1.Inches(5.0)
                .equals(new FeetMeasurementUC1.Inches(5.0))
        );
    }

    @Test
    void testInchEquality_DifferentValue() {
        assertFalse(
            new FeetMeasurementUC1.Inches(5.0)
                .equals(new FeetMeasurementUC1.Inches(6.0))
        );
    }

    @Test
    void testInchEquality_Null() {
        assertFalse(new FeetMeasurementUC1.Inches(5.0).equals(null));
    }

    @Test
    void testInchEquality_TypeMismatch() {
        assertFalse(new FeetMeasurementUC1.Inches(5.0)
                .equals(new FeetMeasurementUC1.Feet(5.0)));
    }

    @Test
    void testInchEquality_SameReference() {
    	FeetMeasurementUC1.Inches i = new FeetMeasurementUC1.Inches(5.0);
        assertTrue(i.equals(i));
    }
}