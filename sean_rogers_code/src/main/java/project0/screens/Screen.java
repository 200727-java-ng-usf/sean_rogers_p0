package project0.screens;

public abstract class Screen {

    private String name;
    private String route;

    protected Screen(String name, String route) {
        this.name = name;
        this.route = route;
    }

    public String getName() {
        return name;
    }

    public String getRoute() {
        return route;
    }

    /**
     * Displays a particular menu depending on the screen implementation
     */
    public abstract void render();

}
