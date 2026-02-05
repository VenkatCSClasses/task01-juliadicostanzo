package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid or starting balance is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email) && isAmountValid(startingBalance)){
            this.email = email;
            this.balance = startingBalance;
        }
        else if (isAmountValid(startingBalance) == false){
            throw new IllegalArgumentException("Starting balance: " + startingBalance + " is invalid, cannot create account");
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * if the amount is negative or larger than the balance, throws an InsufficientFundsException
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (isAmountValid(amount) == false || amount <= 0){
            throw new IllegalArgumentException("Cannot withdraw amount less than zero or amount with more than two decimal places");
        }
        else if (amount <= balance){
            this.balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }

    /**
     * @return true if email is valid, false otherwise
     */
    public static boolean isEmailValid(String email){
        if(email == null || email.length() == 0){
            return false;
        }

        String pattern = "^[A-Za-z0-9][A-Za-z0-9-]*@[A-Za-z]+\\.[A-Za-z]+$";
        return email.matches(pattern);
    }

    /**
     * @return true if amount is valid (positive, two decimal points or less), false otherwise
     */
    public static boolean isAmountValid(double amount){
        return amount >= 0 && ((amount * 100) % 1 == 0); 
    }

    /**
     * @post increases the balance by amount if amount is valid
     * if amount is invalid, throws IllegalArgumentException
     */
    public void deposit(double amount){
        if(isAmountValid(amount) && amount > 0){
            this.balance += amount;
        }else{
            throw new IllegalArgumentException("Deposit amount is invalid");
        }
    }

    /**
     * @post transfer amount from one account to another if amount is valid and from an account that has sufficient funds
     * if amount is invalid, throws IllegalArgumentException
     * if from account has insufficient funds, throws InsufficientFundsException
     * if from and to accounts are the same, throws IllegalArgumentException
     */
    public void transfer(BankAccount to, double amount) throws InsufficientFundsException {
        if(!isAmountValid(amount) || amount <= 0){
            throw new IllegalArgumentException("Transfer amount is invalid, must be positive and have two decimal points or less");
        }
        if(to == null){
            throw new IllegalArgumentException("must have a valid account to transfer to");
        }
        if(this == to){
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }
        if (this.getBalance() < amount){
            throw new InsufficientFundsException("Not enough money to transfer");
        }
        this.withdraw(amount);
        to.deposit(amount);
    }
}