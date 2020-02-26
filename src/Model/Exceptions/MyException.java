package Model.Exceptions;

public class MyException extends Exception {
    private String message;

    public MyException(String m){
        message=m;
    }
    public String getMessage(){
        return message;
    }
}
