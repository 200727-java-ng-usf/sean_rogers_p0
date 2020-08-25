package project0.exceptions;

public class EmptyUsernameOrPasswordException extends RuntimeException {
    public EmptyUsernameOrPasswordException() {
        super("Empty Username or Password.");
    }
}
