package project0.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project0.models.AppUser;
import project0.utilities.DAOUtilities;

import java.util.List;

public class UserDAOImplTest {

    DAOUtilities daou;
    UserDAOImpl udi;

    @Before
    public void setup() {
        daou = new DAOUtilities();
        udi = new UserDAOImpl();
    }

    @After
    public void teardown() {
        daou = null;
        udi = null;
    }

    @Test
    public void ensureUserDAOImplGetUsersWorks() {

        List<AppUser> appUsers = udi.getUsers();

        for(int i = 0; i < appUsers.size(); i++) {
            System.out.println(appUsers.get(i));
        }

        System.out.println("end test\n");
    }

    @Test
    public void ensureUserDAOImplGetUserWorks() {
        AppUser appUser = udi.getUserByUsername("seanrog1");

        System.out.println(appUser);
        System.out.println("end test\n");
    }

    @Test
    public void ensureUserDAOImplGetUserWorksWithNoUserFound() {
        AppUser appUser = udi.getUserByUsername("fakeseanrog");

        System.out.println(appUser);
        System.out.println("end test\n");
    }

    @Test
    public void addUserToDatabase() {

        AppUser appUser = new AppUser();
        appUser.setUsername("testuser2");
        appUser.setPassword("testuser2password");
        System.out.println(udi.addUser(appUser));

        //System.out.println(account);
        System.out.println("end test\n");
    }

}
