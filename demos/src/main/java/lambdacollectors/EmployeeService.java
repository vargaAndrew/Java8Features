package lambdacollectors;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeService {

    public String concatenateNames(List<Employee> employees) {
        return employees.stream()//lekerjuk a listahoz tartozo stream-et
            .map(Employee::getName)//Employee elemekbol szeretnenk neveket konvertalni
            .collect(Collectors.joining(", "));
    }

    public long sumSalaries(List<Employee> employees) {//matematikai muveletet vegzo kollektor
//        return employees.stream()
//                .collect(Collectors.summingInt(Employee::getSalary)); // ToIntFunction ,mivel megint objektumbol
//                atvaltunk int-re
        //ez a megoldas, ha egyszerre tobb mindent is szeretnenk gyujteni. Erre jo a summarizing
        return employees.stream()
            .collect(Collectors.summarizingInt(Employee::getSalary)).getSum();
    }

    public List<Employee> filterSalaryGreaterThan(List<Employee> employees, int min) {//filtereli az alkalamzottakat
        // fizetes alapjan. Tehat egy olyan listat hoz letre amelyben nem fog szerepelni azok alkalmazott akinek a
        // fizetese kisebb mint a masodik parameterkent megadott szam
        return employees.stream()
            .filter(e -> e.getSalary() >=
                min) //ez meg csak egy stream. A streamon atmeno elemeket azokat szepen ossze kell gyujteni. Erre van
            // a collect
            .collect(Collectors.toList());//meghivjuk a filter kozbulso o[eratort
        //a Collectors.toList egyszeruen belerakosgatja az elemeket a listaba. Ez a leggyakraban hasznalt kollektor
    }

    public Map<Long, Employee> groupById(List<Employee> employees) { //Mapet hoz letre ahol a kulcs az employee
        // azonositoja az erteke maga az employee. Ha a memoriabol gyorsan tobbszor el akarjuk erni az adott
        // alkalmazottat akkor map-bol gyorsan meg tudjuk tenni, hiszen listaban mindig keresni kellene
        return employees.stream()
            .collect(Collectors.toMap(//a toMap-al megadhattuk, hogy hogyan alakitsa ki a kulcsot es az erteket
                Employee::getId, //ez a keyMapper
                e -> e //masodik parametere, hogz mi legyen az ertek, ami nem mas mint onmaga
                //3. parameter a mergeFunction ami azt mondja meg, hogy mi van akkor ha az adott kulccsal mar van bent
                // ertek. Ez egyedi ID igy ez nem lehetseges
                //a 4. parameter a mapFactory, ami azt mondja meg, hogy milyen tipusu legyen map. Tehat, hogy a map
                // interfacenak melyik implementaciojat valassza. Ez szamunkra most lenyegtelen
            ));
    }

    public Map<String, List<Employee>> groupByDivision(List<Employee> employees) {//letrehoz egy mapet ahol a kulcs a
        // divizio neve az ertek pedig a benne levo alkalmazottak
        return employees.stream().collect(Collectors.groupingBy(
            Employee::getDivision));//megint hasznalhatunk egy method referencet (Employee::getDivision)
    }

    public Map<String, Long> noEmployeesPerDivision(List<Employee> employees) {//ey nem csak kigyujti, hanem azt is
        // megmondja, hogy melyik divizioba mennyi ember van
        return employees.stream().collect(Collectors.groupingBy(Employee::getDivision,
            Collectors.counting()));
    }

    public Map<Boolean, List<Employee>> partitionBySalary(List<Employee> employees, int boundary) {
        return employees.stream().collect(
            Collectors.partitioningBy(e -> e.getSalary() >= boundary)
//itt egy olyan parametert kell atadnunk ami predicate tehat egy booleant kell visszaadnunk, hogy az egyik vagy a
// masik csoportba tartozzon-e az adott alkalmazott
            //tehat az alkalmazotthoz (e) lekerjuk a fizeteset e.getSalary
            //partition muvelettel 2 reszre tudunk szedni egy csoportot. Ha a masodik parameterben megadott erteknel
            // kisebb a fizetese akkor az egyik csoportba ha nagyobb akkor a masikba
        );
    }

    public Map<String, Optional<Integer>> minSalaryByDivision(List<Employee> employees) {//visszaadja divizionkent a
        // legkisebb fizetesu alkalmazotjat es annak a fizeteset adja vissza
        return employees.stream().collect(
            Collectors.groupingBy(Employee::getDivision,//divizionkent csoportosit
                Collectors.mapping(Employee::getSalary, Collectors.minBy(Comparator.naturalOrder())))
//masodik parameterkent atadunk egy downStreamet, hogy mit csinaljon ezekkel az elemekkel, amik az adott csoportba
// tartoznanak. Es most meg kell mondani, hogy mit csinaljon az egy divizioba tartozo alkalmazottakkal.
            //a 3. downstream a minBy, amivel kivalasztjuk azt akinek legkisebb a salary erteke
        );
    }
}
