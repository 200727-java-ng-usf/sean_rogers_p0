package project0.screens;

import project0.services.UserService;

import java.util.InputMismatchException;

import static project0.driver.ProjectDriver.app;

/**
 * When a user is logged in, the dashboard screen is displayed with a handful of options
 */
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
            System.out.println("4) Create a new account");
            System.out.println("5) View transactions for an account");
            System.out.println("6) Sign out");

            try {
                System.out.println("> ");
                userInput = app.getConsole().readLine();
                int accountId;

                switch (userInput) {
                    case "1":
                        System.out.println("Enter amount to deposit");
                        userInput = app.getConsole().readLine();
                        System.out.println("Enter account id");
                        accountId = Integer.parseInt(app.getConsole().readLine());
                        userService.depositFunds(Double.parseDouble(userInput), accountId);
                        break;
                    case "2":
                        System.out.println("Enter amount to withdraw");
                        userInput = app.getConsole().readLine();
                        System.out.println("Enter account id");
                        accountId = Integer.parseInt(app.getConsole().readLine());
                        userService.withdrawFunds(Double.parseDouble(userInput), accountId);
                        break;
                    case "3":
                        System.out.println(userService.getBalances());
                        break;
                    case "4":
                        userService.createNewAccount(app.getCurrentUser());
                        break;
                    case "5":
                        System.out.println("Enter account id");
                        accountId = Integer.parseInt(app.getConsole().readLine());
                        System.out.println(userService.getTransactionsForAccount(accountId));
                        break;
                    case "6":
                        app.setCurrentUser(null);
                        break;
                    default:
                        System.out.println("Invalid selection. Please try again");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again");
            } catch (Exception e) {
                e.printStackTrace();
                app.setAppRunning(false);
            }

        }

    }

}
