package lambdastreams;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReduceTest {

    List<Employee> employees = Arrays.asList(
        new Employee("John Doe"),
        new Employee("Jane Doe"),
        new Employee("Joe Doe"),
        new Employee("John John Smith")
    );

    @Test
    public void testReduce() {
        int length = employees.stream().reduce(
            0,
            (i, e) -> i + e.getName().length(),
            (i1, i2) -> i1 + i2
        );
        assertEquals(38, length);
    }

    @Test
    public void testNameCounter() {
        NameCounter nameCounter = employees.stream().reduce(
            new NameCounter(),
//identity: mi az a kezdeti ertek amibol kiinduljunk. Nem adunk meg neki parametert ezert null-aval inicializalva
            (nc, e) -> nc.add(e),
            //accumulator, az aktualis elemet es a stream kovetkezo elemet hogyan kell osszedolgozni
            (nc1, nc2) -> nc1.add(nc2)
            //combiner (hogyan kell ket elemet osszedolgozni)-> ami ket nev countert combinal ossze
        );
        assertEquals(1, nameCounter.getThreePartName());
        assertEquals(3, nameCounter.getTwoPartName());
        //collect-nel ugzanez csak suppliernek hivjak az elso parametert.
    }

    @Test
    public void testNameCounterCollect() {
        NameCounterMod nameCounterMod =
            employees.stream().collect(
                NameCounterMod::new,
                //a supplier ami azt mondja meg, hogy mit syeretnenk megkapni, peldanyositson le egyet
                (counter, employee) -> counter.add(employee),
                //a kovetkezo azt mondja meg, hogy a nameCounter mod-ot hogyan syeretnenk osszedolgozni ay employeval
                (c1, c2) -> c1.add(c2)
                //combiner, ami azt mondja meg, hogy hogzan kell ket darab nameCounterMod-ot osszekombinalni
            );
        assertEquals(3, nameCounterMod.getTwoPartName());
        assertEquals(1, nameCounterMod.getThreePartName());
    }

    //a reduce az mindig uj objektumot hozott letre. Tehat imutable objektumokat hasznalhattunk addig a collect
    // metodus viszont egy olyan osztalyt is parameterul kaphatott, ami az adott osztaly allapotat azaz a belole
    // peldanyositott objektum allapotat modositja. Ezert mindig ujra es ujra arra hivta meg a megfelelo metodusokat.
    // Ugye ez bizonyos esetekben gyorsabb lehet hiszen nem kell allandoan ujabb es ujabb objektumokat letrehozni
    //
}
