package Model.Statement;

import Model.Collection.Dictionary.MyIDictionary;
import Model.Collection.Stack.MyIStack;
import Model.ProgramState;
import Model.Exceptions.MyException;
import Model.Type.Type;

public class CompoundStatement implements IStatement {
    private IStatement first;
    private IStatement second;

    public CompoundStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    public String toString() {
        return "("+first.toString() + " ; " + second.toString()+")";
    }
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack=state.getStack();
        stack.push(second);
        stack.push(first);
        return null;
    }
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws
            MyException{
        //MyIDictionary<String,Type> typEnv1 = first.typecheck(typeEnv);
        //MyIDictionary<String,Type> typEnv2 = snd.typecheck(typEnv1);
        //return typEnv2;
        return second.typecheck(first.typecheck(typeEnv));
    }
    public IStatement deepCopy()
    {
        return new CompoundStatement(first,second);
    }
}
