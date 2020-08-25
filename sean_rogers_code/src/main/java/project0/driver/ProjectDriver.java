package project0.driver;

import project0.dao.AccountDAOImpl;
import project0.models.Account;
import project0.utilities.AppState;
import project0.utilities.DAOUtilities;

import java.util.ArrayList;
import java.util.List;

/**
 * This class runs the application
 */
public class ProjectDriver {

    public static AppState app = new AppState();

    public static DAOUtilities util = new DAOUtilities();

    public static void main(String[] args) {

        System.out.println("Welcome to the SeanRog BanksR us of America!\n");
        while(app.isAppRunning()) {
            app.getRouter().navigate("/home");
        }

    }
}
