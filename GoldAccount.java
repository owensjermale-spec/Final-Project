// Withdraw indefinitely
// 5% interest

public class GoldAccount extends Account
{
    // Constructors
    public GoldAccount() { this(0, 0, 0.05); }
    public GoldAccount(int id, double balance, double interestRate)
    {
        super(id, balance, interestRate);
    }
}