package interfacedefaultmethods.cloth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TableClothTest {


    @Test
    void testSquare() {
        TableCloth tableCloth = new TableCloth(1.12);
        Assertions.assertEquals(4, tableCloth.getNumberOfSides());
        Assertions.assertEquals(1.5839191898578666, tableCloth.getLengthOfDiagonal());
        Assertions.assertEquals(4.48, tableCloth.getPerimeter());
        Assertions.assertEquals(1.2544, tableCloth.getArea(), 0.0001);
    }
}
