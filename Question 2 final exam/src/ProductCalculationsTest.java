import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ProductCalculationsTest {

    private final ProductCalculations calc = new ProductCalculations(3500) {
        @Override
        public double GetVst() {
            return 0;
        }
    };

    @Test
    public void GetVat_WithValidData_ReturnsCorrectValue() {
        double expected = 525.0;   // 15% of 3500
        double actual = calc.GetVat();
        assertEquals(expected, actual, 0.001);
    }

    @Test
    public void GetTotal_WithValidData_ReturnsCorrectValue() {
        double expected = 4200.0;  // 3500 + 525 + 175
        double actual = calc.GetTotal();
        assertEquals(expected, actual, 0.001);
    }
}