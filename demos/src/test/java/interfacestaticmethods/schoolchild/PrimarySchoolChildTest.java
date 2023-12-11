package interfacestaticmethods.schoolchild;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PrimarySchoolChildTest {

    @Test
    void testOfLowerClassSchoolChild() {
        //PrimarySchoolChild primarySchoolChild = PrimarySchoolChild.of(8);

        Assertions.assertEquals(LowerClassSchoolChild.class, PrimarySchoolChild.of(10).getClass());
    }

    @Test
    void testOfWithUpperClassChild() {
        Assertions.assertEquals(UpperClassSchoolChild.class, PrimarySchoolChild.of(12).getClass());
    }

    @Test
    void testOfWithWrongAge() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> PrimarySchoolChild.of(25));

        Assertions.assertEquals("Your age is not compatible with elementary school!", exception.getMessage());
    }

}
