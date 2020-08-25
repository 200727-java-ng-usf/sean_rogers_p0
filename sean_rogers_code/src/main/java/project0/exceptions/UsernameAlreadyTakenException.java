package project0.exceptions;

public class UsernameAlreadyTakenException extends RuntimeException {
    public UsernameAlreadyTakenException() {
        super("Username already taken");
    }
}
