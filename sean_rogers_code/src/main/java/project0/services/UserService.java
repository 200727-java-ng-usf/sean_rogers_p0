package project0.services;

import project0.dao.UserDAOImpl;
import project0.exceptions.UsernameOrPasswordIncorrectException;
import project0.models.AppUser;

import static project0.driver.ProjectDriver.app;


/**
 * This class contains the business logic of the user and database interaction
 */
public class UserService {
    private UserDAOImpl userDAO;

    public UserService(UserDAOImpl dao){
        userDAO = dao;
    }

    /**
     * Authenticate will be called when the user tries to log in
     * @param username
     * @param password
     */
    public void authenticate(String username, String password) {

        AppUser appUser = userDAO.getUserByUsername(username);

        if(appUser == null) {
            throw new UsernameOrPasswordIncorrectException();
        } else {
            if(appUser.getPassword() != password) {
                throw new UsernameOrPasswordIncorrectException();
            }
        }
    }

    public void register(AppUser newUser) {
        if (newUser == null || newUser.getUsername().equals("") || newUser.getPassword().equals("")) {
            throw new RuntimeException("New User is null or username/password is empty");
        }

        AppUser userInDb = userDAO.getUserByUsername(newUser.getUsername());
        if(userInDb != null) {
            throw new RuntimeException("Username already taken");
        }

        userDAO.addUser(newUser);
        app.setCurrentUser(newUser);
    }

}
