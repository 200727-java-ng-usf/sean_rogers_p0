package project0.screens;

public class HomeScreen extends Screen{

    public HomeScreen() {
        super("HomeScreen", "/home");
    }

    @Override
    public void render() {
        System.out.println("Welcome to Sean's Project0!\n");
        System.out.println("1) Login");
        System.out.println("2) Register");
        System.out.println("3) Exit Application");

        try{

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}
