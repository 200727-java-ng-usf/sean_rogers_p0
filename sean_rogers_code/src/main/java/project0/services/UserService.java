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

    public UserService(UserDAOImpl dao){
        userDAO = dao;
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
            if(appUser.getPassword() != password) {
                throw new UsernameOrPasswordIncorrectException();
            }
        }
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

}
