package Model.Expresions;

import Model.Exceptions.MyException;
import Model.Collection.Dictionary.MyIDictionary;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class LogicExpression implements Expression {
    private Expression expression1;
    private Expression expression2;
    private int operation;

    public LogicExpression(Expression expression1, Expression expression2, int operation) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }
    public Expression deepCopy()
    {
        return new LogicExpression(expression1.deepCopy(), expression2.deepCopy(),operation);
    }

    public String toString(){
        return expression1.toString()+" "+String.valueOf(operation)+" "+expression2.toString();
    }
    public Expression getExpression1()
    {
        return expression1;
    }
    public Expression getExpression2()
    {
        return expression2;
    }
    public int getOperation(){
        return operation;
    }
    public Value evaluate(MyIDictionary<String, Value> table, MyIDictionary<Integer, Value> heap) throws MyException {
        Value v1,v2;
        v1= expression1.evaluate(table,heap);
        if(v1.getType().equals(new BoolType())){
            v2 = expression2.evaluate(table,heap);
            if(v2.getType().equals(new BoolType())){
                BoolValue i1 = (BoolValue) v1;
                BoolValue i2 = (BoolValue) v2;
                boolean n1,n2;
                n1= i1.getValue();
                n2=i2.getValue();
                if(operation=='|')
                    return new BoolValue(n1||n2);
                else if(operation=='&')
                    return new BoolValue(n1&&n2);
                else throw new MyException("wrong operator");
            }
            else throw new MyException("second operator is not a boolean");
        }
        else throw new MyException("first operand is not a boolean");
    }

    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        Type typ1, typ2;
        typ1=expression1.typecheck(typeEnv);
        typ2=expression2.typecheck(typeEnv);
        if (typ1.equals(new BoolType())) {
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            } else
                throw new MyException("second operand is not a bool");
        }else
            throw new MyException("first operand is not a bool");
    }
}
