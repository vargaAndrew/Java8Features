package lambdacomparator.cloud;

public class CloudStorage implements Comparable<CloudStorage> {

    private static final int STANDARD_SIZE = 1000;
    private static final int STANDARD_PERIOD = 12;

    private String providerName;
    private int storageSize;
    private double price;
    private PayPeriod payPeriod;

    public CloudStorage(String providerName, int storageSize, double price, PayPeriod payPeriod) {
        this.providerName = providerName;
        this.storageSize = storageSize;
        this.price = price;
        this.payPeriod = payPeriod;
    }

    public CloudStorage(String providerName, int storageSize) {
        this.providerName = providerName;
        this.storageSize = storageSize;
    }

    public String getProviderName() {
        return providerName;
    }

    public int getStorageSize() {
        return storageSize;
    }

    public double getPrice() {
        return price;
    }

    public PayPeriod getPayPeriod() {
        return payPeriod;
    }

    @Override
    public int compareTo(CloudStorage o) {
        double currentValue = this.payPeriod != null ?
            this.price * STANDARD_PERIOD / this.payPeriod.getLength() / this.storageSize * STANDARD_SIZE : 0;
        double otherValue =
            o.payPeriod != null ? o.price * STANDARD_PERIOD / o.payPeriod.getLength() / o.storageSize * STANDARD_SIZE :
                0;
        return Double.compare(currentValue, otherValue);
    }
}
