package project0.dao;

import project0.models.Account;
import project0.models.AppUser;
import project0.models.Transaction;
import project0.utilities.DAOUtilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAOImpl {


    Connection connection = null;
    PreparedStatement pstmt = null;

    /**
     * Gets all the accounts belonging to the user passed in
     * @return
     */
    public List<Transaction> getTransactionsBelongingToUser(AppUser appUser, Account account) {


        List<Transaction> transactions = new ArrayList<>();


        try{
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM \"Project0\".transactions t " +
                    "where userid = ? and accountid = ?;";
            pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, appUser.getId());
            pstmt.setInt(2, account.getId());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction();

                transaction.setAmount(rs.getDouble("amount"));
                transaction.setUserId(rs.getInt("userid"));
                transaction.setAccountId(rs.getInt("accountid"));

                transactions.add(transaction);
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return transactions;
    }

    /**
     * add a transaction to the database
     * @return
     */
    public boolean addTransaction(Transaction transaction) {

        try{
            connection = DAOUtilities.getConnection();
            String sql = "INSERT INTO \"Project0\".transactions (\"amount\", \"userid\", \"accountid\") values (?, ?, ?);";
            pstmt = connection.prepareStatement(sql);

            pstmt.setDouble(1, transaction.getAmount());
            pstmt.setInt(2, transaction.getUserId());
            pstmt.setInt(3, transaction.getAccountId());

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
