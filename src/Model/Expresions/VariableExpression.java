package Model.Expresions;

import Model.Exceptions.MyException;
import Model.Collection.Dictionary.MyIDictionary;
import Model.Type.Type;
import Model.Value.Value;

public class VariableExpression implements Expression {
    private String id;

    public VariableExpression(String id) {
        this.id = id;
    }

    public Expression deepCopy()
    {
        return new VariableExpression(new String(id));
    }

    public String toString(){
        return id;
    }
    public String getId()
    {
        return id;
    }
    public Value evaluate(MyIDictionary<String, Value> symbolTable, MyIDictionary<Integer, Value> heap) throws MyException {
        return symbolTable.get(id);
    }
    public Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException {
        return typeEnv.get(id);
    }
}
