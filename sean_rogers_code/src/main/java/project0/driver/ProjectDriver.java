package project0.driver;

import project0.dao.AccountDAOImpl;
import project0.models.Account;
import project0.utilities.AppState;
import project0.utilities.DAOUtilities;

import java.util.ArrayList;
import java.util.List;

public class ProjectDriver {

    public static AppState app = new AppState();

    public static DAOUtilities util = new DAOUtilities();

    public static void main(String[] args) {

        while(app.isAppRunning()) {
            app.getRouter().navigate("/home");
        }

    }
}
