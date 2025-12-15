import java.util.Date;

// Info for all accounts:
// account number, balance, and customer info (cust id, name, etc)

public class Account
{
    private int id;
    private double balance;
    private final Date DATE_CREATED;
    private double interestRate;
    private Customer customer;

    // Constructors
    public Account(int id, double balance) { this(id, balance, 0); }
    public Account(int id, double balance, double interestRate)
    {
        this.id = id;
        this.balance = balance;
        this.interestRate = interestRate;
        this.DATE_CREATED = new Date();
    }

    // Accessors and Mutators
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public double getBalance() { return this.balance; }
    public Date getDateCreated() { return DATE_CREATED; }
    public double getInterestRate() { return interestRate; }
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }
    public Customer getCustomer() { return this.customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    // Withdraw and Deposit method
    public void withdraw(double amount) { this.balance -= amount; }
    public void deposit(double amount) { this.balance += amount; }

    @Override
    public String toString()
    {
        return "Account ID: " + getId() +
                "\nAccount Type: " + getClass().getSimpleName() +
                "\nCustomer Name: " + getCustomer().getName() +
                "\nBalance: $" + String.format("%.2f",getBalance());
    }
}

ATM Project
