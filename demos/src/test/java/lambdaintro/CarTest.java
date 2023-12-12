package lambdaintro;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CarTest {

    @Test
    void testCreate() {
        Car car = new Car("Fiat", "Punto Evo", 2_000_000, 4.064);

        Assertions.assertEquals("Fiat", car.getBrand());
        Assertions.assertEquals("Punto Evo", car.getType());
        Assertions.assertEquals(2_000_000, car.getPrice());
        Assertions.assertEquals(4.064, car.getLength());
    }
}
