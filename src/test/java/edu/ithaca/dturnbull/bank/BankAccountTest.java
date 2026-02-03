package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200); //tests normal starting balance boundary case
        assertEquals(200, bankAccount.getBalance(), 0.001); //vaild equivalence class
        
        BankAccount bankAccount2 = new BankAccount("a@b.com", 0); //tests zero starting balance boundary case
        assertEquals(0, bankAccount2.getBalance(), 0.001);
        
        //BankAccount bankAccount3 = new BankAccount("a@b.com", -200); //tests negative starting balance boundary case
       // assertEquals(-200, bankAccount3.getBalance(), 0.001); An account can't have a negative starting balance, so this test is invalid.

        BankAccount bankAccount4 = new BankAccount("a@b.com", 1000000000); //tests large starting balance boundary case
        assertEquals(1000000000, bankAccount4.getBalance(), 0.001); //vaild equivalence class
        //getBalance has no invalid equivalence classes as it has no parameters
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.01); //vaild equivalence class checks the balance is correct after withdraw 
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300)); // invalid equivalence class checks exception is thrown when amount is larger than balance
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-300)); //invalid equivalence class checks exception is thrown when amount is negative 
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-300.001));
        bankAccount.withdraw(100);
        assertEquals(0, bankAccount.getBalance(), 0.01); //checks the balance can be reduced to zero boundary case

        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(100.0001)); //invalid equivalence class checks exception is thrown when amount is more than balance
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(100.001));
    }

    @Test
    void isEmailValidTest(){
        //tests are very comprehensive for this method
        assertFalse(BankAccount.isEmailValid(""));         // empty string //boundary case
        assertFalse(BankAccount.isEmailValid(null));         // null //boundary case
        assertTrue(BankAccount.isEmailValid( "user@gmail.com"));   // valid email address //equivalence class
        assertTrue(BankAccount.isEmailValid( "user-a@gmail.com"));   // valid email address //equivalence class
        assertTrue(BankAccount.isEmailValid("2user@gmail.com")); // Valid email address (starting with number) //equivalence class
        assertFalse(BankAccount.isEmailValid("-user@gmail.com"));  // local part starts with invalid character //equivalence class
        assertFalse(BankAccount.isEmailValid(".user@gmail.com")); // local part starts with invalid character //equivalence class
        assertFalse(BankAccount.isEmailValid("@gmail.com"));   // missing local part (username) //equivalence class
        assertFalse(BankAccount.isEmailValid("user@@gmail.com")); // double @ symbol //equivalence class
        assertFalse(BankAccount.isEmailValid("usergmail.com"));   // missing @ symbol //equivalence class
        assertFalse(BankAccount.isEmailValid("user@.com"));   // missing second-level domain name //equivalence class
        assertFalse(BankAccount.isEmailValid("user@gmail"));      // missing top-level domain name //equivalence class
        assertFalse(BankAccount.isEmailValid("user@-gmail.com")); // domain name starts with invalid character//equivalence class
        assertFalse(BankAccount.isEmailValid("user2@gm#ail.com")); // invalid character in domain name //equivalence class
        assertFalse(BankAccount.isEmailValid("user@gmail..com")); // double dot in domain name//equivalence class
        assertFalse(BankAccount.isEmailValid("user@gmail#.com")); // invalid character in domain name //equivalence class
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));

        assertThrows(IllegalArgumentException.class,()-> new BankAccount("user@gmail.com", -100));
        assertThrows(IllegalArgumentException.class,()-> new BankAccount("user@gmail.com", 100.001));
        assertThrows(IllegalArgumentException.class,()-> new BankAccount("user@gmail.com", 100.1231));
        assertThrows(IllegalArgumentException.class,()-> new BankAccount("user@gmail.com", -100.1231));
    }

    @Test 
    void isAmountValidTest(){
        assertTrue(BankAccount.isAmountValid(0)); //boundary case
        assertTrue(BankAccount.isAmountValid(100)); //valid equivalence class
        assertTrue(BankAccount.isAmountValid(100.50)); //valid equivalence class
        assertFalse(BankAccount.isAmountValid(-100)); //invalid equivalence class
        assertFalse(BankAccount.isAmountValid(-.01)); //invalid equivalence class
        assertFalse(BankAccount.isAmountValid(100.03401)); //invalid equivalence class
    }

    @Test
    void depositTest() {
        BankAccount bankAccount = new BankAccount("user@gmail.com",0);
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(0)); // boundary case 
        assertEquals(0, bankAccount.getBalance(), 0.001);
        bankAccount.deposit(100); //desposit to an account with zero balance
        assertEquals(100, bankAccount.getBalance(), 0.001); //valid equivalence class
        bankAccount.deposit(50.75); //deposit to an account with non-zero balance
        assertEquals(150.75, bankAccount.getBalance(), 0.001); //valid equivalence class
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-10)); //invalid equivalence class
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(20.5234)); //invalid equivalence class
    }

    @Test
    void transferTest() {
        BankAccount bankAccount1 = new BankAccount("user1@gmail.com", 0);
        BankAccount bankAccount2 = new BankAccount("user2@gmail.com",100);
        assertThrows(IllegalArgumentException.class, () -> BankAccount.transfer(bankAccount1, bankAccount2, 0)); //boundary case
        assertThrows(IllegalArgumentException.class, () -> BankAccount.transfer(bankAccount1, bankAccount2, 10)); //invalid equivalence class can't transfer from account with insufficient funds
        BankAccount.transfer(bankAccount2, bankAccount1, 50); //valid equivalence class
        assertEquals(bankAccount1.getBalance(),50, 0.001); 
        assertEquals(bankAccount2.getBalance(),50, 0.001); 
        
        BankAccount.transfer(bankAccount2, bankAccount1, 49.99); //valid equivalence class
        assertEquals(bankAccount1.getBalance(),99.99, 0.001); 
        assertEquals(bankAccount2.getBalance(),0.01, 0.001);
        assertThrows(InsufficientFundsException.class, () -> BankAccount.transfer(bankAccount2, bankAccount1, 49.99)); //invalid equivalence class
        assertThrows(IllegalArgumentException.class, () -> BankAccount.transfer(bankAccount1, bankAccount2, -10)); //invalid equivalence class
        assertThrows(IllegalArgumentException.class, () -> BankAccount.transfer(bankAccount1, bankAccount2, 10.28393)); //invalid equivalence class

    }
}