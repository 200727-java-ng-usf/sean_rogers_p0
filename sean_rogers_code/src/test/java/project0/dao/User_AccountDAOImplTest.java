package project0.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project0.models.Account;
import project0.models.AppUser;
import project0.utilities.DAOUtilities;

import java.util.List;

public class User_AccountDAOImplTest {

    DAOUtilities daou;
    User_AccountDAOImpl uadi;

    @Before
    public void setup() {
        daou = new DAOUtilities();
        uadi = new User_AccountDAOImpl();
    }

    @After
    public void teardown() {
        daou = null;
        uadi = null;
    }

    @Test
    public void ensureGetAccountsForUser1Works() {
        AppUser appUser = new AppUser();
        appUser.setId(1);
        appUser.setUsername("seanrog");
        appUser.setPassword("seanrogpassword");

        List<Account> accountList = uadi.getAccountsBelongingToUser(appUser);

        for(int i = 0; i < accountList.size(); i++) {
            System.out.println(accountList.get(i));
        }

        System.out.println("end test\n");
    }

    @Test
    public void ensureGetUsersForAcount6Works() {
        Account account = new Account();
        account.setId(6);
        account.setBalance(697.83);

        List<AppUser> appUsers = uadi.getUsersBelongingToAccount(account);

        for(int i = 0; i < appUsers.size(); i++) {
            System.out.println(appUsers.get(i));
        }

        System.out.println("end test\n");
    }

    @Test
    public void ensureAddUser_AccountWorks() {
        Account account = new Account();
        account.setId(9);
        account.setBalance(235.11);

        AppUser appUser = new AppUser();
        appUser.setId(7);
        appUser.setUsername("seanrog1");
        appUser.setPassword("seanrogpassword1");

        System.out.println(uadi.addUser_Account(appUser, account));
    }

}
