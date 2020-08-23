package project0.screens;

import project0.models.AppUser;
import project0.services.UserService;

import static project0.driver.ProjectDriver.app;

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

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}
