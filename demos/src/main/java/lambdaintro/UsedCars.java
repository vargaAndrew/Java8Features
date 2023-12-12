package lambdaintro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsedCars {

    private List<Car> carsForSale;

    public UsedCars(List<Car> carsForSale) {
        this.carsForSale = carsForSale;
    }

    public List<Car> listCarsByPrice() {
        List<Car> sorted = new ArrayList<>(carsForSale);
        sorted.sort((car, anotherCar) -> Integer.valueOf(car.getPrice()).compareTo(anotherCar.getPrice()));
        return sorted;
    }

    public List<Car> listCarsByLengthDesc() {
        List<Car> sorted = new ArrayList<>(carsForSale);
        //sorted.sort(Comparator.comparingDouble(Car::getLength));
        sorted.sort((car, anotherCar) -> Double.valueOf(car.getLength()).compareTo(anotherCar.getLength()));
        Collections.reverse(sorted);
        return sorted;
    }

    public List<Car> listCarsOneBrandByType(String brand) {
        List<Car> sortedCarsSelectedByBrand = getCarsByBrand(brand);
        sortedCarsSelectedByBrand.sort((car, anotherCar) -> car.getType().compareTo(anotherCar.getType()));
        return sortedCarsSelectedByBrand;
    }

    private List<Car> getCarsByBrand(String brand) {
        List<Car> carsWithSameBrand = new ArrayList<>();
        for (Car actualCar : carsForSale) {
            if (actualCar.getBrand().equals(brand)) {
                carsWithSameBrand.add(actualCar);
            }
        }
        return carsWithSameBrand;
    }


}
