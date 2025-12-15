// No interest fee
// Transaction fee: $3
// First 2 transactions are free
// Withdraw cannot exceed balance

public class CheckingAccount extends Account
{
    private int monthlyTransactionCount;
    private final int TRANSACTION_FEE = 3;

    // Constructors
    public CheckingAccount() { this(0, 0); }
    public CheckingAccount(int id, double balance)
    {
        super(id, balance);
        this.monthlyTransactionCount = 0;
    }

    public void resetMonthlyTransactionCount() { this.monthlyTransactionCount = 0; }

    @Override
    public void deposit(double amount)
    {
        this.monthlyTransactionCount+=1;
        super.deposit(amount);
    }

    // Override withdraw: cannot exceed balance
    // Regular accounts CANNOT withdraw more than their current balance
    @Override
    public void withdraw(double amount)
    {
        this.monthlyTransactionCount+=1;
        double newBalance = this.getBalance() - amount;
        if (newBalance < 0)
            super.withdraw(this.getBalance());
        else
        {
            double costOfTransactions = this.calculateCostOfTransactions();
            double newBalanceAfterTransactions = this.getBalance() - costOfTransactions;
            if (newBalanceAfterTransactions < this.TRANSACTION_FEE)
            {
                System.out.println("Unable to withdraw, the amount of transactions is too high for the balance, please deposit more.");
                return;
            }
            super.withdraw(amount);
        }
    }

    public double calculateCostOfTransactions() 
    {
        if (monthlyTransactionCount<=2)
            return 0;
        return (this.monthlyTransactionCount-2) * this.TRANSACTION_FEE;
    }

    public void endOfMonthAccountUpdate()
    { this.withdraw(this.getBalance() - this.calculateCostOfTransactions()); }
}