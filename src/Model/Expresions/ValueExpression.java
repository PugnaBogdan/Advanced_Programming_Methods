package Model.Expresions;

import Model.Exceptions.MyException;
import Model.Collection.Dictionary.MyIDictionary;
import Model.Type.Type;
import Model.Value.Value;

public class ValueExpression implements Expression {
    private Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }
    public Expression deepCopy()
    {
        return new ValueExpression(value.deepCopy());
    }
    public String toString(){
        return value.toString();
    }
    public Value getValue(){
        return value;
    }
    public Value evaluate(MyIDictionary<String, Value> table, MyIDictionary<Integer, Value> heap) throws MyException {
        return value;
    }
    public Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        return value.getType();
    }
}
