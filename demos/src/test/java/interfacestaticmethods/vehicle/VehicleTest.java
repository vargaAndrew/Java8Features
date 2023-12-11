package interfacestaticmethods.vehicle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VehicleTest {

    @Test
    void testOfWithBicycle() {
        Vehicle vehicle = Vehicle.of(2);

        Assertions.assertEquals(Bicycle.class, vehicle.getClass());
    }

    @Test
    void testOfWithCar() {
        Vehicle vehicle = Vehicle.of(4);

        Assertions.assertEquals(Car.class, vehicle.getClass());
    }

    @Test
    void testOfWithWrongParameter() {
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> Vehicle.of(3));
        Assertions.assertEquals("This is neither a bicycle nor a car.", ex.getMessage());
    }
}
