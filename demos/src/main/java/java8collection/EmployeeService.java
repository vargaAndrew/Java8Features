package java8collection;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeService {

    //Mashogy is meg lehet oldani ezeket a funkcionalitasokat mondjuk stream-ek vagy feltetelek hasznalataval. Ha
    // ezek mellett dontunk akkor ismernunk kell a mukodesuket

    public List<Employee> removeWhereSalaryIsLowerThan(List<Employee> employees, int maxSalary) {//listabol el kell
        // tavolitani azokat az employe-kat akik a parameterben atadott fizetesnel kisebb a fizetese
        employees.removeIf(e -> e.getSalary() <
            maxSalary);//parameterul egy predicatet kell megadni (boolean). Annyit tesz, hogy az employee-hez (e)
        // hozza kell rendelni egy boolean erteket
        return employees;//amit parameterul kap listat azt is adja vissza. Ez egy kedvelt megoldas, hiszen igy
        // lathato, hogz amit ez visszaad azzal azonnal muveletet lehet vegezni
    }

    public List<String> trimmedNames(List<String> names) {
        names.replaceAll(String::trim);//minden elemet kicsereli
        return names;
    }

    public List<Employee> sortByName(List<Employee> employees) {
        employees.sort(Comparator.comparing(
            Employee::getName));//komparatornak parameterul egy functiont kell atadni, hogy mi alapjan tortenjen a
        // rendezes
        //sort-ot az teszi lehetove, hogy mostmar az interfacekban is vannak default metodusok
        return employees;
    }

    public List<Employee> convertNamesToLowerCase(List<Employee> employees) {
        employees.forEach(e -> e.setName(
            e.getName()
                .toLowerCase()));//parameterul atadott consumert hivhatunk meg minden egyes lista elemre. A consumer
        // egy elemet fogad es nem ad vissza mast. Ezen az elemen dolgozik meghozza ugy, hogy kisbetusiti a neveket
        // e.setName(e.getName().toLowerCase()))
        //Annyit erdemes megjegyezni, hogz ezek a metodusok ezen listan dolgoznak es a listaban modositjak az elemet.
        // Azonban nagyon gyakran ezt ugy szoktuk megvalositani, hogy uj listat hozunk letre. Ebben az esetben
        // azonban nem ezeket az ujonan megjelent metodusokat szoktuk hasznalni, hanem helyette streamokat hasznalunk
        // es az elemeket osszegyujtjuk egy uj collectionba a collect metodus hasznalataval
        return employees;
    }

    public Map<String, Employee> firstEmployeeWithFirstName(List<Employee> employees) {
        Map<String, Employee> firstEmployees = new HashMap<>();//letrehozunk egy uj map-et
        for (Employee employee : employees) {//vegigmegyunk a parameterul atadott listan es a map-be csak akkor
            // tesszuk bele az adott elemet, hogyha a map az adott kulccsal tehat az adott vezeteknevvel nem
            // tartalmaz mar elemet
            String firstName = employee.getName().split(" ")[0];
            firstEmployees.putIfAbsent(firstName,
                employee);//vizsgalni kell hogyha a map-be meg nincs benne az adott ertek akkor beletesszuk, hiszen
            // most atlaltunk egy alkalmazottat ezzel a vezeteknevvel. De abban az esetben ha szerepel mar benne,
            // akkor nem tesszuk bele, hanem atlepjuk. Ezzel garantaljuk azt, hogy mindig az elso talat elem kerul
            // bele a Map-be. Az alabbi modon tudjuk megoldani, ha nem akarunk feltetel vizsgalatot (if)-et hasznalni
        }
        return firstEmployees;
    }

    public Map<String, Integer> countByFirstName(List<Employee> employees) {//termeszetesen ezt is implementalhatnank
        // a streamekkel, de mi most az uj collection metodusokat hasznaljuk
        Map<String, Integer> counts = new HashMap<>();
        for (Employee employee : employees) {
            String firstName = employee.getName().split(" ")[0];
            counts.merge(firstName, 1, (i, j) -> i +
                j);//merge metodus ugy mukodik, hogy meg kell adni a kulcsot, majd masodik parameterkent meg kell
            // adni az erteket jelen esetben ha nem szerepel benne akkor az egyes erteket vegye fel. Ha szerepel
            // akkor a beirando es a regi ertekhez (i, j) rendeljunk hozza valamifele erteket. Ebben az esetben a
            // kulcs es az erteket osszeadjuk

            //ezt az alabbi modon is tudjuk implemetalni
//            counts.compute(firstName, (k, v) -> v == null ? v = 1 : v + 1); //itt tenyleg kulcs - ertek parjaink
//            vannak. itt meg kell nezni azt, hogy szerepel-e ertek v==null (az elozo ertek null-e). Ha nem szerepel
//            benne ertek akkor az 1-et bele kell tenni. Abban az esetben ha mar szerepel ertek, akkor hozza kell
//            adnunk az ertekhez 1-et
        }
        return counts;
    }

    public Map<Long, Employee> updateEmployees(Map<Long, Employee> base, List<Employee> changedEmployees) { //baseban
        // a kulcs az alkalmazott azonositoja az erteke maga az alkalmazott
        //gyakorlatilag annyit kell csinalni, hogy az az alkalmazott aki szerepel a map-ben es a lisaban is azt
        // csereljuk ki a map-ben a listaban szereplore. Amennyiben nem szerepel akkor nem csinalunk semmit
        for (Employee employee : changedEmployees) {
            //ezeket a metodusokat tipikusan valamilyen felteteles szerkezettel egy if-el tudnank implementalni, de
            // csak azert, hogy rovidebb legyen ezert vezettek be ezeket a metodusokat
            base.replace(employee.getId(), employee);
        }
        return base;
    }

    public Map<Long, Integer> salariesChanged(Map<Long, Integer> base, List<Employee> changedEmployees) {
        //itt is megintcsak hasznalhatnank feltetelt
        for (Employee employee : changedEmployees) {
            base.remove(employee.getId(),
                employee.getSalary());//ebbol a mapbol ami az alkalmazott fizeteset es azonositojat tartalmazza csak
            // akkor vesszuk ki az elemet hogyha az adott ertek (employee.getSalary()) pontosan megegyezik az
            // alkalmazott fizetesevel
            //Eddig az egyparameteres remove az adott elemet a mapbol minden esetben kivette. Viszont a remove
            // ketparameteres formaja ugy mukodik, hogy az adott elemet csak akkor veszi ki a mapbol, ha az erteke
            // megegyezik a masodik parameterrel
        }
        return base;
    }
}
