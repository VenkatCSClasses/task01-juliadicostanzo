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
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address
        assertTrue(BankAccount.isEmailValid( "a@b.org"));   // valid email address
        assertTrue(BankAccount.isEmailValid( "a-b@c.com"));   // valid email address
        assertTrue(BankAccount.isEmailValid("2a@gmail.com")); // valid email address (starting with number)
        assertFalse(BankAccount.isEmailValid(""));         // empty string
        assertFalse(BankAccount.isEmailValid("a@.com"));   // missing second-level domain name
        assertFalse(BankAccount.isEmailValid("a@b"));      // missing top-level domain name
        assertFalse(BankAccount.isEmailValid("@b.com"));   // missing local part (username)
        assertFalse(BankAccount.isEmailValid("ab.com"));   // missing @ symbol
        assertFalse(BankAccount.isEmailValid("a@b..com")); // double dot in domain name
        assertFalse(BankAccount.isEmailValid("a@@b.com")); // double @ symbol
        assertFalse(BankAccount.isEmailValid("-@b.com"));  // local part starts with invalid character
        assertFalse(BankAccount.isEmailValid("a@-gmail.com")); // domain name starts with invalid character
        assertFalse(BankAccount.isEmailValid("a@gmail#.com")); // invalid character in domain name
        assertFalse(BankAccount.isEmailValid(".a@gmail.com")); // local part starts with invalid character
        assertFalse(BankAccount.isEmailValid("a2@#gmail.com")); // invalid character in domain name
        



        
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