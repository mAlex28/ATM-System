package atm;

import java.util.Date;

public class Transaction {

    private double amount;
    private Date timestamp;
    private String memo;
    private Account inAccount; // link the account holder

    public Transaction(double amount, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";

    }

    public Transaction(double amount, String memeo, Account inAccount) {

        this(amount, inAccount);

        // set the memo 
        this.memo = memo;

    }

    public double getAmount() {
        return this.amount;
    }

    // get transaction summary
    public String getSummaryLine() {
        if (this.amount >= 0) {
            return String.format("%s : Rs%.02f %s", this.timestamp.toString(), this.amount, this.memo);
        } else {
            return String.format("%s : Rs(%.02f) %s", this.timestamp.toString(), -this.amount, this.memo);

        }
    }
}
