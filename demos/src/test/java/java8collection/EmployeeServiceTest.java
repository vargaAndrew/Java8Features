package java8collection;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class EmployeeServiceTest {

    private EmployeeService employeeService = new EmployeeService();

    private List<Employee> employees = Collections.unmodifiableList(Arrays.asList(
        new Employee(1L, "John Doe", 180_000),
        new Employee(2L, "Jane Doe", 200_000),
        new Employee(3L, "Joe Doe", 100_000),
        new Employee(4L, "John Smith", 100_000)));

    @Test
    public void test_removeWhereSalaryIsLowerThan() {
        List<Employee> removed = employeeService.removeWhereSalaryIsLowerThan(new ArrayList<>(employees),
            190_000);//lathatjuk, hogy employees egy modosithatatlan lista, de itt (new ArrayList..) lepeldanyositunk
        // egy uj listat parameterul atadva neki az elobbi modosithatatlan listat es ezert az elemek atmasolasra
        // kerulnek ebbe a modosithato listaba es ezert ebbol mar tenyleg lehet elemet eltavolitani
        assertEquals(Arrays.asList("Jane Doe"),
            removed.stream().map(Employee::getName)
                .collect(
                    Collectors.toList()));//itt a listat streame alakitjuk majd meghivjuk a map metodust, ami az
        // Employee objektumokbol Stringeket gyart meghozza a getName metodusanak a meghivasaval es utana ezeket
        // gyujti ossze egy Listaba (String)
    }

    @Test
    public void test_trimmedNames() {
        List<String> names = Arrays.asList("    John Doe    ", "   Jane Doe    ");
        List<String> replaced = employeeService.trimmedNames(names);//itt a spaceket kell eltavolitani
        assertEquals(Arrays.asList("John Doe", "Jane Doe"), replaced);
    }

    @Test
    public void test_sortByName() {//parameterkent atadott listat nev alapjan rendezze be
        List<Employee> sorted = employeeService.sortByName(new ArrayList<>(employees));
        assertEquals(Arrays.asList("Jane Doe", "Joe Doe", "John Doe", "John Smith"),
            sorted.stream().map(Employee::getName).collect(Collectors.toList()));
    }

    @Test
    public void test_convertNamesToLowerCase() {//parameterul atadunk egy listat az alkalmazottak listajat es azt
        // szeretnenk ha a nevuket kisbetusitene
        List<Employee> converted = employeeService.convertNamesToLowerCase(new ArrayList<>(employees));
        assertEquals(Arrays.asList("john doe", "jane doe", "joe doe", "john smith"),
            converted.stream().map(Employee::getName).collect(Collectors.toList()));
    }

    @Test
    public void test_countByFirstName() {
        Map<String, Integer> counts = employeeService.countByFirstName(new ArrayList<>(employees));
        assertEquals(Integer.valueOf(2), counts.get("John"));
        assertEquals(Integer.valueOf(1), counts.get("Jane"));
    }

    @Test
    public void test_firstEmployeeWithFirstName() {//gyakorlatilag egy map-et ad vissza, amelyet ugy allitunk elo,
        // hogz az alkalmazottaknak vesszuk a vezetek nevet es megnezzuk az elso olyan alkalmazottat akinek ugyanez a
        // vezetekneve. Es ezt igy kigyujtjuk egy map-be
        Map<String, Employee> names = employeeService.firstEmployeeWithFirstName(employees);
        assertEquals(3, names.size());
        assertEquals("John Doe", names.get("John").getName());
        assertEquals("Jane Doe", names.get("Jane").getName());
    }

    @Test
    public void test_updateEmployees() {
        List<Employee> changedEmployees = Arrays.asList(new Employee(1L, "John Doe", 250_000),
            new Employee(5L, "Jane Smith", 100_000));

        Map<Long, Employee> updated = employeeService.updateEmployees(
            new HashMap<>(employees.stream().collect(Collectors.toMap(Employee::getId, e -> e))),
            changedEmployees); //itt is van egy stream meghozza azert, hogy a listat map-e alakitsam. Ehhez hasznalom
        // a Collectors.toMap-et ahol a kulcs az a getId method reference az ertek pedig onmaga azaz az employee
        // objektum lesz

        assertEquals(4, updated.size());
        assertEquals(250_000, updated.get(1L).getSalary());
    }

    @Test
    public void test_salariesChanged() {
        List<Employee> changedEmployees = Arrays.asList(new Employee(1L, "John Doe", 180_001),
            new Employee(2L, "Jane Doe", 200_001),
            new Employee(3L, "Joe Doe", 100_000),
            new Employee(4L, "John Smith", 100_001));

        Map<Long, Integer> changed = employeeService.salariesChanged(
            employees.stream().collect(Collectors.toMap(Employee::getId, Employee::getSalary)),
            changedEmployees
        );

        assertEquals(3, changed.size());
        assertFalse(changed.containsKey(3L));
    }
}
