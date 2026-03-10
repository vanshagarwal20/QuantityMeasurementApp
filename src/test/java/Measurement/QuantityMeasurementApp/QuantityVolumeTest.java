package Measurement.QuantityMeasurementApp;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class QuantityVolumeTest {

    private static final double EPSILON = 1e-6;

    @Test
    void testEquality_LitreToLitre() {

        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1.0, VolumeUnit.LITRE);

        assertTrue(v1.equals(v2));
    }

    @Test
    void testEquality_LitreToMillilitre() {

        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        assertTrue(v1.equals(v2));
    }

    @Test
    void testConversion_LitreToMillilitre() {

        Quantity<VolumeUnit> v = new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result = v.convertTo(VolumeUnit.MILLILITRE);

        assertEquals(
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE),
                result
        );
    }

    @Test
    void testConversion_GallonToLitre() {

        Quantity<VolumeUnit> v = new Quantity<>(1.0, VolumeUnit.GALLON);

        Quantity<VolumeUnit> result = v.convertTo(VolumeUnit.LITRE);

        assertEquals(
                new Quantity<>(3.78541, VolumeUnit.LITRE),
                result
        );
    }

    @Test
    void testAddition_LitrePlusMillilitre() {

        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> result = v1.add(v2, VolumeUnit.LITRE);

        assertEquals(
                new Quantity<>(2.0, VolumeUnit.LITRE),
                result
        );
    }

    @Test
    void testInvalidValue_NaN() {

        assertThrows(IllegalArgumentException.class, () -> {
            new Quantity<>(Double.NaN, VolumeUnit.LITRE);
        });
    }

    @Test
    void testNullUnit() {

        assertThrows(IllegalArgumentException.class, () -> {
            new Quantity<>(5.0, null);
        });
    }
}