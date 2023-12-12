package lambdaoptional;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Costumer {

    private final List<Costume> costumes;

    public Costumer(List<Costume> costumes) {
        this.costumes = costumes;
    }

    public List<Costume> getCostumes() {
        return List.copyOf(costumes);
    }

    public Optional<Costume> findFirstCostumeWithKeyWord(String keyWord) {
        Optional<Costume> foundCostume = findFirst(costume -> costume.getDescription().contains(keyWord));
        return foundCostume;
    }

    public Optional<Costume> findFirstCheaperCostume(int maxPrice) {
        Optional<Costume> cheaperOption = findFirst(costume -> costume.getPrice() < maxPrice);
        return cheaperOption;
    }

    public Optional<Costume> findFirstCostumeSameSize(Size size) {
        Optional<Costume> sameSize = findFirst(costume -> costume.getSize() == size);
        return sameSize;
    }

    private Optional<Costume> findFirst(Predicate<Costume> condition) {
        for (Costume actualCostume : costumes) {
            if (condition.test(actualCostume)) {
                return Optional.of(actualCostume);
            }
        }
        return Optional.empty();
    }
}
