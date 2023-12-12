package lambdaintro;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FlatTest {

    @Test
    void testCreate() {
        Flat flat = new Flat("Budapest, Fő utca 3.", 135.6, 245_000_000);

        Assertions.assertEquals("Budapest, Fő utca 3.", flat.getAddress());
        Assertions.assertEquals(135.6, flat.getArea());
        Assertions.assertEquals(245_000_000, flat.getPrice());
    }
}
