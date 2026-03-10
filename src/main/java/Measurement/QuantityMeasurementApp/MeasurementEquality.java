package Measurement.QuantityMeasurementApp;

public class MeasurementEquality {

    // UC1 — Feet
    public static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
        	// Same reference check
            if(this == obj) {
                return true;
            }
            // Null or type check
            if(obj == null || getClass() != obj.getClass()) {
                return false;
            }
            
            // Safe cast
            Feet other = (Feet) obj;
            
            // Floating point comparison
            return Double.compare(this.value, other.value) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }
    }

    // UC2 — Inches
    public static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {

            if(this == obj) {
                return true;
            }
            if(obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Inches other = (Inches) obj;
            return Double.compare(this.value, other.value) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }
    }

    // UC2 — Static Helper Methods
    public static boolean equalsInFeet(double a, double b) {
        return new Feet(a).equals(new Feet(b));
    }

    public static boolean equalsInInches(double a, double b) {
        return new Inches(a).equals(new Inches(b));
    }

    public static void main(String[] args) {
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);
        System.out.println("Feet Equal: " + f1.equals(f2));

        Inches i1 = new Inches(5.0);
        Inches i2 = new Inches(5.0);
        System.out.println("Inches Equal: " + i1.equals(i2));
    }
}