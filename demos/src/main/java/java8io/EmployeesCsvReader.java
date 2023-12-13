package java8io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmployeesCsvReader {

    //A java8-ban megjelentek olyan metodusok amelyek kepesek konyvtarat bejarni meghozza ugy, hogy a konyvtar
    // tartalmat streamkent adja vissza. Valamint lehetseges ezt rekurziv modon megadni. Valamint ami meg megjelent,
    // hogy egy szoveges file tartalmat kepes soronkent Stream-kent visszaadni. Igy konnyen fel lehet ezt dolgozni.
    // Valamint ez nem csak fileokkal, hanem readerekkel is mukodik.

    public long numberOfCsvFiles(Path dir) {
        try (
            Stream<Path> entries = Files.list(dir) //ez a konyvtar tartalmat fogja visszaadni meghozza egy streambe
        ) {
            return entries.filter(p -> p.toString().endsWith(".csv"))
                .count();//csak azok az elemek fognak megmaradni a streambe amelyek .csv kiterjesztesuek es utana
            // ezeket megszamoljuk
        } catch (IOException ioe) {
            throw new IllegalStateException("Error reading directory", ioe);
        }
    }

    public Path largestCsvFile(Path dir) {//legnagyobb allomanyt kell visszaadnia ami csv kiterjesztesu
        try (
            Stream<Path> entries = Files.walk(dir)//ez rekurzivan jarja be a konyvtarakat
        ) {
            return entries.filter(p -> p.toString().endsWith(".csv"))//ki kell filterelni amik csv-re vegzodnek
                .max(Comparator.comparing(
                    EmployeesCsvReader::fileSize))//max metodusal ki kell valasztani a legnagyobbat es ekkor
                // parameterkent at kell adni egy komparatort. A comparing metodusban egy fuggvenyt szeretnenk
                // implementalni meghozza azt a fuggvenyt, hogy a path-hoz (p) szeretnenk hozzarendelni a Files.size
                // (-> Files.size(p))-t. Mivel a size metodus is dob exceptiont ezert sajnos a streameknel nem tudunk
                // mit csinalni az adott anonymus fuggvenyen belul (az adott lambda kifejezesen belul) kell ezt a
                // kivetelt lekezelnunk, Viszont ilyent ne csinaljunk, hogy a lambda-n belul ilyen bonyolult
                // szerkezeteket irunk (pl try-catch ag). Az ide segit alt + enter es extract to method reference es
                // ekkor ezt a metodus torzset kiszervezi egy kulon metodusba aminek a neve fileSize lesz
                .orElseThrow(() -> new IllegalArgumentException("Contains no CSV file"));
        } catch (IOException ioe) {
            throw new IllegalStateException("Error reading directory", ioe);
        }
    }

    public List<Employee> readEmployees(Path path) {//ez az allomany tartalmaz csv formatumba alkalmazottakat
        try (//ha filet olvasunk akkor azt illik bezarni ezert a try with resources szerkezetet erdemes alkalmaznunk
             Stream<String> lines = Files.lines(path)//visszadja a file sorait String streamkent
        ) {
            return lines.map(Employee::fromCsvLine)
                .collect(
                    Collectors.toList());//soronkent vegigmegyunk es atkonvertaljuk a String-eket alkalmayott
            // peldanza a map metodust hasznalva es utana ezt osszegyujtjuk egy listaba
        } catch (IOException ioe) {//lines dobja
            throw new IllegalArgumentException("Can not read file");
        }
    }

    public List<Employee> readDefaultEmployees() {//ez nagyon hasonlo, de itt nem en hozom letre az allomanyt, hanem
        // az allomany az a classPath-on talalhato -> src/main/resources/java8io/employees.csv
        //ugye class pathrol beolvasni allomanyt a classnak a getResourceAsStream metodusaval lehetseges. Mivel
        // tudjuk, hogy syoveges ez az allomany ezert nyugodtan hasznalhatunk bufferedReadert. Es a buffered
        // readernek a lines metodusa visszaadja streamkent a sorokat ugyanugy, mint az elobb a file eseten
        try (//ha filet olvasunk be akkor azt erdemes lezarni ezert tegyuk tryWithResourcesbe
             Stream<String> lines = new BufferedReader(
//azert kellett beburkolni egy buffered readerbe, hogy soronkent el tudjuk olvasni. Es ennek adjuk at parameterkent
// az InputStreamReadert
                 new InputStreamReader(EmployeesCsvReader.class.getResourceAsStream("employees.csv"),
                     StandardCharsets.UTF_8)).lines()
             //erdemes megadni a karakter kodolast kulonben a platform alap karakter kodolasat fogja figyelembe venni
             // az pedig elterhet
//azert nem kell a nevnel / jel mivel az aktualis io packageban van es onnan fogja az allomanyt keresni
        ) {
            return lines.skip(1).map(
                    Employee::fromCsvLine) //itt a factory metodust hivja meg ami letrehozza a sorokbol az adott
                // employee-t. Itt hasznalhatjuk a method referencet
                .collect(
                    Collectors.toList()); //az elso sora az formatum leiro. Ezt ki kell hagyni. Szerencsere a
            // stream-eknel van erre egy kozbulso operator
            //es a skip metodus aminek megmondjuk hogy egyetlen egz darab sort ugorjon at. Majd hivjuk meg a mappet
        }
    }

    private static long fileSize(Path p) {
        try {
            return Files.size(p);
        } catch (IOException e) {
            throw new IllegalStateException("Error getting files size", e);
        }
    }
}
