package project0.dao;

import project0.models.Account;
import project0.models.AppUser;
import project0.models.User_Account;
import project0.utilities.DAOUtilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This model represents the association table relating users to accounts
 */
public class User_AccountDAOImpl {

    Connection connection = null;
    PreparedStatement pstmt = null;

    /**
     * Gets all the accounts belonging to the user passed in
     * @return
     */
    public List<Account> getAccountsBelongingToUser(AppUser appUser) {


        List<Account> accounts = new ArrayList<>();


        try{
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM \"Project0\".accounts a " +
                    "join \"Project0\".users_accounts ua " +
                    "on a.id = ua.accountid " +
                    "where userid = ? " +
                    "order by ua.accountid;";
            pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, appUser.getId());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Account account = new Account();

                account.setId(rs.getInt("accountid"));
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
     * Get all users associated with the account passed in
     * @param account
     * @return
     */
    public List<AppUser> getUsersBelongingToAccount(Account account) {


        List<AppUser> appUsers = new ArrayList<>();


        try{
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM \"Project0\".users u " +
                    "join \"Project0\".users_accounts ua " +
                    "on u.id = ua.userid " +
                    "where accountid = ?;";
            pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, account.getId());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                AppUser appUser = new AppUser();

                appUser.setId(rs.getInt("userid"));
                appUser.setUsername(rs.getString("username"));
                appUser.setPassword(rs.getString("password"));

                appUsers.add(appUser);
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return appUsers;
    }



    /**
     * add new user/account relationship
     * @return
     */
    public boolean addUser_Account(AppUser user, Account account) {

        try{
            connection = DAOUtilities.getConnection();
            String sql = "INSERT INTO \"Project0\".users_accounts (\"userid\", \"accountid\") values (?, ?);";
            pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, user.getId());
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
