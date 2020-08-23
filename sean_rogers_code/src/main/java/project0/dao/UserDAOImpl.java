package project0.dao;

import project0.models.Account;
import project0.models.AppUser;
import project0.utilities.DAOUtilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl {


    Connection connection = null;
    PreparedStatement pstmt = null;

    /**
     * Gets all the users from the users table
     * @return
     */
    public List<AppUser> getUsers() {


        List<AppUser> appUsers = new ArrayList<>();


        try{
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM \"Project0\".users;";
            pstmt = connection.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                AppUser appUser = new AppUser();

                appUser.setId(rs.getInt("id"));
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
     * Retrieves the user specified by an id input
     * @param id
     * @return
     */
    public AppUser getUserById(int id) {
        AppUser appUser = null;

        try {
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM \"Project0\".users where id = ?;";
            pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                appUser = new AppUser();
                appUser.setId(rs.getInt("id"));
                appUser.setUsername(rs.getString("username"));
                appUser.setPassword(rs.getString("password"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return appUser;
    }

    /**
     * add a new user to database
     * @return
     */
    public boolean addUser(AppUser appUser) {

        try{
            connection = DAOUtilities.getConnection();
            String sql = "INSERT INTO \"Project0\".users (\"username\", \"password\") values (?, ?)";
            pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, appUser.getUsername());
            pstmt.setString(2, appUser.getPassword());

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
