package lambdaprimitives;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EmployeeService {

    public List<Employee> generateEmployees(String prefix, int number) {//generalnia kell egy employee listat
        // meghozza ugy, hogy az
        //alkalmazottak nevei az elso parameterkent megadott prefix-el kezdodjenek es legyen a nevukhoz
        // hozzakonkatenalva a sorszamuk, valamint megadhatunk 2. parameterkent egy egesz szamot, hogy hagy darab
        // alkalmazottat szeretnenk legeneralni. Ha tesztadatot szeretnenk generalni akkor ez a metodus hasznos lehet
        return IntStream
            .range(0,
                number)//kezdo ertek az elso parameter zaro ertek a masodik parameter (ennyi darab erteket fog
            // generalni, egesz szamokat 0-tol az itt megadott szamig, de ez a szam mar nem lesz benne, alulrol zart
            // felulrol nyitott intervallum)
            .mapToObj(i -> new Employee(prefix + " " + i, 0, null)) // IntFunction
            //azt szeretnenk, hogy minden egyes egesz szamhoz peldanzositson le egy uj employeet. vissza kell
            // valtanunk Object tipusra mert a sima map tovabra is intel dolgozna. Az egesz szambol hozzon letre egy
            // alkalmazottat.
            .collect(Collectors.toList());
    }

    public long sumSalary(List<Employee> employees) {
        return employees.stream().mapToInt(Employee::getSalary).sum(); // ToIntFunction
        //az alkalmazottak listajanak lekerjuk a streamjet. Utana ezt pedig szeretnenk atvaltani a fizetesre es utana
        // kesobb ezt fogjuk osszegezni. Ehhez megint egy primitiv tipust szeretnek hasznalni ezert nem elegendo a
        // map metodus, mert az objektumbol objektumot csinal
    }

    public Boundaries salaryBoundaries(List<Employee> employees) {//egyszerre kell visszaadni a minimum es a maximum
        // erteket. Ezt megtehetnenk ugy, hogy 2x jarjuk vegig az employees listat. Azonban, hogy gyorsabb legyen
        // egyben jarjuk vegig es gyujtsuk ki a minimum es a maximum ertekeket is.
        //Erre van a summaryStatistic metodus ami pont ezt csinalja, hogy sokmindent gyujt mikozben bejarja ezt a
        // listat.
        IntSummaryStatistics stat = employees.stream().mapToInt(Employee::getSalary).summaryStatistics();
        return new Boundaries(stat.getMin(), stat.getMax());
    }

    public static class Boundaries {
        private int min;

        private int max;

        public Boundaries(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }
    }
}
