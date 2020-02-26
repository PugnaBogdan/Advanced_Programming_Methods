package Model.Statement;

import Model.Collection.Dictionary.MyIDictionary;
import Model.Exceptions.MyException;
import Model.Expresions.ValueExpression;
import Model.ProgramState;
import Model.Type.Type;
import Model.Value.IntValue;

public class SleepStatement implements IStatement {
    private Integer number;

    public SleepStatement(Integer num){
        this.number = num;
    }

    public String toString(){ return "sleep(" +number.toString()+")";}

    public ProgramState execute(ProgramState state) throws MyException {
        if (number>0)
        {
            state.getStack().push(new SleepStatement(number-1));
        }
        return null;
    }
    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnvironment) throws MyException {
        return typeEnvironment;
    }

    public IStatement deepCopy()
    {
        return new SleepStatement(number);
    }

}
