package atm;

import java.util.ArrayList;

public class Account {

    private String accName;
//    private double balance;
    private String uuid;
    private User holder;
    private ArrayList<Transaction> transactions; // list of transactions

    public Account(String accName, User holder, Bank bank) {
        this.accName = accName;
        this.holder = holder;

        // get a uuid for the account
        this.uuid = bank.getNewAccountUUID();

        // init trasactions
        this.transactions = new ArrayList<Transaction>();
    }

    public String getUUID() {
        return this.uuid;
    }

    public String getSummaryLine() {
        // get the balance
        double balance = this.getBalance();

        // format the summary if the account is overdrawn
        if (balance >= 0) {
            return String.format("%s : Rs%.02f %s", this.uuid, balance, this.accName);
        } else {
            return String.format("%s : Rs(%.02f) %s", this.uuid, balance, this.accName);

        }
    }
    
    public double getBalance() {
        double balance = 0;
        for (Transaction t : this.transactions) {
            balance += t.getAmount();
        }
        return balance;
    }
    
    // print trans history
    public void printTransHistory() {
        System.out.printf("\nTransaction history for account %s ", this.uuid);
        for (int i = this.transactions.size()-1; i >= 0; i--) {
            System.out.println(this.transactions.get(i).getSummaryLine());
        }
        System.out.println();     
    }
    
    public void addTransaction(double amount, String memo) {
        // create new transaction object and add it to our list
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);
    }
    
}
