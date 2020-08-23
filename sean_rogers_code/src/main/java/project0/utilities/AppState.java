package project0.utilities;

import project0.dao.UserDAOImpl;
import project0.models.AppUser;
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
    }

}
