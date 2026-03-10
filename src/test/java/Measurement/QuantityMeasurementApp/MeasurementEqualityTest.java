package Measurement.QuantityMeasurementApp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MeasurementEqualityTest {

    // UC1 - Feet Tests
    @Test
    void testFeetEquality_SameValue() {
        MeasurementEquality.Feet f1 = new MeasurementEquality.Feet(1.0);
        MeasurementEquality.Feet f2 = new MeasurementEquality.Feet(1.0);
        assertTrue(f1.equals(f2));
    }

    @Test
    void testFeetEquality_DifferentValue() {
        assertFalse(
            new MeasurementEquality.Feet(1.0)
                .equals(new MeasurementEquality.Feet(2.0))
        );
    }

    @Test
    void testFeetEquality_Null() {
        assertFalse(new MeasurementEquality.Feet(1.0).equals(null));
    }

    @Test
    void testFeetEquality_TypeMismatch() {
        assertFalse(new MeasurementEquality.Feet(1.0).equals("abc"));
    }

    @Test
    void testFeetEquality_SameReference() {
        MeasurementEquality.Feet f = new MeasurementEquality.Feet(1.0);
        assertTrue(f.equals(f));
    }

    // UC2 - Inch Tests
    @Test
    void testInchEquality_SameValue() {
        assertTrue(
            new MeasurementEquality.Inches(5.0)
                .equals(new MeasurementEquality.Inches(5.0))
        );
    }

    @Test
    void testInchEquality_DifferentValue() {
        assertFalse(
            new MeasurementEquality.Inches(5.0)
                .equals(new MeasurementEquality.Inches(6.0))
        );
    }

    @Test
    void testInchEquality_Null() {
        assertFalse(new MeasurementEquality.Inches(5.0).equals(null));
    }

    @Test
    void testInchEquality_TypeMismatch() {
        assertFalse(new MeasurementEquality.Inches(5.0)
                .equals(new MeasurementEquality.Feet(5.0)));
    }

    @Test
    void testInchEquality_SameReference() {
        MeasurementEquality.Inches i = new MeasurementEquality.Inches(5.0);
        assertTrue(i.equals(i));
    }
}
