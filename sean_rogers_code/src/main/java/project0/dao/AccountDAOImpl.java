package project0.dao;

import project0.models.Account;
import project0.utilities.DAOUtilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for querying the database for Account objects
 */
public class AccountDAOImpl {

    Connection connection = null;
    PreparedStatement pstmt = null;

    /**
     * Gets all the accounts from table accounts
     * @return
     */
    public List<Account> getAccounts() {


        List<Account> accounts = new ArrayList<>();


        try{
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM \"Project0\".accounts a2;";
            pstmt = connection.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Account account = new Account();

                account.setId(rs.getInt("id"));
                account.setBalance(rs.getDouble("balance"));

                accounts.add(account);

            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return accounts;
    }

    /**
     * Retrieves the account specified by an id input
     * @param id
     * @return
     */
    public Account getAccountById(int id) {
        Account account = null;

        try {
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM \"Project0\".accounts a2 where id = ?;";
            pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                account = new Account();
                account.setId(rs.getInt("id"));
                account.setBalance(rs.getDouble("balance"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return account;
    }

    /**
     * add new account to database and return the id of the new account
     * @return
     */
    public int addAccount() {

        try{
            connection = DAOUtilities.getConnection();
            String sql = "INSERT INTO \"Project0\".accounts (\"balance\") values (?) returning id;";
            pstmt = connection.prepareStatement(sql);

            pstmt.setDouble(1, 0.0);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                return rs.getInt("id");
            }

            /*if(pstmt.executeUpdate() != 0) {
                return true;
            } else {
                return false;
            }*/

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            closeResources();
        }
        return 0;
    }

    /**
     * updates the account balance at the specified id
     * @param account
     * @return
     */
    public boolean updateAccount(Account account) {

        try {
            connection = DAOUtilities.getConnection();
            String sql = "UPDATE \"Project0\".accounts SET \"balance\" = ? where id = ?;";
            pstmt = connection.prepareStatement(sql);

            pstmt.setDouble(1, account.getBalance());
            pstmt.setInt(2, account.getId());

            if(pstmt.executeUpdate() != 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }

    }


    /**
     * close connection and preparedStatement
     */
    private void closeResources() {
        try {
            if (pstmt != null)
                pstmt.close();
        } catch (SQLException e) {
            System.err.println("Could not close statement!");
            e.printStackTrace();
        }

        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            System.err.println("Could not close connection!");
            e.printStackTrace();
        }
    }
}
