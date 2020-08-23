package project0.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project0.models.Account;
import project0.models.AppUser;
import project0.models.Transaction;
import project0.utilities.DAOUtilities;

import java.util.List;

public class TransactionDAOImplTest {
    DAOUtilities daou;
    TransactionDAOImpl tdi;

    @Before
    public void setup() {
        daou = new DAOUtilities();
        tdi = new TransactionDAOImpl();
    }

    @After
    public void teardown() {
        daou = null;
        tdi = null;
    }

    @Test
    public void ensureGetTransactionsForUser1Account1Works() {
        AppUser appUser = new AppUser();
        appUser.setId(1);
        appUser.setUsername("seanrog");
        appUser.setPassword("seanrogpassword");

        Account account = new Account();
        account.setId(1);


        List<Transaction> transactionsList = tdi.getTransactionsBelongingToUser(appUser, account);

        for(int i = 0; i < transactionsList.size(); i++) {
            System.out.println(transactionsList.get(i));
        }

        System.out.println("end test\n");
    }

    @Test
    public void ensureGetTransactionForUser6Account8Works() {
        AppUser appUser = new AppUser();
        appUser.setId(6);
        appUser.setUsername("testuser3");
        appUser.setPassword("testuser3password");

        Account account = new Account();
        account.setId(8);


        List<Transaction> transactionsList = tdi.getTransactionsBelongingToUser(appUser, account);

        for(int i = 0; i < transactionsList.size(); i++) {
            System.out.println(transactionsList.get(i));
        }

        System.out.println("end test\n");
    }

    @Test
    public void addTransactionWithAmount25point06UserId4AccountId6() {

        Transaction transaction = new Transaction();
        transaction.setAmount(25.06);
        transaction.setUserId(4);
        transaction.setAccountId(6);
        System.out.println(tdi.addTransaction(transaction));

        //System.out.println(account);
        System.out.println("end test\n");
    }

}
