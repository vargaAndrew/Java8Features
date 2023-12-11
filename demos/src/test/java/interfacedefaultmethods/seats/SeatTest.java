package interfacedefaultmethods.seats;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SeatTest {

    @Test
    void testCar() {
        Car car = new Car("Fiat Punto Evo", 5);

        Assertions.assertEquals("Fiat Punto Evo", car.getBrand());
        Assertions.assertEquals(5, car.getNumberOfSeat());
    }

    @Test
    void testFamilyCar() {
        FamilyCar familyCar = new FamilyCar();
        Assertions.assertEquals(5, familyCar.getNumberOfSeat());
    }

    @Test
    void testSportsCar() {
        SportsCar sportsCar = new SportsCar();
        Assertions.assertEquals(2, sportsCar.getNumberOfSeat());
    }


}
