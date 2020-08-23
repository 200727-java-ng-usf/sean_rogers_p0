package project0.screens;

import project0.services.UserService;

import static project0.driver.ProjectDriver.app;

public class DashboardScreen extends Screen {

    private UserService userService;

    public DashboardScreen(UserService userService) {
        super("DashboardScreen", "/dashboard");
        this.userService = userService;
    }

    @Override
    public void render() {

        String userInput;
        System.out.println("Welcome " + app.getCurrentUser().getUsername() + "! :D");

        while (app.isSessionValid()) {

            System.out.println("1) Deposit funds into an account");
            System.out.println("2) Withdraw funds from an account");
            System.out.println("3) View the balance of your account(s)");
            System.out.println("4) Sign out");

            try {
                System.out.println("> ");
                userInput = app.getConsole().readLine();
                int account;

                switch (userInput) {
                    case "1":
                        System.out.println("Enter amount to deposit");
                        userInput = app.getConsole().readLine();
                        System.out.println("Enter account id");
                        account = Integer.parseInt(app.getConsole().readLine());
                        userService.depositFunds(Double.parseDouble(userInput), account);
                        break;
                    case "2":
                        System.out.println("Enter amount to withdraw");
                        userInput = app.getConsole().readLine();
                        System.out.println("Enter account id");
                        account = Integer.parseInt(app.getConsole().readLine());
                        userService.withdrawFunds(Double.parseDouble(userInput), account);
                        break;
                    case "3":
                        System.out.println(userService.getBalances());
                        break;
                    case "4":
                        app.setCurrentUser(null);
                }

            } catch (Exception e) {
                e.printStackTrace();
                app.setAppRunning(false);
            }

        }

    }

}
