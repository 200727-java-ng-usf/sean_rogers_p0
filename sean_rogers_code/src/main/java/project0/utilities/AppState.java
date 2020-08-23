package project0.utilities;

import project0.dao.UserDAOImpl;
import project0.models.AppUser;
import project0.screens.HomeScreen;
import project0.services.UserService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AppState {

    private BufferedReader console;
    private AppUser currentUser;
    private ScreenRouter router;
    private boolean appRunning;

    public AppState() {

        appRunning = true;
        console = new BufferedReader(new InputStreamReader(System.in));

        final UserDAOImpl userDAOImpl = new UserDAOImpl();
        final UserService userService = new UserService(userDAOImpl);

        router = new ScreenRouter();
        router.addScreen(new HomeScreen());
    }

    public BufferedReader getConsole() {
        return console;
    }

    public void setConsole(BufferedReader console) {
        this.console = console;
    }

    public AppUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(AppUser currentUser) {
        this.currentUser = currentUser;
    }

    public ScreenRouter getRouter() {
        return router;
    }

    public void setRouter(ScreenRouter router) {
        this.router = router;
    }

    public boolean isAppRunning() {
        return appRunning;
    }

    public void setAppRunning(boolean appRunning) {
        this.appRunning = appRunning;
    }
}
