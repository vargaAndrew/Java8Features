package lambdaintro;

import java.util.List;
import java.util.function.Predicate;

public class RealEstateAgency {

    private List<Flat> flatsForSale;

    public RealEstateAgency(List flatsForSale) {
        this.flatsForSale = flatsForSale;
    }

    public Flat findFirstCheaperFlat(int maxPrice) {
        Flat cheaperFlat = findFirst(flat -> flat.getPrice() < maxPrice);
        return cheaperFlat;
    }

    public Flat findFirstGreaterFlat(double minArea) {
        Flat biggerFlat = findFirst(flat -> flat.getArea() > minArea);
        return biggerFlat;
    }

    public Flat findFirstFlatInSameTown(String town) {
        Flat flatInTown = findFirst(flat -> flat.getAddress().startsWith(town));
        return flatInTown;
    }

    private Flat findFirst(Predicate<Flat> condition) {
        for (Flat actualFlat : flatsForSale) {
            if (condition.test(actualFlat)) {
                return actualFlat;
            }
        }
        throw new IllegalArgumentException("No such flat");
    }
}
