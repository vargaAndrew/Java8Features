package lambdaoptional;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class EmployeeService {

    public Optional<Employee> findFirst(List<Employee> employees, Predicate<Employee> condition) {
        for (Employee employee : employees) {
            if (condition.test(employee)) {
                return Optional.of(employee);//ez a statikus optional metodus
            }
        }
        //ha nem talalunk az adott feltetelnek megfelelo alkalmazottat akkor nem exceptiont dobunk, hanem egy ures
        //optional-al (egy ures dobozzal terunk vissza)
        return Optional.empty();
    }
}
