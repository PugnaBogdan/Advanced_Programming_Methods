package Model.Statement;

import Model.Collection.Dictionary.MyIDictionary;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import javafx.util.Pair;

import java.util.ArrayList;

public class Await implements IStatement {
    private String var;

    public Await(String var) {
        this.var = var;
    }

    @Override
    public String toString() {
        return "await(" + var + ')';
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value value=state.getSymbolTable().get(var);///check if var in symtable
        if(value.getType().equals(new IntType()))///check its type
        {
            IntValue index=(IntValue) value;
            if(state.getBarrierTable().get(index.getValue())!=null)///if its in the barrier table
            {
                Pair<Integer, ArrayList<Integer>> foundIndex=state.getBarrierTable().get(index.getValue());///retrieve the foun entry
                Integer n1=foundIndex.getKey();
                ArrayList<Integer> list1=foundIndex.getValue();
                int nl=list1.size();
                if(n1>nl)///check sizes
                {
                    int ok=0;
                    for (Integer k: list1)
                    {
                        if(k==state.getID())///if the curentstate in the list
                            ok=1;
                    }
                    if(ok==1)
                        state.getStack().push(new Await(var));
                    else
                    {
                        foundIndex.getValue().add(state.getID());///add the new state
                        state.getStack().push(new Await(var));
                    }
                }
            }
            else throw new MyException("Await Exception : index not in barrierTable");
        }
        else throw new MyException("Await Exception: var is wrong");
        return null;
    }

    public IStatement deepCopy()
    {
        return new Await(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type=typeEnv.get(var);
        if(type.equals(new IntType()))
            return typeEnv;
        else throw new MyException("AwaitStatement: wrong type");
    }

}
