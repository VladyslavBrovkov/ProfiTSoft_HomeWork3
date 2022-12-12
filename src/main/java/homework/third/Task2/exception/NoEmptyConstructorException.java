package homework.third.Task2.exception;

public class NoEmptyConstructorException extends RuntimeException{
    public NoEmptyConstructorException(String message) {
        super(message);
    }
}
