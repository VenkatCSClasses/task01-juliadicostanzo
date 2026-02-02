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

        assertEquals(100, bankAccount.getBalance(), 0.001); //vaild equivalence class checks the balance is correct after withdraw 
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300)); // invalid equivalence class checks exception is thrown when amount is larger than balance
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(-300)); //invalid equivalence class checks exception is thrown when amount is negative 
        bankAccount.withdraw(100);
        assertEquals(0, bankAccount.getBalance(), 0.001); //checks the balance can be reduced to zero boundary case
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
    }

    @Test 
    void isAmountValidTest(){
        assertTrue(BankAccount.isAmountValid(0)); //boundary case
        assertTrue(BankAccount.isAmountValid(100)); //valid equivalence class
        assertTrue(BankAccount.isAmountValid(100.50)); //valid equivalence class
        assertFalse(BankAccount.isAmountValid(-100)); //invalid equivalence class
        assertFalse(BankAccount.isAmountValid(-.01)); //invalid equivalence class
    }

}