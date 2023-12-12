package lambdacomparator;

import java.util.Comparator;
import java.util.List;

public class EmployeeService {

    public List<Employee> sortByName(List<Employee> employees) {
//        employees.sort(new Comparator<Employee>() { //ez a regi modszer amikor egy Comparatort hozunk letre
//            @Override
//            public int compare(Employee o1, Employee o2) {
//                return o1.getName().compareTo(o2.getName());
//            }
//        });

//        employees.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));

        //A comparator egy funkcionalis interface, ami azt jelenti, hogy egy darab metodus van benne.
        employees.sort(Comparator.comparing(Employee::getName));
        return employees;
    }

    public List<Employee> sortBySalaryThanName(List<Employee> employees) { //ez amikor tobb feltetel alapjan
        // szeretnenk rendezni
        employees.sort(Comparator.comparingInt(Employee::getSalary).thenComparing(Employee::getName));
        return employees;
    }

    public List<Employee> sortByCardNumberNullsFirst(List<Employee> employees) {
        employees.sort(Comparator.comparing(Employee::getCardNumber, Comparator.nullsFirst(Comparator.naturalOrder()))
            .thenComparing(Employee::getName));//azok kozott akiknek a cardNumbere null is legyen egy rendezes
        return employees;
    }


    public List<Employee> sortByNameReversed(List<Employee> employees) {
        employees.sort(Comparator.comparing(Employee::getName).reversed());

//        employees.sort(Comparator.comparing(Employee::getName, Comparator.reverseOrder()));
        return employees;
    }

}
