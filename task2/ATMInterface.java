package task2;

import java.util.*;

class User {
    private String userID;
    private String pin;
    private List<Transaction> transactionHistory;
    private double balance;

    public User(String userID, String pin) {
        this.userID = userID;
        this.pin = pin;
        this.transactionHistory = new ArrayList<>();
        this.balance = 0.0; 
    }

    public String getUserID() {
        return userID;
    }

    public String getPin() {
        return pin;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public double getBalance() {
        return balance; 
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
        if ("Deposit".equals(transaction.getType())) {
            balance += transaction.getAmount(); 
        } else if ("Withdraw".equals(transaction.getType())) {
        }
    }
}

class Transaction {
    private String type;
    private double amount;
    private Date date;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.date = new Date();
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }
}

class ATM {
    private User currentUser;

    public void authenticateUser(String userID, String pin) {
        currentUser = new User(userID, pin);
        System.out.println("Authentication successful.");
    }

    public void deposit(double amount) {
        if (currentUser != null) {
            currentUser.addTransaction(new Transaction("Deposit", amount));
            System.out.println("$" + amount + " deposited successfully.");
        } else {
            System.out.println("Please log in first.");
        }
    }

    public void withdraw(double amount) {
        if (currentUser != null) {
            if (amount <= currentUser.getBalance()) { 
                currentUser.addTransaction(new Transaction("Withdraw", amount));
                System.out.println("$" + amount + " withdrawn successfully!!!");
            } else {
                System.out.println("Insufficient balance!!!!!.You cannot withdraw more than your current balance.");
            }
        } else {
            System.out.println("Please log in first.");
        }
    }

    public void transfer(String recipientID, double amount) {
        if (currentUser != null) {
            if (amount <= currentUser.getBalance()) { 
                currentUser.addTransaction(new Transaction("Transfer to " + recipientID, amount));
                System.out.println("$" + amount + " transferred to " + recipientID + " successfully.");
            } else {
                System.out.println("Insufficient balance!!!!. You cannot transfer more than your current balance.");
            }
        } else {
            System.out.println("Please log in first.");
        }
    }

    public void displayTransactionHistory() {
        if (currentUser != null) {
            List<Transaction> transactions = currentUser.getTransactionHistory();
            if (transactions.isEmpty()) {
                System.out.println("No transactions available.");
            } else {
                System.out.println("Transaction History:");
                for (Transaction transaction : transactions) {
                    System.out.println(transaction.getType() + " - $" + transaction.getAmount() + " - " + transaction.getDate());
                }
            }
        } else {
            System.out.println("Please log in first.");
        }
    }

    public void displayBalance() {
        if (currentUser != null) {
            System.out.println("Current balance: $" + currentUser.getBalance());
        } else {
            System.out.println("Please log in first.");
        }
    }

    public void quit() {
        System.out.println("Quitting the ATM interface.THANK YOU");
        System.exit(0);
    }
}

public class ATMInterface {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ATM atm = new ATM();

        System.out.println("Welcome to the ATM interface!!!");

        while (true) {
        	System.out.println("---------------------------------------------");
            System.out.println("1. Authenticate user");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Display transaction history");
            System.out.println("6. Display balance");
            System.out.println("7. Quit");
            System.out.println("---------------------------------------------");
            System.out.print("Enter your choice: ");

            try {
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        System.out.print("Enter userID: ");
                        String userID = sc.next();
                        System.out.print("Enter PIN: ");
                        String pin = sc.next();
                        atm.authenticateUser(userID, pin);
                        break;
                    case 2:
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = sc.nextDouble();
                        atm.deposit(depositAmount);
                        break;
                    case 3:
                        System.out.print("Enter withdraw amount: ");
                        double withdrawAmount = sc.nextDouble();
                        atm.withdraw(withdrawAmount);
                        break;
                    case 4:
                        System.out.print("Enter recipient userID: ");
                        String recipientID = sc.next();
                        System.out.print("Enter transfer amount: ");
                        double transferAmount = sc.nextDouble();
                        atm.transfer(recipientID, transferAmount);
                        break;
                    case 5:
                        atm.displayTransactionHistory();
                        break;
                    case 6:
                        atm.displayBalance();
                        break;
                    case 7:
                        atm.quit();
                        break;
                    default:
                        System.out.println("Invalid choice!!!. Please enter a number from 1 to 7.");
                }
            } catch (InputMismatchException ime) {
                System.out.println("Invalid input!!!! Please enter a number.");
                sc.nextLine(); 
            }
        }
    }
}
