// $10/m maintenance fee
// 6% interest fee
// Withdraw cannot exceed balance

public class RegularAccount extends Account
{
    // Fixed monthly maintenance fee ($10)
    public final double MAINTENANCE_FEE = 10.0;

    // Constructors
    public RegularAccount() {  super(0, 0, 0);  }
    public RegularAccount(int accountNumber, double balance, double interestRate)
    {
        super(accountNumber, balance, interestRate);
    }

    // Override withdraw: cannot exceed balance
    // Regular accounts CANNOT withdraw more than their current balance
    @Override
    public void withdraw(double amount) {
        if ((this.getBalance() - amount) < 0) {
            System.out.println("Insufficient funds.");
            return;
        }

        // If requested withdrawal is greater than the available balance,
        // withdraw ONLY the amount available (set balance to 0)
        if (amount > this.getBalance())
            // Withdraw entire balance instead
            super.withdraw(this.getBalance());
        else
            // Otherwise perform a normal withdraw
            super.withdraw(amount);
    }
}