package datedaylight;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ZoneTest {

    @Test
    public void testZoneId() {
        ZoneId zoneId = ZoneId.of(
            "Europe/Budapest"); //az idozonakat a ZoneId osztaly reprezentalja. Itt eleg az idozona String
        // reprezentaciojat atadni
        //ZoneId.systemDefault()-al lehet lekerni a default/alapertelmezett idozonakat
        assertTrue(ZoneId.getAvailableZoneIds()
            .contains(zoneId.toString()));//A Set (halmaz) azaz az rendelkezesre allo idozonak tartalmazza-e a zoneId-t
    }

    @Test
    public void testZonedDateTime() {//letrehoz idot idozonaval
        LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 12, 16, 0);
        ZonedDateTime zonedDateTime =
            ZonedDateTime.of(localDateTime, ZoneId.of("Europe/Budapest"));
        assertEquals("2018-01-12T16:00+01:00[Europe/Budapest]", zonedDateTime.toString());
    }

    @Test
    public void testChange() {//szeretnenk valtani az idozonak kozott
        LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 12, 16, 0);
        ZonedDateTime zonedDateTime =
            ZonedDateTime.of(localDateTime, ZoneId.of(
                "Europe/Budapest"));//a letrehozott idot ebben az idozonaban ertelmezzunk es ezt szeretnenk atrakni
        // az utc idozonaba
        ZonedDateTime zonedDateTime1 =
            zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));//ez az idozona valtast vegrahjto metodus
        assertEquals("2018-01-12T15:00Z[UTC]",
            zonedDateTime1.toString());//itt epintunk arra, hogz a toString metodusa az mindig ugyanugz mukodik es
        // szabvanyos formatumban irja ki a zoneDateTime ertekét is
    }

    @Test
    public void testDaylightSaving() {//nyari/teli idoszamitas kozotti valtas
        ZonedDateTime zonedDateTime =
            ZonedDateTime.of(LocalDateTime.of(2017, 3, 26, 1, 59),//ekkor allitottak at ay orat 3orara
                ZoneId.of("Europe/Budapest"));
        ZonedDateTime zonedDateTime1 = zonedDateTime.plusMinutes(1);
        assertEquals("2017-03-26T03:00+02:00[Europe/Budapest]", zonedDateTime1.toString());
    }
}
