package project0.screens;

import static project0.driver.ProjectDriver.app;

/**
 * This is the screen that is displayed when the application is started or a user has just logged out
 */
public class HomeScreen extends Screen{

    public HomeScreen() {
        super("HomeScreen", "/home");
    }

    @Override
    public void render() {
        System.out.println("1) Login");
        System.out.println("2) Register");
        System.out.println("3) Exit Application");

        boolean runLoop = true;

        while(runLoop){

            try{
                runLoop = false;
                System.out.println("> ");
                String userInput = app.getConsole().readLine();

                switch (userInput) {
                    case "1":
                        app.getRouter().navigate("/login");
                        break;
                    case "2":
                        app.getRouter().navigate("/register");
                        break;
                    case "3":
                        app.setAppRunning(false);
                        break;
                    default:
                        System.out.println("Invalid selection. Please try again");
                        runLoop = true;
                }

            } catch(Exception e) {
                e.printStackTrace();
            }

        } //end loop

    } //end render

}
