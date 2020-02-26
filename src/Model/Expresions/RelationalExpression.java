package Model.Expresions;

import Model.Exceptions.MyException;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;
import Model.Collection.Dictionary.MyIDictionary;

public class RelationalExpression implements Expression {
    private Expression expression1;
    private Expression expression2;
    private String operation;

    public RelationalExpression(String operation, Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    public String toString(){
        return expression1.toString()+" "+operation+" "+expression2.toString();
    }

    public Expression deepCopy()
    {
        return new RelationalExpression(operation ,expression1.deepCopy(), expression2.deepCopy());
    }

    public Expression getExpression1(){
        return expression1;
    }
    public Expression getExpression2(){
        return expression2;
    }
    public Value evaluate(MyIDictionary<String, Value> table, MyIDictionary<Integer, Value> heap) throws MyException {
        Value v1,v2;
        v1= expression1.evaluate(table,heap);
        if(v1.getType().equals(new IntType())){
            v2 = expression2.evaluate(table,heap);
            if(v2.getType().equals(new IntType())){
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1,n2;
                n1= i1.getValue();
                n2= i2.getValue();
                if(operation.equals("<"))
                    return new BoolValue(n1<n2);
                else if(operation.equals(">"))
                    return new BoolValue(n1>n2);
                else if(operation.equals("=="))
                    return new BoolValue(n1==n2);
                else if(operation.equals("!=")) {
                    return new BoolValue(n1!=n2);
                }
                else if(operation.equals("<=")) {
                    return new BoolValue(n1<=n2);
                }
                else if(operation.equals(">=")) {
                    return new BoolValue(n1>=n2);
                }
                else throw new MyException("wrong operator");
            }
            else throw new MyException("second operator is not an integer");
        }
        else throw new MyException("first operand is not an integer");
    }
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        Type typ1, typ2;
        typ1=expression1.typecheck(typeEnv);
        typ2=expression2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new BoolType();
            } else
                throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");
    }
}
