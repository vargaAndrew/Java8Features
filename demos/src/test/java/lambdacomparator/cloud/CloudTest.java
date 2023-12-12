package lambdacomparator.cloud;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CloudsTest {

    List<CloudStorage> storages;

    List<CloudStorage> storagesWithoutFree;

    @BeforeEach
    void initStorages() {
        storagesWithoutFree = List.of(
            new CloudStorage("iDrive", 2000, 52.12, PayPeriod.ANNUAL),
            new CloudStorage("iDrive", 5000, 74.62, PayPeriod.ANNUAL),
            new CloudStorage("pCloud", 500, 175.0, PayPeriod.LIFETIME),
            new CloudStorage("pCloud", 2000, 350.0, PayPeriod.LIFETIME),
            new CloudStorage("pCloud", 500, 3.99, PayPeriod.MONTHLY),
            new CloudStorage("pCloud", 2000, 7.99, PayPeriod.MONTHLY),
            new CloudStorage("Zoolz", 100, 35.88, PayPeriod.ANNUAL),
            new CloudStorage("Zoolz", 500, 83.88, PayPeriod.ANNUAL),
            new CloudStorage("Zoolz", 1000, 12.99, PayPeriod.MONTHLY),
            new CloudStorage("Mega", 200, 4.99, PayPeriod.MONTHLY),
            new CloudStorage("Mega", 1000, 9.99, PayPeriod.MONTHLY),
            new CloudStorage("Mega", 4000, 19.99, PayPeriod.MONTHLY),
            new CloudStorage("OneDrive", 100, 1.99, PayPeriod.MONTHLY)
        );
        storages = new ArrayList<>(storagesWithoutFree);
        storages.addAll(List.of(
            new CloudStorage("iDrive", 5),
            new CloudStorage("Zoolz", 1),
            new CloudStorage("Mega", 50),
            new CloudStorage("OneDrive", 5)
        ));
    }

    @Test
    void testAlphabeticallyFirst() {
        Clouds clouds = new Clouds();
        CloudStorage cloudStorage = clouds.alphabeticallyFirst(storages);

        assertEquals("iDrive", cloudStorage.getProviderName());
    }

    @Test
    void testBestPriceForShortestPeriodWithoutFree() {
        Clouds clouds = new Clouds();
        CloudStorage cloudStorage = clouds.bestPriceForShortestPeriod(storagesWithoutFree);

        assertEquals(1.99, cloudStorage.getPrice(), 0.0000001);
        assertEquals("OneDrive", cloudStorage.getProviderName());
    }

    @Test
    void testBestPriceForShortestPeriod() {
        Clouds clouds = new Clouds();
        CloudStorage cloudStorage = clouds.bestPriceForShortestPeriod(storages);

        assertEquals(0, cloudStorage.getPrice(), 0.0000001);
    }

    @Test
    void testWorstOffersWithoutFree() {
        Clouds clouds = new Clouds();
        List<CloudStorage> worstStorages = clouds.worstOffers(storagesWithoutFree);

        assertEquals(3, worstStorages.size());
    }

    @Test
    void testWorstOffers() {
        Clouds clouds = new Clouds();
        List<CloudStorage> worstStorages = clouds.worstOffers(storages);

        assertEquals(3, worstStorages.size());
        assertEquals(List.of("Zoolz", "Mega", "OneDrive"),
            worstStorages.stream().map(CloudStorage::getProviderName).collect(Collectors.toList()));
        assertEquals(List.of(100, 200, 100),
            worstStorages.stream().map(CloudStorage::getStorageSize).collect(Collectors.toList()));
        assertEquals(List.of(PayPeriod.ANNUAL, PayPeriod.MONTHLY, PayPeriod.MONTHLY),
            worstStorages.stream().map(CloudStorage::getPayPeriod).collect(Collectors.toList()));
        assertEquals(List.of(35.88, 4.99, 1.99),
            worstStorages.stream().map(CloudStorage::getPrice).collect(Collectors.toList()));
    }

    @Test
    void testWorstOffersWithOneProvider() {
        Clouds clouds = new Clouds();
        List<CloudStorage> worstStorages = clouds.worstOffers(
            List.of(new CloudStorage("iDrive", 2000, 52.12, PayPeriod.ANNUAL)));

        assertEquals(1, worstStorages.size());
        assertEquals(List.of("iDrive"),
            worstStorages.stream().map(CloudStorage::getProviderName).collect(Collectors.toList()));
        assertEquals(List.of(2000),
            worstStorages.stream().map(CloudStorage::getStorageSize).collect(Collectors.toList()));
        assertEquals(List.of(PayPeriod.ANNUAL),
            worstStorages.stream().map(CloudStorage::getPayPeriod).collect(Collectors.toList()));
        assertEquals(List.of(52.12), worstStorages.stream().map(CloudStorage::getPrice).collect(Collectors.toList()));
    }
}