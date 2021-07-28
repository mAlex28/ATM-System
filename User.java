package atm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class User {
    
    private String firstName;
    private String lastName;
    private String uuid;
    private byte pinHash[]; //md5 hash of user's pin
    private ArrayList<Account> accounts;
    
    public User(String firstName, String lastName, String pin, Bank bank) {
        this.firstName = firstName;
        this.lastName = lastName;
        
        try {
            // hash the pin using md5
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
            
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Invalid Algorithm");
            ex.printStackTrace();
            System.exit(1);
        }
        
        // get a uuid for the user
        this.uuid = bank.getNewUserUUID();
        
        // create accounts
        this.accounts = new ArrayList<Account>();
        System.out.printf("New user %s, %s with ID %s created \n", lastName, firstName, this.uuid);  
    }
    
    // add an account for the user
    public void addAccount(Account acc) {
        this.accounts.add(acc);
    }
    
    public String getUUID() {
        return this.uuid;
    }
    
// check if the given pin matches the user pin
    public boolean validatePin(String pin) {
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);
            
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Invalid Algorithm");
            ex.printStackTrace();
            System.exit(1);
            
        }
        
        return false;
    }
    
    public String getFirstName() {
        return this.firstName;
    }
    
    public void printAccountSummary() {
        System.out.printf("\n\n%s's account summary\n", this.firstName);
        for (int a = 0; a < this.accounts.size(); a++) {
            System.out.printf("  %d) %s\n", a+1, this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }
    
    public int numAccounts() {
        return this.accounts.size();
    }
    
    public void printAcctTransHistroy(int accIndx) {
        this.accounts.get(accIndx).printTransHistory();
    }
    
    public double getAccBalance(int accIndx) {
        return this.accounts.get(accIndx).getBalance();
    }

    public String getAccountUUID(int accIndx) {
        return this.accounts.get(accIndx).getUUID();
    }
    
    public void addAccountTransaction(int accIndx, double amount, String memo) {
        this.accounts.get(accIndx).addTransaction(amount, memo);
    }

}
