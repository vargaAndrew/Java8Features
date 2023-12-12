package lambdastreams;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class StreamTest {

    List<Employee> employees = Arrays.asList(
        new Employee("John Doe"),
        new Employee("Jane Doe"),
        new Employee("Joe Doe"),
        new Employee("John Smith")
    );

    @Test
    public void testCount() {
        assertEquals(0L, Stream.empty().count());

        assertEquals(2L, Stream.of(new Employee("John Doe"),
            new Employee("Jack Doe")).count());

        Stream<Employee> s = Stream.of(new Employee("John Doe"),
            new Employee("Jack Doe"));
        assertEquals(2L, s.count());


        assertEquals(4L, employees.stream().count());
    }

    @Test
    public void testMin() {
        Employee employee =
            employees.stream().min(Comparator.comparing(
                    Employee::getName)) //lambda kifejezest lehet a comparingnak atadni (Employee::getName)
                .get();//testnel hasznalhatjuk a .get() et isPresent nelkul. Ha empty akkor elbukik a test, de ez nem
        // is baj
        assertEquals("Jane Doe", employee.getName());
    }

    @Test
    public void testFindFirst() {
        Employee employee =
            employees.stream().findFirst().get(); //get azert kell, mivel Optionalt kell vissyaadni
        //get helyett hasznalhatnak a .orElseThrow(itt egy prducert kell irni) azaz .orElseThrow(() -> new
        // IllegalArgumentException) ekkor a fejlesytoeszkoy nem fog warningot dobni.
        assertEquals("John Doe", employee.getName());
        //findFirst eseteben nem fogja a teljes streamot feldolgoyni, hanem ha megtalalja akkor le fog allni
    }

    @Test
    public void testAllMatch() {
        boolean result = employees.stream().allMatch(e -> e.getName().length() > 5);
        assertTrue(result);

        assertFalse(employees.stream().allMatch(e -> e.getName().startsWith("a")));
    }

    @Test
    public void testForEach() {
        employees.stream().forEach(e -> e.setName(e.getName().toUpperCase()));
        assertEquals("JOHN DOE", employees.get(0).getName());
    }


}
