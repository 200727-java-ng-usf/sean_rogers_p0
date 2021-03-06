package project0.services;

import project0.dao.AccountDAOImpl;
import project0.dao.TransactionDAOImpl;
import project0.dao.UserDAOImpl;
import project0.dao.User_AccountDAOImpl;
import project0.exceptions.*;
import project0.models.Account;
import project0.models.AppUser;
import project0.models.Transaction;
import project0.models.User_Account;
import project0.utilities.MethodLibrary;

import java.util.List;

import static project0.driver.ProjectDriver.app;


/**
 * This class contains the business logic of the user and database interaction
 */
public class UserService {
    private UserDAOImpl userDAO;
    private AccountDAOImpl accountDAO;
    private User_AccountDAOImpl user_accountDAO;
    private TransactionDAOImpl transactionDAO;


    /**
     * initially, this was the only constructor
     * @param udao
     */
    public UserService(UserDAOImpl udao){
        userDAO = udao;
        accountDAO = new AccountDAOImpl();
        user_accountDAO = new User_AccountDAOImpl();
        transactionDAO = new TransactionDAOImpl();
    }

    /**
     * this constructor was created to pass mocked DAOs as arguments for unit testing the service layer
     *
     * this reassigns the other DAOs when called
     * @param udao
     * @param adao
     * @param uadao
     * @param tdao
     */
    public UserService(UserDAOImpl udao,
                       AccountDAOImpl adao,
                       User_AccountDAOImpl uadao,
                       TransactionDAOImpl tdao) {
        this(udao);
        accountDAO = adao;
        user_accountDAO = uadao;
        transactionDAO = tdao;
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

    /**
     * Provides the functionality for a new user registering for a new user login.
     * It throws an exception if the user provided an empty username or password.
     * It throws an exception if the user tries to register with a name already in the database
     * @param newUser
     */
    public void register(AppUser newUser) {
        if (newUser == null || newUser.getUsername().equals("") || newUser.getPassword().equals("")) {
            throw new EmptyUsernameOrPasswordException();
        }

        AppUser userInDb = userDAO.getUserByUsername(newUser.getUsername());

        //username already taken
        if(userInDb != null) {
            throw new UsernameAlreadyTakenException();
        }

        newUser.setId(userDAO.addUser(newUser));
        app.setCurrentUser(newUser);
    }

    /**
     * deposits a user defined amount to the account with the accountId provided by the user
     * @param amount
     * @param accountId
     */
    public boolean depositFunds(double amount, int accountId) {

        List<Account> accounts = user_accountDAO.getAccountsBelongingToUser(app.getCurrentUser());

        boolean accountFound = false;
        for(Account account : accounts) {
            if(account.getId() == accountId) {
                accountFound = true;
            }
        }

        if(!accountFound) {
            //user does not have specified account
            throw new AccountNotFoundException();
        } else {
            Account account = accountDAO.getAccountById(accountId);
            account.setBalance(account.getBalance() + amount);
            accountDAO.updateAccount(account);
            addTransaction(account, app.getCurrentUser(), amount);
        }
        return true;
    }

    /**
     * withdraws a user defined amount from the account specified by user
     *
     * throws an exception when the amount to withdraw is higher than the balance of the account
     * @param amount
     * @param accountId
     */
    public boolean withdrawFunds(double amount, int accountId) {

        List<Account> accounts = user_accountDAO.getAccountsBelongingToUser(app.getCurrentUser());

        boolean accountFound = false;
        for(Account account : accounts) {
            if(account.getId() == accountId) {
                accountFound = true;
            }
        }

        // user isn't associated with the account specified by user
        if(!accountFound) {
            throw new AccountNotFoundException();
        }

        // user has account specified
        Account account = accountDAO.getAccountById(accountId);

        // ensure the balance is high enough for the withdrawal amount
        if(account.getBalance() - amount < 0) {
            throw new InsufficientFundsException();
        }

        account.setBalance(account.getBalance() - amount);
        accountDAO.updateAccount(account);
        addTransaction(account, app.getCurrentUser(), -amount);
        return true;
    }

    /**
     * returns the balances of each account for the current user
     * @return
     */
    public String getBalances() {
        List<Account> accounts = user_accountDAO.getAccountsBelongingToUser(app.getCurrentUser());
        StringBuilder accountsStringBuilder = new StringBuilder();

        String accountBalanceCurrencyFormat;

        for(Account account : accounts) {
            //convert double to currency format
            accountBalanceCurrencyFormat = MethodLibrary.doubleToUSDFormat(account.getBalance());

            //append each account number and balance to the stringBuilder
            accountsStringBuilder.append("Account number " + account.getId() +
                                ": " + accountBalanceCurrencyFormat + "\n");
        }

        return accountsStringBuilder.toString();
    }


    public String getTransactionsForAccount(int accountId) {
        List<Account> accounts = user_accountDAO.getAccountsBelongingToUser(app.getCurrentUser());

        boolean accountFound = false;
        for(Account account : accounts) {
            if(account.getId() == accountId) {
                accountFound = true;

            }
        }

        // user isn't associated with the account specified by user
        if(!accountFound) {
            throw new AccountNotFoundException();
        }

        Account account = accountDAO.getAccountById(accountId);

        List<Transaction> transactions = transactionDAO.getTransactionsBelongingToUser(app.getCurrentUser(), account);

        StringBuilder transactionsStringBuilder = new StringBuilder();

        String transactionBalanceCurrencyFormat;

        for(Transaction transaction : transactions) {
            //convert double to currency format then assign to transactionBalanceCurrencyFormat
            transactionBalanceCurrencyFormat = MethodLibrary.doubleToUSDFormat(transaction.getAmount());

            //append each account number and balance to the stringBuilder
            transactionsStringBuilder.append("Transaction: " + transactionBalanceCurrencyFormat + "\n");
        }

        return transactionsStringBuilder.toString();
    }

    /**
     * creates a new money account
     * @param appUser
     */
    public void createNewAccount(AppUser appUser) {
        int accountId = accountDAO.addAccount();
        Account account = new Account(accountId, 0.0);
        user_accountDAO.addUser_Account(appUser, account);
    }

    /**
     * Every time a user deposits to or withdraws from an account, that is recorded in the database.
     * @param account
     * @param amount
     */
    private void addTransaction(Account account, AppUser appUser, double amount) {
        Transaction transaction = new Transaction();
        transaction.setAccountId(account.getId());
        transaction.setUserId(appUser.getId());
        transaction.setAmount(amount);

        transactionDAO.addTransaction(transaction);
    }

}
