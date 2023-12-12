package dateduration;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DurationTest {

    @Test
    public void testDuration() {
        Duration duration = Duration.ofHours(2); //itt is statikus metodusok vannak
        assertEquals("PT2H", duration.toString());//String reprezentacioja PT2H
    }

    @Test
    public void testBetween() {
        LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 12, 16, 0);
        LocalDateTime localDateTime1 = LocalDateTime.of(2018, 1, 12, 18, 0);
        Duration duration = Duration.between(localDateTime, localDateTime1);
        assertEquals("PT2H", duration.toString());
    }

    @Test
    public void testParse() {//szerintem felesleges a public modositoszo kiirasa
        Duration duration = Duration.parse("PT2H");
        assertEquals("PT2H", duration.toString());
    }

    @Test
    public void testPlus() {
        LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 12, 16, 0);
        LocalDateTime localDateTime1 = localDateTime.plus(
            Duration.ofHours(2)); //immutable ezert megintcsak lesz egy visszateresi erteke
        assertEquals(LocalDateTime.of(2018, 1, 12, 18, 0), localDateTime1); //18 ora lesz a novelt eredmenye

    }

    @Test
    public void testDurationPlus() {
        Duration duration = Duration.ofHours(2).plusMinutes(3);
        assertEquals(Duration.ofHours(3).plusMinutes(3).plusHours(-1),
            duration);//lathato, ha minusz szamot adok hozza akkor az a szam abszolut ertekben levonasra kerul
    }

    @Test
    public void testNormalize() {//itt checkolni tudjuk, hogz a duration osztaly az erteket. Tehat mi tortenik akkor,
        // ha egy kelloen nagz szamot adunk meg.
        Duration duration = Duration.ofMinutes(250);
        assertEquals("PT4H10M", duration.toString());//automatikusan normalizalja, nem nekunk kell megtenni. PT4H10M
    }
}
