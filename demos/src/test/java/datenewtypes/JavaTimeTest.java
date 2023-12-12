package datenewtypes;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JavaTimeTest {

    @Test
    public void testLocalDateTime() {
        LocalDate localDate = LocalDate.now(); //aktualis datumot tartalmazza
        System.out.println(localDate);//a toString formatuma az ISO8100 as szabvanynak felel meg

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);

        LocalDate localDate1 = LocalDate.of(2018, 1, 12);
        assertEquals("2018-01-12",
            localDate1.toString());//ez a kiiras operacios rendszer fuggetlen, tehat mindig ezt fogja kiirni

        LocalDateTime localDateTime1 = LocalDateTime.of(2018, 1, 12, 16, 0);
        assertEquals("2018-01-12T16:00",
            localDateTime1.toString());//mivel localDateTime ezert az adott rendszerre vonatkoyik es ezert nem
        // tartalmaz idozona datot

        LocalTime localTime1 = LocalTime.of(16, 0);
        assertEquals("16:00", localTime1.toString());

        assertEquals("2018-01-13T17:00",
            localDateTime1.plusDays(1).plusHours(1)
                .toString());//ezek a tipusok immutable-k azaz mindig egy uj peldanyt fognak visszaadni

        assertTrue(localDateTime.isAfter(localDateTime1));//ossze is tudunk datumokat hasonlitani
    }

    @Test
    public void testConvert() {
        LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 12, 16, 0);
        LocalDate localDate = localDateTime.toLocalDate();
        LocalTime localTime = localDateTime.toLocalTime();
        assertEquals("2018-01-12", localDate.toString());
        assertEquals("16:00", localTime.toString());

        LocalDateTime localDateTime1 = LocalDateTime.of(localDate,
            localTime);//egy overloadolt metodusat kell hivni az of-nak
        assertEquals("2018-01-12T16:00", localDateTime1.toString());
    }

    @Test
    public void testJavaUtilDate() {//ez azt mutatja be hogz a java util date kozott hogyan tudunk konvertalni
        Date date = new Date();//java util date datumot es idot is tartalmaz
        LocalDateTime localDateTime =
            LocalDateTime.ofInstant(date.toInstant(),
                ZoneId.systemDefault());//mivel egyik sem tartalmaz idozona adatot ezert a default rendszer
        // zonaadatat hasznaljuk
        System.out.println(localDateTime);

        Date date1 = Date.from(
            localDateTime.atZone(ZoneId.systemDefault()).toInstant());//visszafele konvertalas localbol javaDate util
        System.out.println(
            date1); //date tostringje ugy van megirva hogy fugg az oprendszer beallitasaitol. Tehat nem a standard
        // ISO 8601-es szabvany szerint kerul kiirasra
    }

    @Test
    public void testChronoUnit() { //datumok kozotti kulonbs;g szamolasa
        LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 12, 16, 0);
        LocalDateTime localDateTime1 = LocalDateTime.of(2018, 1, 16, 16, 0);

        assertEquals(4L, ChronoUnit.DAYS.between(localDateTime, localDateTime1));

    }

    @Test
    public void testField() { //csak egy mezojenek lekerdezese
        LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 12, 16, 0);
        assertEquals(12, localDateTime.getDayOfMonth());
    }

    @Test
    public void testFormat() {
        LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 12, 16, 0);
        /*DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(
            FormatStyle.FULL); //2018. január 12., péntek  lathato, hogy a lokalis datet hasznalta (magyar
            beallitasoknak megfelelo)*/
        DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy. MMMM dd. hh:mm")
                .withLocale(Locale.ENGLISH);//konkret formatum String megadasa
        String s = localDateTime.format(formatter);

        assertEquals("2018. January 12. 04:00", s);
    }

    @Test
    public void testParse() { //ekkor Stringbol szeretnenk datumot letrehozni
        DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern(
                "yyyy-MM-dd HH:mm");//az orat (HH) nagy betuvel irjuk ugyanis ez jelenti azt, hogz a 24h
        // intervallumot hasznaljuk. du4 az 16:00. Valamint mivel nem szovegesen irjuk ki a datumot ezert mindegy
        // hogz milzen localt hasznalunk (ezert nem adtuk meg a .withLocale-t)
        String s = "2018-01-12 16:00";
        LocalDateTime localDateTime =
            LocalDateTime.parse(s, formatter);
        assertEquals(12, localDateTime.getDayOfMonth());
    }


}
