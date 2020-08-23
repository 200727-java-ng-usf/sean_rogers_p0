package project0.screens;

import project0.services.UserService;

import static project0.driver.ProjectDriver.app;

public class LoginScreen extends Screen {

    private UserService userService;

    public LoginScreen(UserService userService) {
        super("LoginScreen", "/login");

        this.userService = userService;
    }

    @Override
    public void render() {

        String username;
        String password;

        try {

            System.out.println("Login in to Project0");
            System.out.print("Username: ");
            username = app.getConsole().readLine();
            System.out.print("Password: ");
            password = app.getConsole().readLine();

            userService.authenticate(username, password);

            if(app.isSessionValid()) {
                app.getRouter().navigate("/dashboard");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}
