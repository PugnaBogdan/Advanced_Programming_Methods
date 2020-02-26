package View;
import Controller.Controller;
import Model.Exceptions.MyException;

import java.io.IOException;

public class RunExample extends Command {
    private Controller ctrl;
    public RunExample(String key, String desc,Controller ctr){
        super(key, desc);
        this.ctrl=ctr;
    }
    @Override
    public void execute() {
        try{
            ctrl.allStep(); }
        catch (MyException | IOException exception){
            System.out.print(exception.getMessage());
        }
    }
}