package lambdacomparator.account;

public class BankAccount implements Comparable<BankAccount> {

    private String accountNumber;
    private String ownerName;
    private double balance;

    public BankAccount(String accountNumber, String ownerName, double balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public int compareTo(BankAccount anotherAccount) {
        return accountNumber.compareTo(anotherAccount.accountNumber);
    }
}