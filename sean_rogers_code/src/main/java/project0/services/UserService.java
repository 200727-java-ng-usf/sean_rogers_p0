package project0.services;

import project0.dao.AccountDAOImpl;
import project0.dao.UserDAOImpl;
import project0.dao.User_AccountDAOImpl;
import project0.exceptions.UsernameOrPasswordIncorrectException;
import project0.models.Account;
import project0.models.AppUser;
import project0.models.User_Account;

import java.util.List;

import static project0.driver.ProjectDriver.app;


/**
 * This class contains the business logic of the user and database interaction
 */
public class UserService {
    private UserDAOImpl userDAO;
    private AccountDAOImpl accountDAO;
    private User_AccountDAOImpl user_accountDAO;

    public UserService(UserDAOImpl udao){
        userDAO = udao;
        accountDAO = new AccountDAOImpl();
        user_accountDAO = new User_AccountDAOImpl();
    }

    public UserService(UserDAOImpl udao, AccountDAOImpl adao, User_AccountDAOImpl uadao) {
        this(udao);
        accountDAO = adao;
        user_accountDAO = uadao;
    }

    /**
     * Authenticate will be called when the user tries to log in
     * @param username
     * @param password
     */
    public void authenticate(String username, String password) {

        AppUser appUser = userDAO.getUserByUsername(username);

        if(appUser == null) {
            throw new UsernameOrPasswordIncorrectException();
        } else {
            if(!appUser.getPassword().equals(password)) {
                throw new UsernameOrPasswordIncorrectException();
            }
        }

        app.setCurrentUser(appUser);
    }

    public void register(AppUser newUser) {
        if (newUser == null || newUser.getUsername().equals("") || newUser.getPassword().equals("")) {
            throw new RuntimeException("New User is null or username/password is empty");
        }

        AppUser userInDb = userDAO.getUserByUsername(newUser.getUsername());
        if(userInDb != null) {
            throw new RuntimeException("Username already taken");
        }

        userDAO.addUser(newUser);
        app.setCurrentUser(newUser);
    }

    public void depositFunds(double amount, int accountId) {

        /*user_accountDAO = new User_AccountDAOImpl();
        accountDAO = new AccountDAOImpl();*/
        List<Account> accounts = user_accountDAO.getAccountsBelongingToUser(app.getCurrentUser());

        boolean accountFound = false;
        for(Account account : accounts) {
            if(account.getId() == accountId) {
                accountFound = true;
            }
        }

        if(!accountFound) {
            throw new RuntimeException("current user does not have account specified");
        } else {
            Account account = accountDAO.getAccountById(accountId);
            account.setBalance(account.getBalance() + amount);
            accountDAO.updateAccount(account);
        }

    }

    public void withdrawFunds(double amount, int accountId) {

        List<Account> accounts = user_accountDAO.getAccountsBelongingToUser(app.getCurrentUser());

        boolean accountFound = false;
        for(Account account : accounts) {
            if(account.getId() == accountId) {
                accountFound = true;
            }
        }

        // user isn't associated with the account specified by user
        if(!accountFound) {
            throw new RuntimeException("current user does not have account specified");
        }

        // user has account specified
        Account account = accountDAO.getAccountById(accountId);

        // ensure the balance is high enough for the withdrawal amount
        if(account.getBalance() - amount < 0) {
            throw new RuntimeException("Insufficient funds for withdrawal");
        }

        account.setBalance(account.getBalance() - amount);
        accountDAO.updateAccount(account);

    }

}
