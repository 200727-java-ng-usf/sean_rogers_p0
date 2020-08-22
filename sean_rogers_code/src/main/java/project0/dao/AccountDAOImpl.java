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
            String sql = "Select * from accounts";
            pstmt = connection.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Account account = new Account();

                account.setId(rs.getInt("id"));
                account.setBalance(rs.getDouble("balance"));

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
     * close connection and preparedStatement
     */
    private void closeResources() {
        try {
            if (pstmt != null)
                pstmt.close();
        } catch (SQLException e) {
            System.out.println("Could not close statement!");
            e.printStackTrace();
        }

        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            System.out.println("Could not close connection!");
            e.printStackTrace();
        }
    }
}
