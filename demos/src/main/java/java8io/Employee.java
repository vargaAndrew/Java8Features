package java8io;

public class Employee {

    private Long id;

    private String name;

    private int salary;

    public Employee(Long id, String name, int salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //itt van egy statikus factory metodus (egy Simple factory)
    public static Employee fromCsvLine(String line) {
        String[] tokens = line.split(",");//a parameterkent kapot String-et elvagja vesszonk alapjan
        long id = Long.parseLong(tokens[0]);//utana az elso vesszoig tartozo reszt azt id-kent tarolja el
        String name = tokens[1];
        int salary = Integer.parseInt(tokens[2]);
        return new Employee(id, name, salary);
    }
}
