package Measurement.QuantityMeasurementApp;

public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {

        if(unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        if(Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }

        this.value = value;
        this.unit = unit;
    }

    public Quantity<U> convertTo(U targetUnit) {

        double base = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(base);

        return new Quantity<>(Math.round(converted * 100.0) / 100.0, targetUnit);
    }

    public Quantity<U> add(Quantity<U> other) {

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        double sumBase = base1 + base2;

        double result = unit.convertFromBaseUnit(sumBase);

        return new Quantity<>(Math.round(result * 100.0) / 100.0, unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        double sumBase = base1 + base2;

        double result = targetUnit.convertFromBaseUnit(sumBase);

        return new Quantity<>(Math.round(result * 100.0) / 100.0, targetUnit);
    }

    @Override
    public boolean equals(Object obj) {

        if(this == obj) {
            return true;
        }

        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Quantity<?> other = (Quantity<?>) obj;

        if(!unit.getClass().equals(other.unit.getClass())) {
            return false;
        }

        double base1 = unit.convertToBaseUnit(value);
        double base2 = ((IMeasurable) other.unit).convertToBaseUnit(other.value);

        return Math.abs(base1 - base2) < 0.01;
    }

    @Override
    public int hashCode() {
        return Double.valueOf(value).hashCode() + unit.hashCode();
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }
    
    public static void main(String[] args) {

        System.out.println("Length Operations:");

        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCHES);

        System.out.println("1 foot == 12 inches : " + q1.equals(q2));

        System.out.println("1 foot to inches : " + q1.convertTo(LengthUnit.INCHES));

        Quantity<LengthUnit> lengthSum = q1.add(q2, LengthUnit.FEET);

        System.out.println("1 foot + 12 inches in feet : " + lengthSum);

        System.out.println("\nWeight Operations:");

        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);

        System.out.println("1 kg == 1000 g : " + w1.equals(w2));

        System.out.println("1 kg to grams : " + w1.convertTo(WeightUnit.GRAM));

        Quantity<WeightUnit> weightSum = w1.add(w2, WeightUnit.KILOGRAM);

        System.out.println("1 kg + 1000 g in kg : " + weightSum);
        
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> v2 =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> v3 =
                new Quantity<>(1.0, VolumeUnit.GALLON);

        // Equality
        System.out.println("1 L == 1000 mL : " + v1.equals(v2));

        // Conversion
        System.out.println("1 L to mL : " +
                v1.convertTo(VolumeUnit.MILLILITRE));

        System.out.println("1 Gallon to Litre : " +
                v3.convertTo(VolumeUnit.LITRE));

        // Addition
        System.out.println("1 L + 1000 mL in Litre : " +
                v1.add(v2, VolumeUnit.LITRE));

        System.out.println("1 L + 1 Gallon in mL : " +
                v1.add(v3, VolumeUnit.MILLILITRE));
    }
}
