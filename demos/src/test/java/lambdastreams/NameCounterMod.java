package lambdastreams;

public class NameCounterMod { //ez modifiable lesz nem uj peldanyt ad vissza, hanem a letezo objektumot fogja modositani
    //pontosabban az allapotat fogja modositani.

    private int threePartName = 0;

    private int twoPartName = 0;

    public NameCounterMod() {
    }

    public NameCounterMod(int threePartName, int twoPartName) {
        this.threePartName = threePartName;
        this.twoPartName = twoPartName;
    }

    public void add(Employee employee) {//void a vissyateresi erteke mivel nem uj objektumot hozunk letre, hanem az
        // allapotat modositjuk
        if (employee.getName().split(" ").length == 2) {
            twoPartName++;
        } else {
            threePartName++;
        }
    }

    public void add(NameCounterMod other) {
        this.twoPartName += other.twoPartName;
        this.threePartName += other.threePartName;
    }

    public int getThreePartName() {
        return threePartName;
    }

    public int getTwoPartName() {
        return twoPartName;
    }
}
