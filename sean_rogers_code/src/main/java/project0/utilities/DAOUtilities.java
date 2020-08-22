package project0.utilities;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOUtilities {

    private static DAOUtilities daoUtilities = new DAOUtilities();

    private static Properties props = new Properties();

    private DAOUtilities() {
        super();

        try{
            props.load(new FileReader("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DAOUtilities getInstance() {
        return daoUtilities;
    }

    public static synchronized Connection getConnection() throws SQLException {
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(props.getProperty("url"),
                                                props.getProperty("username"),
                                                props.getProperty("password"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println("Could not register driver!");
                e.printStackTrace();
            }
        }

        //If connection was closed then retrieve a new connection
        if (connection.isClosed()){
            System.out.println("Opening new connection...");
            connection = DriverManager.getConnection(props.getProperty("url"),
                                                    props.getProperty("username"),
                                                    props.getProperty("password"));
        }
        System.out.println("Connection obtained");
        return connection;
    }
}
