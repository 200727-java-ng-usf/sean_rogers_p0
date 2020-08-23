package project0.dao;

import org.junit.Test;
import project0.utilities.AppState;
import project0.utilities.DAOUtilities;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class DriverTest {

    public static AppState app = new AppState();

    public static DAOUtilities util = new DAOUtilities();

    @Test
    public void loginWithdraw5from1() {
        String s = "1\nseanrog\nseanrogpassword\n2\n5\n1";
        InputStream inContent = new ByteArrayInputStream(s.getBytes());
        System.setIn(inContent);
        app.getRouter().navigate("/home");

    }

}
