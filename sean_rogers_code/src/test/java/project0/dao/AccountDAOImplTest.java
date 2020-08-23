package project0.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project0.models.Account;
import project0.utilities.DAOUtilities;

import java.util.List;

public class AccountDAOImplTest {

    DAOUtilities daou;
    AccountDAOImpl adi;

    @Before
    public void setup() {
        daou = new DAOUtilities();
        adi = new AccountDAOImpl();
    }

    @After
    public void teardown() {
        daou = null;
        adi = null;
    }

    @Test
    public void ensureAccountDAOImplGetAccountsWorks() {

        List<Account> accountList = adi.getAccounts();

        for(int i = 0; i < accountList.size(); i++) {
            System.out.println(accountList.get(i));
        }

        System.out.println("end test\n");
    }

    @Test
    public void ensureAccountDAOImplGetAccountWorks() {
        Account account = adi.getAccountById(1);

        System.out.println(account);
        System.out.println("end test\n");
    }

    @Test
    public void ensureAccountDAOImplGetAccountWorksWithNoAccountFound() {
        Account account = adi.getAccountById(999);

        System.out.println(account);
        System.out.println("end test\n");
    }

    @Test
    public void addAccountToDatabase() {

        System.out.println(adi.addAccount());

        //System.out.println(account);
        System.out.println("end test\n");
    }

    @Test
    public void updateAccountTest() {
        Account account = new Account(3, 257.59);
        adi.updateAccount(account);
    }
}
