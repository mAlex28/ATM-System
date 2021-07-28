package atm;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;
    private ArrayList<User> users; // list of customers
    private ArrayList<Account> accounts; // list of accounts
    
    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    // uuid for user
    public String getNewUserUUID() {
        String uuid;
        Random rndmno = new Random();
        int len = 6;
        boolean nonUnique;

        // loop till uuid is generated
        do {

            uuid = "";
            nonUnique = false;

            // generate uuid
            for (int i = 0; i < len; i++) {
                uuid += ((Integer) rndmno.nextInt(10)).toString();
            }

            for (User u : this.users) {
                if (uuid.compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);

        return uuid;
    }

    // uuid for account
    public String getNewAccountUUID() {
        String uuid;
        Random rndmno = new Random();
        int len = 10;
        boolean nonUnique;

        // loop till uuid is generated
        do {

            uuid = "";
            nonUnique = false;

            // generate uuid
            for (int i = 0; i < len; i++) {
                uuid += ((Integer) rndmno.nextInt(10)).toString();
            }

            for (Account a : this.accounts) {
                if (uuid.compareTo(a.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);

        return uuid;
    }
    
    // add an account
    public void addAccount(Account acc) {
        this.accounts.add(acc);
    }
    
    public User addUser(String firstName, String lastName, String pin) {
        
        // create user and add to the list
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);
        
        // create savings account
        Account newAccount = new Account("Savings", newUser, this);
        
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);
        
        return newUser;
    }
    
    public User userLogin(String userid, String pin) {
        
        // search through the list of users
        for(User u: this.users) {
            //check if the userid is correct
            if (u.getUUID().compareTo(userid) == 0 && u.validatePin(pin) ) {
                return u;  
            }
        }
        return null;
    }
    
    public String getName() {
        return this.name;
    }
}
