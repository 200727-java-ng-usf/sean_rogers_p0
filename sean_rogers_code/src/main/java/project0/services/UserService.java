package project0.services;

import project0.dao.UserDAOImpl;
import project0.exceptions.UsernameOrPasswordIncorrectException;
import project0.models.AppUser;



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

}
