package project0.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import project0.dao.AccountDAOImpl;
import project0.dao.TransactionDAOImpl;
import project0.dao.UserDAOImpl;
import project0.dao.User_AccountDAOImpl;
import project0.driver.ProjectDriver;
import project0.exceptions.*;
import project0.models.Account;
import project0.models.AppUser;
import project0.models.Transaction;

import java.util.ArrayList;
import java.util.List;

import static project0.driver.ProjectDriver.app;

/**
 * test class that tests the methods in UserService
 */
public class UserServiceTest {

    private UserDAOImpl mockUserDAO = Mockito.mock(UserDAOImpl.class);
    private AccountDAOImpl mockAccountDAO = Mockito.mock(AccountDAOImpl.class);
    private User_AccountDAOImpl mockUser_accountDAO = Mockito.mock(User_AccountDAOImpl.class);
    private TransactionDAOImpl mockTransactionDAO = Mockito.mock(TransactionDAOImpl.class);

    UserService sut;

    @Before
    public void setup() {
        sut = new UserService(mockUserDAO, mockAccountDAO, mockUser_accountDAO, mockTransactionDAO);
    }

    @After
    public void tearDown() {
        sut = null;
        mockTransactionDAO = null;
        mockUser_accountDAO = null;
        mockAccountDAO = null;
        mockUserDAO = null;
    }

    @Test(expected = UsernameOrPasswordIncorrectException.class)
    public void authenticateWithUsernameNotFound() {

        //Arrange
        Mockito.when(mockUserDAO.getUserByUsername("Steve0"))
                .thenReturn(null);

        //Act
        sut.authenticate("Steve0", "IamVeryFunny!");

    }

    @Test(expected = UsernameOrPasswordIncorrectException.class)
    public void authenticateWithUsernameFoundButPasswordWrong() {

        //Arrange
        Mockito.when(mockUserDAO.getUserByUsername("Steve0"))
                .thenReturn(new AppUser("Steve0", "IamNotFunny:(", 1));

        //Act
        sut.authenticate("Steve0", "IamVeryFunny!");

    }

    @Test
    public void authenticateWithValidCredentials() {

        //Arrange
        Mockito.when(mockUserDAO.getUserByUsername("Steve0"))
                .thenReturn(new AppUser("Steve0", "IamVeryFunny!", 1));

        //Act
        sut.authenticate("Steve0", "IamVeryFunny!");
        assert (ProjectDriver.app.getCurrentUser().getId() == 1);

    }

    @Test(expected = EmptyUsernameOrPasswordException.class)
    public void userNameOrPasswordIsEmptyString() {
        sut.register(new AppUser("","",1));
    }

    @Test(expected = UsernameAlreadyTakenException.class)
    public void userNameAlreadyInDb() {
        Mockito.when(mockUserDAO.getUserByUsername(Mockito.anyString())).thenReturn(new AppUser());
        sut.register(new AppUser("Mockito","rules!",1));
    }

    @Test
    public void validRegistrationTest() {

        AppUser appUser = new AppUser();
        appUser.setId(1);
        appUser.setUsername("ThisIs");
        appUser.setPassword("Valid:)");

        Mockito.when(mockUserDAO.getUserByUsername(Mockito.anyString())).thenReturn(null);
        Mockito.when(mockUserDAO.addUser(appUser)).thenReturn(1);
        sut.register(new AppUser("ThisIs", "Valid:)", 1));
        app.setCurrentUser(appUser);
        assert (ProjectDriver.app.getCurrentUser().getId() == 1);
    }

    @Test(expected = AccountNotFoundException.class)
    public void depositFundsAccountNotFound() {

        AppUser appUser = new AppUser();
        app.setCurrentUser(appUser);
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 2.2));
        accounts.add(new Account(2, 45.3));
        accounts.add(new Account(3, 49.09));
        Mockito.when(mockUser_accountDAO.getAccountsBelongingToUser(appUser)).thenReturn(accounts);
        sut.depositFunds(5, 4);


    }

    @Test
    public void deposit5ToAccount3() {
        AppUser appUser = new AppUser();
        appUser.setId(1);
        appUser.setUsername("testUser");
        appUser.setPassword("testUserPassword");
        ProjectDriver.app.setCurrentUser(appUser);
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 2.2));
        accounts.add(new Account(2, 45.3));
        accounts.add(new Account(3, 49.09));

        Mockito.when(mockUser_accountDAO.getAccountsBelongingToUser(appUser)).thenReturn(accounts);
        Mockito.when(mockAccountDAO.getAccountById(3)).thenReturn(accounts.get(2));

        assert(sut.depositFunds(5, 3));
    }

    @Test(expected = AccountNotFoundException.class)
    public void withdraw5NoAccountFound() {
        AppUser appUser = new AppUser();
        app.setCurrentUser(appUser);
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 2.2));
        accounts.add(new Account(2, 45.3));
        accounts.add(new Account(3, 49.09));
        Mockito.when(mockUser_accountDAO.getAccountsBelongingToUser(appUser)).thenReturn(accounts);
        Mockito.when(mockAccountDAO.getAccountById(3)).thenReturn(accounts.get(2));
        assert(sut.withdrawFunds(5, 4));
    }

    @Test
    public void withdraw5NoFromAccount3() {
        AppUser appUser = new AppUser();
        appUser.setId(1);
        appUser.setUsername("testUser");
        appUser.setPassword("testUserPassword");
        ProjectDriver.app.setCurrentUser(appUser);
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 2.2));
        accounts.add(new Account(2, 45.3));
        accounts.add(new Account(3, 49.09));
        Mockito.when(mockUser_accountDAO.getAccountsBelongingToUser(appUser)).thenReturn(accounts);
        Mockito.when(mockAccountDAO.getAccountById(3)).thenReturn(accounts.get(2));
        assert(sut.withdrawFunds(5, 3));
    }

    @Test(expected = InsufficientFundsException.class)
    public void withdraw50NoFromAccount3InsufficientFunds() {
        AppUser appUser = new AppUser();
        appUser.setId(1);
        appUser.setUsername("testUser");
        appUser.setPassword("testUserPassword");
        ProjectDriver.app.setCurrentUser(appUser);
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 2.2));
        accounts.add(new Account(2, 45.3));
        accounts.add(new Account(3, 49.09));
        Mockito.when(mockUser_accountDAO.getAccountsBelongingToUser(appUser)).thenReturn(accounts);
        Mockito.when(mockAccountDAO.getAccountById(3)).thenReturn(accounts.get(2));
        assert(sut.withdrawFunds(50, 3));
    }

    @Test
    public void withdraw5NoFromAccount3InsufficientFunds() {
        AppUser appUser = new AppUser();
        appUser.setId(1);
        appUser.setUsername("testUser");
        appUser.setPassword("testUserPassword");
        ProjectDriver.app.setCurrentUser(appUser);
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 2.2));
        accounts.add(new Account(2, 45.3));
        accounts.add(new Account(3, 49.09));
        Mockito.when(mockUser_accountDAO.getAccountsBelongingToUser(appUser)).thenReturn(accounts);
        Mockito.when(mockAccountDAO.getAccountById(3)).thenReturn(accounts.get(2));
        assert(sut.withdrawFunds(5, 3));
    }

    @Test
    public void getBalanceForUser1() {
        AppUser appUser = new AppUser();
        appUser.setId(1);
        appUser.setUsername("testUser");
        appUser.setPassword("testUserPassword");
        ProjectDriver.app.setCurrentUser(appUser);
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 2.2));
        accounts.add(new Account(2, 45.3));
        accounts.add(new Account(3, 49.09));
        Mockito.when(mockUser_accountDAO.getAccountsBelongingToUser(appUser)).thenReturn(accounts);
        String expectedOutput = "Account number 1: $2.20\nAccount number 2: $45.30\nAccount number 3: $49.09\n";
        assert(sut.getBalances().equals(expectedOutput));
    }

    @Test(expected = AccountNotFoundException.class)
    public void getTransactionsForAccount4NoAccountFound() {
        AppUser appUser = new AppUser();
        app.setCurrentUser(appUser);
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 2.2));
        accounts.add(new Account(2, 45.3));
        accounts.add(new Account(3, 49.09));
        Mockito.when(mockUser_accountDAO.getAccountsBelongingToUser(appUser)).thenReturn(accounts);
        Mockito.when(mockAccountDAO.getAccountById(3)).thenReturn(accounts.get(2));
        assert(sut.withdrawFunds(5, 4));
    }

    @Test
    public void getTransactionsForAccount3() {
        AppUser appUser = new AppUser();
        appUser.setId(1);
        appUser.setUsername("testUser");
        appUser.setPassword("testUserPassword");
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 2.2));
        accounts.add(new Account(2, 45.3));
        accounts.add(new Account(3, 49.09));
        List<Transaction> t = new ArrayList<>();
        app.setCurrentUser(appUser);
        t.add(new Transaction(25.6, 1, 3));
        t.add(new Transaction(-43.2, 1, 3));
        t.add(new Transaction(15.52, 1, 3));
        Mockito.when(mockTransactionDAO
                .getTransactionsBelongingToUser(app.getCurrentUser(), accounts.get(2)))
                .thenReturn(t);

        Mockito.when(mockUser_accountDAO.getAccountsBelongingToUser(appUser)).thenReturn(accounts);
        Mockito.when(mockAccountDAO.getAccountById(3)).thenReturn(accounts.get(2));

        String expectedOutput = "Transaction: $25.60\nTransaction: -$43.20\nTransaction: $15.52\n";
        assert(sut.getTransactionsForAccount(3).equals(expectedOutput));
    }


}
