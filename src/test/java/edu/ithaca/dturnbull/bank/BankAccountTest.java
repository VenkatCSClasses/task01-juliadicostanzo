package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest(){
        //tests are very comprehensive for this method
        assertFalse(BankAccount.isEmailValid(""));         // empty string //boundary case
        assertFalse(BankAccount.isEmailValid(null));         // null //boundary case
        assertTrue(BankAccount.isEmailValid( "user@gmail.com"));   // valid email address //equivalence class
        assertTrue(BankAccount.isEmailValid( "user-a@gmail.com"));   // valid email address //equivalence class
        assertFalse(BankAccount.isEmailValid("2user@gmail.com")); // Valid email address (starting with number) //equivalence class
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

}