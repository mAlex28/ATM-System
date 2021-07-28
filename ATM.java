package atm;

import java.util.Scanner;

public class ATM {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank theBank = new Bank("Bank of Cringe");

        User nUser = theBank.addUser("John", "Doe", "1234");

        Account nAccount = new Account("Checking", nUser, theBank);
        nUser.addAccount(nAccount);
        theBank.addAccount(nAccount);

        User curUser; // current user

        while (true) {
            curUser = ATM.mainMenuPrompt(theBank, sc);

            ATM.printUserMenu(curUser, sc);
        }
    }

    // ATM login menu 
    public static User mainMenuPrompt(Bank theBank, Scanner sc) {
        String userID;
        String pin;
        User authUser;

        // prompt the user for user id untill a corrent one is reached
        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.printf("Enter user ID: ");
            userID = sc.nextLine();
            System.out.printf("Enter pin: ");
            pin = sc.nextLine();

            // get the user corresponding to the id and pin
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect userID or pin. " + "Please try again. ");
            }

        } while (authUser == null); // countinue looping untill sucessful loping
        return authUser;

    }

    public static void printUserMenu(User theUser, Scanner sc) {
        // print a summary of the user's accounts
        theUser.printAccountSummary();

        int choice;

        do {
            System.out.printf("Hello %s,\n", theUser.getFirstName());
            System.out.println(" 1) Transaction histroy");
            System.out.println(" 2) Withdraw");
            System.out.println(" 3) Diposit");
            System.out.println(" 4) Transfer");
            System.out.println(" 5) Quit");
            System.out.println();
            System.out.println("Select your task: ");
            choice = sc.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice");
            }

        } while (choice < 1 || choice > 5);

        switch (choice) {
            case 1:
                ATM.showTransHistory(theUser, sc);
                break;
            case 2:
                ATM.withdraw(theUser, sc);
                break;
            case 3:
                ATM.deposit(theUser, sc);
                break;
            case 4:
                ATM.transfer(theUser, sc);
                break;
            case 5:
                sc.nextLine();
                break;
        }

        // redisplay the menu if user doesn't quit     
        if (choice != 5) {
            ATM.printUserMenu(theUser, sc);
        }

    }

    public static void showTransHistory(User theUser, Scanner sc) {
        int theAcct;

        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + "of the transaction you want to see: ", theUser.numAccounts());
            theAcct = sc.nextInt() - 1;
            if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (theAcct < 0 || theAcct >= theUser.numAccounts());

        //print the trans histroy
        theUser.printAcctTransHistroy(theAcct);

    }

    public static void transfer(User theUser, Scanner sc) {
        int fromAcc;
        int toAcc;
        double amount;
        double accBalance;

        // account to transfer from 
        do {
            System.out.printf("Enter the number (1-%d) of the accunt\nto transfer from: ", theUser.numAccounts());
            fromAcc = sc.nextInt() - 1;
            if (fromAcc < 0 || fromAcc >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");

            }

        } while (fromAcc < 0 || fromAcc >= theUser.numAccounts());

        accBalance = theUser.getAccBalance(fromAcc);

        // account to transfer to
        do {
            System.out.printf("Enter the number (1-%d) of the accunt\n" + "to transfer to:", theUser.numAccounts());
            toAcc = sc.nextInt() - 1;
            if (toAcc < 0 || toAcc >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");

            }

        } while (toAcc < 0 || toAcc >= theUser.numAccounts());

        // amount to transfer
        do {
            System.out.printf("Enter the transfer amount (max Rs%.02f): Rs", accBalance);
            amount = sc.nextDouble();

            if (amount < 0) {
                System.out.println("Amount must be greater than zero");
            } else if (amount > accBalance) {
                System.out.printf("Amount must be less than the account balance of Rs.%.02f.\n", accBalance);
            }
        } while (amount < 0 || amount > accBalance);

        theUser.addAccountTransaction(fromAcc, -1 * amount, String.format("Transfer to account %s", theUser.getAccountUUID(toAcc)));
        theUser.addAccountTransaction(fromAcc, -1 * amount, String.format("Transfer to account %s", theUser.getAccountUUID(fromAcc)));

    }

    public static void withdraw(User theUser, Scanner sc) {
        int fromAcc;
        double amount;
        double accBalance;
        String memo;

        // account to transfer from 
        do {
            System.out.printf("Enter the number (1-%d) of the accunt\n to withdraw from: ", theUser.numAccounts());
            fromAcc = sc.nextInt() - 1;
            if (fromAcc < 0 || fromAcc >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");

            }

        } while (fromAcc < 0 || fromAcc >= theUser.numAccounts());

        accBalance = theUser.getAccBalance(fromAcc);

        // amount to transfer
        do {
            System.out.printf("Enter the withdraw amount (max Rs%.02f): Rs", accBalance);
            amount = sc.nextDouble();

            if (amount < 0) {
                System.out.println("Amount must be greater than zero");
            } else if (amount > accBalance) {
                System.out.printf("Amount must be less than the account balance of Rs.%.02f.\n", accBalance);
            }
        } while (amount < 0 || amount > accBalance);

        sc.nextLine();

        // get the memo 
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();

        // withdraw
        theUser.addAccountTransaction(fromAcc, -1 * amount, memo);
    }

    public static void deposit(User theUser, Scanner sc) {
        int toAcc;
        double amount;
        double accBalance;
        String memo;

        // account to transfer from 
        do {
            System.out.printf("Enter the number (1-%d) of the accunt\n to deposit:", theUser.numAccounts());
            toAcc = sc.nextInt() - 1;
            if (toAcc < 0 || toAcc >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");

            }

        } while (toAcc < 0 || toAcc >= theUser.numAccounts());

        accBalance = theUser.getAccBalance(toAcc);

        // amount to transfer
        do {
            System.out.printf("Enter the deposit amount (max Rs%.02f): Rs", accBalance);
            amount = sc.nextDouble();

            if (amount < 0) {
                System.out.println("Amount must be greater than zero");
            }
        } while (amount < 0);

        sc.nextLine();

        // get the memo 
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();

        // deposit
        theUser.addAccountTransaction(toAcc, amount, memo);
    }
}
