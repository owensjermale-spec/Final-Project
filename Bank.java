import java.util.ArrayList;
import java.util.List;

public class Bank {
    private final ArrayList<Account> accounts = new ArrayList<>();

    public Bank() {}

    public void openCheckingAccount(Customer c)
    {
        CheckingAccount a = new CheckingAccount(this.accounts.size(), 0);
        a.setCustomer(c);
        this.accounts.add(a);
    }

    public void openRegularAccount(Customer c)
    {
        RegularAccount a = new RegularAccount(this.accounts.size(), 0, 0.06);
        a.setCustomer(c);
        this.accounts.add(a);
    }

    public void openGoldAccount(Customer c)
    {
        GoldAccount a = new GoldAccount(this.accounts.size(), 0, 0.05);
        a.setCustomer(c);
        this.accounts.add(a);
    }

    public List<Account> getAccounts() { return List.copyOf(this.accounts); }

    public void removeAccount(Account a) { this.accounts.remove(a); }
    public void removeAccount(int id) { this.accounts.remove(id); }

    public void endOfMonthAccountUpdate(List<Account> accounts)
    {
        double costOfMonthlyInterest = 0;
        double costOfTransactions = 0;

        for (Account a: this.accounts)
        {
            if (a instanceof CheckingAccount c)
            {
                costOfTransactions = c.calculateCostOfTransactions();
                c.resetMonthlyTransactionCount();
                String strFeesNotice = String.format("Deducting monthly transaction fees of $%s from account #%s. . .", costOfTransactions, c.getId());
                String strBalanceAfterFees = String.format("New balance is $%s", (Math.round(c.getBalance()) / 100.0));

                System.out.println(strFeesNotice);
                c.withdraw(costOfTransactions);
                System.out.println(strBalanceAfterFees);
            }
            else if (a instanceof RegularAccount r)
            {
                costOfMonthlyInterest = r.getInterestRate() * r.getBalance();
                double costOfMaintFee = r.MAINTENANCE_FEE;
                r.withdraw(costOfMonthlyInterest + costOfMaintFee);
            }
            else if (a instanceof GoldAccount g)
            {
                costOfMonthlyInterest = g.getInterestRate() * g.getBalance();
                g.withdraw(costOfMonthlyInterest);
            }
        }
        System.out.println("End of Month Account Updates Have Been Applied.");
    }

    // Deposit method
    public void deposit(int accountId, double amount)
    {
        if (accountId < 0 || accountId >= accounts.size())
        {
            System.out.println("Invalid account ID");
            return;
        }

        accounts.get(accountId).deposit(amount);
        System.out.println("Deposit successful. Deposited $" + amount);
    }

    // Withdraw method
    public void withdraw(int accountId, double amount)
    {
        if (accountId < 0 || accountId >= accounts.size())
        {
            System.out.println("Invalid account ID");
            return;
        }

        accounts.get(accountId).withdraw(amount);
        System.out.println("Withdraw successful. Withdrawal $" + amount);
    }

    // Display bank statistics
    public void displayBankStats(List<Account> accounts){

        //initialize variables for calculating balances
        double currentBalance;
        double bankHighestBalance = 0;
        double bankSumOfBalances = 0;
        double bankAverageBalance = 0;
        int bankNumberOfAccounts = accounts.size();
        int bankZeroBalanceAccounts = 0;

        for(Account a: this.accounts){
            currentBalance = a.getBalance();

            //check if balance is zero
            if(currentBalance == 0){
                bankZeroBalanceAccounts++;
            }

            //add balance to running sum
            bankSumOfBalances += currentBalance;

            //compare balance against highest balance so far
            if(currentBalance > bankHighestBalance){
                bankHighestBalance = currentBalance;
            }
        }

        //calculate average balance
        bankAverageBalance = bankSumOfBalances / bankNumberOfAccounts;


        System.out.println("=============== ACCOUNTS REPORT ===============");
        System.out.println(String.format("There are currently %s accounts in the banking system.", bankNumberOfAccounts));
        System.out.println(String.format("There are %s accounts with no balance.", bankZeroBalanceAccounts));
        System.out.println(String.format("The highest balance for an account is $%s", bankHighestBalance));
        System.out.println(String.format("The sum of all account balances is $%s", bankSumOfBalances));
        System.out.println(String.format("The average account balance is $%s", bankAverageBalance));

    }

    // Display account method
    public void displayAccountInfo(int accountId)
    {
        if  (accountId < 0 || accountId >= accounts.size())
        {
            System.out.println("Invalid account ID");
            return;
        }

        Account a = this.accounts.get(accountId);
        Customer c = a.getCustomer();

        System.out.println("Details of Account ID #: " + a.getId());
        System.out.println("--------------------------");
        System.out.println("Customer Phone: " + c.getPhone());
        System.out.println("Customer Name: " + c.getName());
        System.out.println("Customer Address: " + c.getAddress());
        System.out.println("Balance: $" + String.format("%.2f", a.getBalance()));

    }
}