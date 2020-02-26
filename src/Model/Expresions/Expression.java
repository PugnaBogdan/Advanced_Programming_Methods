package Model.Expresions;

import Model.Collection.Dictionary.MyIDictionary;
import Model.Exceptions.MyException;
import Model.Type.Type;
import Model.Value.Value;

public interface Expression {
    Value evaluate(MyIDictionary<String, Value> table, MyIDictionary<Integer, Value> heap) throws MyException;
    Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
    Expression deepCopy();
}
