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
