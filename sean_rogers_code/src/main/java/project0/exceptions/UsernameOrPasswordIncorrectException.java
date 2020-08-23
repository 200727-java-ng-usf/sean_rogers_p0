package project0.exceptions;

public class UsernameOrPasswordIncorrectException extends RuntimeException{

    public UsernameOrPasswordIncorrectException() {
        super("Username or password incorrect");
    }

}
