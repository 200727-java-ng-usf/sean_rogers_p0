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
import project0.exceptions.UsernameOrPasswordIncorrectException;
import project0.models.AppUser;

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

    @Test(expected = RuntimeException.class)
    public void userNameOrPasswordIsEmptyString() {
        sut.register(new AppUser("","",1));
    }

    @Test(expected = RuntimeException.class)
    public void userNameAlreadyInDb() {
        Mockito.when(mockUserDAO.getUserByUsername(Mockito.anyString())).thenReturn(new AppUser());
        sut.register(new AppUser("Mockito","rules!",1));
    }

    @Test
    public void validRegistrationTest() {
        sut.register(new AppUser("ThisIs", "Valid:)", 1));
        assert (ProjectDriver.app.getCurrentUser().getId() == 1);
    }

}
