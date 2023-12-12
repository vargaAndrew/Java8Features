package dateperiod;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PeriodTest {

    @Test
    public void testPeriod() {
        Period period = Period.ofDays(5); //period class peldanyositasa. statikus metodusokkal lehet ezt megoldani
        assertEquals(5,
            period.getDays());//ha kiiratjuk csak a period-ot akkor meglatjuk, hogy hogy van implementalva a toString
        // metodus es ez visszaadja a String reprezentaciojat a periodnak azaz P5D

        Period period1 = Period.of(1, 2, 3);
        //period1 toStringje visszaadja: P1Y2M3D
        assertEquals(2, period1.getMonths());
    }

    @Test
    public void testParse() {//nem csak String-e lehet alakitani hanem Stringbol is letre lehet hozni periodot ez a
        // parse
        String s = "P2Y3M";
        Period period = Period.parse(s);
        assertEquals(3, period.getMonths());
    }

    @Test
    public void testBetween() {
        LocalDate localDate = LocalDate.of(2018, 1, 12);//szinten az of statikus metodussal hozunk letre egy peldanyt
        LocalDate localDate1 = LocalDate.of(2018, 1, 9);

        Period period = Period.between(localDate, localDate1);
        assertEquals(-3, period.getDays());
    }

    @Test
    public void testNormalize() {//normalizalas feladata, hogy atvaltja a honapokat evre
        Period period = Period.ofMonths(20).normalized();
        System.out.println(period);//nem a 20 honapot irja ki hanem 1 ev 8honapot -> P1Y8M
    }

    @Test
    public void testPlus() {//lehet egy perioddal es egy datummal muveletet is vegezni
        LocalDate localDate = LocalDate.of(2018, 1, 12);
        Period period = Period.ofDays(3);
        LocalDate localDate1 = localDate.plus(period);
        assertEquals(15, localDate1.getDayOfMonth());
    }

    @Test
    public void testPeriodPlus() {//magan a periodon is lehet muveletet vegezni. Ott is vannak plus metodusok amik
        // egy ujabb periodot adnak vissza hiszen a period onmagaba immutable. Tehat nem az allapotat modositjuk,
        // hanem mindig egy uj peldany kerul visszaadasra
        Period period = Period.ofDays(2);
        Period period1 = period.plusMonths(2);
        assertEquals("P2M2D", period1.toString());
    }
}
