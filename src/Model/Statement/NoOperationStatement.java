package Model.Statement;

import Model.Collection.Dictionary.MyIDictionary;
import Model.ProgramState;
import Model.Exceptions.MyException;
import Model.Type.Type;

public class NoOperationStatement implements IStatement {
    public NoOperationStatement() {
    }
    public String toString(){
        return  "";
    }
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        return typeEnv;
    }
    public IStatement deepCopy()
    {
        return new NoOperationStatement();
    }
}
