package lambdaintermediate;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntermediateTest {

    List<Employee> employees = Arrays.asList(
        new Employee("John Doe"),
        new Employee("Jane Doe"),
        new Employee("Joe Doe"),
        new Employee("John Smith")
    );

    //tanulo tesztek -> ezeken lehet elsajatitani a kozbulso muveletek mukodeset
    @Test
    public void testFilter() {
        List<Employee> filtered =
            employees.stream().filter(e -> e.getName().startsWith("John"))
                .collect(Collectors.toList());

        assertEquals(2, filtered.size());
        assertEquals("John Doe", filtered.get(0).getName());
    }

    @Test
    public void testDistinct() { //duplikatumokat tudunk kiszurni
        List<String> filtered = Stream.of("John", "John", "Jane", "John")
            .distinct().collect(Collectors.toList());
        assertEquals(Arrays.asList("John", "Jane"), filtered);
    }

    @Test
    public void testLimitSkip() {//Streamen atmeno elemek szamat lehet limitalni valamint atugrani
        List<Employee> filtered = employees.stream()
            .skip(1).limit(2)
            .collect(
                Collectors.toList());//ugorjon at 1-et es utana belimitalom, hogy csak 2-t adjon vissza es megintcsak
        // osszegyujtom egy listaba az eredmenyeket
        assertEquals(2, filtered.size());
        assertEquals("Jane Doe", filtered.get(0).getName());
    }

    @Test
    public void testInfinite() {
        List<Integer> l = Stream.iterate(1, n -> n + 2).skip(2)
            .limit(
                3). //1-es a kezdoertek, utana, hogy a kovetkezot hogzan szamolja ki tehat az elozo +2, majd ugorjon
            // at 2-t es adjon visza 3-at
                collect(Collectors.toList());
        assertEquals(Arrays.asList(5, 7, 9), l);
    }

    @Test
    public void testMap() {//a streamon atmeno egyik erteket atranszformaljuk egy masik ertekbe.
        List<String> names = employees.stream().limit(2) //le limitalom, mert nem szeretnem az osszeset beirni
            .map(e -> e.getName()).collect(Collectors.toList());
        assertEquals(Arrays.asList("John Doe", "Jane Doe"), names);
        //az employee streambol egy String streamot kaptam
    }

    @Test
    public void testFlatMap() {//ahol gyakorlatilag az elemekbol Streamot tudok letrehozni es ezeket a Streamokon
        // athalado elemeket egyesiti egyetlen egy streambe
        List<String> s1 = Arrays.asList("John Doe", "Jane Doe");
        List<String> s2 = Arrays.asList("Jack Doe", "Joe Doe");

        List<String> names = Stream.of(s1, s2).flatMap(l -> l.stream()).collect(Collectors.toList());
        //falatmap azt mondja meg, hogy hogyan hozzyunk letre streamot a listabol

        assertEquals(Arrays.asList("John Doe", "Jane Doe", "Jack Doe", "Joe Doe"), names);
    }

    @Test
    public void testSorted() { //sorrendbe lehet rendezni az elemeket
        List<Employee> sorted = employees.stream()
            .sorted(Comparator.comparing(
                Employee::getName)) //sorteddal ayt adjuk meg, hogy a streamunket hogzan szeretnenk berendezni
            // ha nem adunk meg comparatort akkor natural ordering. Tehat a Comparable interface compareTo metodusat
            // hivja. Ha megadunk komparatort akkor pedig hasznalhatjuk azt is.
            .collect(Collectors.toList());
        assertEquals("Jane Doe", sorted.get(0).getName());
    }

    @Test
    public void testPeek() { //ez gyakorlatilag minden egyes elemen lefut es ezert nagyon jol hasznalhato debug celokra
        List<String> names = employees.stream()//nezzuk meg, hogy hogzan lehet a kulonbozo muveleteket sorrendben
            // lefuttatni illetve egymasra fuzni. Elso lepes az employeesnak vesszuk a streamjet
            .peek(System.out::println) //ekkor lathatom hogy a bemeneti stream 4 db employeet tartalmaz
            .map(
                e -> e.getName()) //utana szeretnenk ezeket atkonvertalni ezeket nevekke (szeretnenk az employeehey
            //.peek(System.out::println) //nagyon jol lathato, hogy a map jol mukodik mivel az employee peldanyokat
            // atkonvertalta Stringekke, de meg ossze-vissza vannak
            // az e.getName erteket rendelni)
            .sorted()//utana szeretnem, hogy ezek le is legyenek rendezve. Natural ordering alapjan, hiszen a
            // Stringnel a compareTo az van deklaralva
            .peek(
                System.out::println)//jol lathatoan csak a 2 erteket irja ki, pedig a limit utana jon. Ezt az
            // ugynevezett lazy kiertekelesnek nevezik. Ami azt jelenti, hogy kizarolag annyi elemet fog feldolgozni
            // amennyire szukseg van. Tehat nem azt csinalja, hogy vegigmegy a stream osszes elemen es az osszeset
            // kiirja, hanem kizarolag annyit allit elo amennyire szukseg van. Ezt ugy kepzeljuk el, hogy a limit
            // huzza be az elemeket es nem pedig a sorted tolja az elemeket. Az elejen meg szukseg volt arra, hogy az
            // osszes elemet eloallitsuk ugyanis a sorted (a rendezes) kizarolag akkor tud jol mukodni, ha az osszes
            // elemet eloallitottuk neki.
            .limit(2) //majd szeretnenk, ha ebbol csak az elso kettot adna vissza
            .collect(Collectors.toList());// es ezt szeretnem collectalni

        assertEquals(Arrays.asList("Jane Doe", "Joe Doe"), names);
        //peek az osszes elemet megfogja es azon valamilyen muveletet hajt vegre
    }
}
