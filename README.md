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

import java.util.Scanner;

public class BankDemo
{
    final private static Scanner s = new Scanner(System.in);
    private static final Bank bank = new Bank();

    public static void main(String[] args)
    {
        boolean doLoop = true;
        Customer c;
        String input;
        double amount = 0;
        int accountId = -1;
        Account a = null;

        while (doLoop)
        {
            displayMenu();
            input = s.nextLine();
            switch (input)
            {
                case "1":
                    c = createCustomer();
                    bank.openCheckingAccount(c);
                    break;
                case "2":
                    c = createCustomer();
                    bank.openGoldAccount(c);
                    break;
                case "3":
                    c = createCustomer();
                    bank.openRegularAccount(c);
                    break;
                case "4": // Deposit
                    a = getAccount(); // get account
                    if (a == null)
                    {
                        // invalid account//non-existent account
                        System.out.println("The account ID you provided does not exist, please try again.");
                        break;
                    }
                    amount = getAmount();
                    a.deposit(amount);
                    System.out.println("You have deposited $" + amount + " and your new balance is $" + a.getBalance() + ".");
                    amount = 0;
                    break;
                case "5": // Withdraw
                    a = getAccount();
                    if (a==null)
                    {
                        // invalid account//non-existent account
                        System.out.println("The account ID you provided does not exist, please try again.");
                        break;
                    }
                    amount = getAmount();
                    a.withdraw(amount);
                    System.out.println("You have withdrawn $" + amount + " and your new balance is $" + a.getBalance() + ".");
                    amount = 0;
                    break;
                case "6": // Display Account Info
                    a = getAccount();
                    bank.displayAccountInfo(a.getId());
                    break;
                case "7":
                    a = getAccount();
                    bank.removeAccount(a);
                    break;
                case "8":
                    bank.endOfMonthAccountUpdate(bank.getAccounts());
                    System.out.println("All accounts are updated!");
                    break;
                case "9":
                    System.out.println("Generating bank report . . .");
                    bank.displayBankStats(bank.getAccounts());
                    break;
                case "10":
                    doLoop = false;
                    break;
                default:
                    System.out.println("Invalid input, please enter a number between 1-10");
                    break;
            }
//            System.out.println("Before s.next");

//            System.out.println("After s.next");
        }
    }

    private static Account getAccount()
    {
        int id = -1;

        while(true)
        {
            System.out.print("Please enter the account ID: ");

            try
            {
                String idStr = s.nextLine();
                id = Integer.parseInt(idStr);
            }
            catch (Exception e)
            {
                System.out.println("Invalid input, please enter a number.");
                continue;
            }
            try { bank.getAccounts().get(id); }
            catch (Exception e)
            {
                System.out.println("Invalid input, please enter a valid account ID.");
                continue;
            }

            if (id > -1)
                break;
        }
        return bank.getAccounts().get(id);
    }

    private static double getAmount()
    {
        double amount = -4;

        while(true)
        {
            System.out.print("Please enter the amount: ");

            try
            {
                String amountStr = s.nextLine();
                amount = Double.parseDouble(amountStr);
            }
            catch (Exception e)
            {
                System.out.println("Invalid input, please enter a number.");
                continue;
            }
            if (amount > -1)
                break;

            System.out.println("The amount cannot be less than 0. Please enter a number above 0.");
        }
        return amount;
    }

    public static Customer createCustomer()
    {
        String name;
        String phoneNumberString;
        long phoneNumber;
        String address;
        System.out.print("Please input the customer name: ");
        name = s.nextLine();
        while (true)
        {
            System.out.print("Please input customer phone number: ");
            phoneNumberString = s.nextLine();
            if (phoneNumberString.length() != 10)
            {
                System.out.print("Please input a 10 digit phone number.");
                continue;
            }
            try
            {
                phoneNumber = Long.parseLong(phoneNumberString);
            }
            catch (Exception e)
            {
                System.out.print("Please input a 10 digit phone number.");
                continue;
            }
            System.out.print("Please input customer address: ");
            address = s.nextLine();
            break;
        }
        return new Customer(name, address, phoneNumber);
    }

    public static void displayMenu()
    {
        System.out.println("    Bank Menu  ");
        System.out.println("====================");
        System.out.println("1. Create Checking Account");
        System.out.println("2. Create Gold Account");
        System.out.println("3. Create Regular Account");
        System.out.println("4. Deposit");
        System.out.println("5. Withdraw");
        System.out.println("6. Display Account Info");
        System.out.println("7. Remove an Account");
        System.out.println("8. Apply End of Month (Interest/Fees)");
        System.out.println("9. Display Bank Statistics");
        System.out.println("10. Exit");
        System.out.println("====================");
        System.out.println("Please input your choice (1-10): ");
    }
}

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
ATM Project
