package project0.screens;

import project0.exceptions.EmptyUsernameOrPasswordException;
import project0.exceptions.UsernameAlreadyTakenException;
import project0.models.AppUser;
import project0.services.UserService;

import static project0.driver.ProjectDriver.app;

/**
 * this screen provides the interface for the user to register a new login
 */
public class RegisterScreen extends Screen {

    private UserService userService;

    public RegisterScreen(UserService userService) {
        super("RegisterScreen", "/register");
        this.userService = userService;
    }

    @Override
    public void render() {

        String username;
        String password;

        try {
            System.out.println("Register New User:");
            System.out.println("Enter username: ");
            username = app.getConsole().readLine();
            System.out.println("Enter password: ");
            password = app.getConsole().readLine();

            AppUser newUser = new AppUser();
            newUser.setUsername(username);
            newUser.setPassword(password);

            userService.register(newUser);

            if(app.isSessionValid()) {
                app.getRouter().navigate("/dashboard");
            }

        } catch(EmptyUsernameOrPasswordException e) {
            System.out.println("You must type something in for both the username and password");
        } catch(UsernameAlreadyTakenException e) {
            System.out.println("That username has already been taken");
        }

        catch(Exception e) {
            e.printStackTrace();
        }

    }

}
