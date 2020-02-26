package Model.Statement;

import Model.Collection.Dictionary.MyIDictionary;
import Model.Exceptions.MyException;
import Model.Expresions.Expression;
import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import javafx.util.Pair;

import java.util.ArrayList;

public class NewBarrier implements IStatement{
    private String var;
    private Expression expression;

    public NewBarrier(String var, Expression expression) {
        this.var = var;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "newBarrier("+ var +", "+expression+")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value value=expression.evaluate(state.getSymbolTable(),state.getHeapTable());

        if(value.getType().equals(new IntType()))
        {
            IntValue Nr = (IntValue) value;
            ///updating
            state.getBarrierTable().put(state.getBarrierTable().getAddress()+1,new Pair<Integer, ArrayList<Integer>>(Nr.getValue(), new ArrayList<Integer>()));

            Value value1=state.getSymbolTable().get(var); ///exists

            if(value1.getType().equals(new IntType()))
                state.getSymbolTable().put(var,new IntValue(state.getBarrierTable().getAddress()));

            else throw new MyException("NewBarrierStatement: type of variable is wrong");
        }
        else throw new MyException("NewBarrierStatement: type of the expression is wrong");
        return null;
    }

    public IStatement deepCopy()
    {
        return new NewBarrier(var,expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.get(var).equals(new IntType()) && expression.typecheck(typeEnv).equals(new IntType()))
            return typeEnv;
        else throw new MyException("NewBarrier Exception: wrong types");
    }




}
