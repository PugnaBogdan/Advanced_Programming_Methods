package Model.Expresions;

import Model.Type.ReferenceType;
import Model.Type.Type;
import Model.Value.Value;
import Model.Exceptions.MyException;
import Model.Collection.Dictionary.MyIDictionary;
import Model.Value.ReferenceValue;

public class HeapReading implements Expression {
    private Expression expression;

    public HeapReading(Expression expression) {
        this.expression = expression;
    }

    public Expression deepCopy()
    {
        return new HeapReading( expression.deepCopy() );
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable, MyIDictionary<Integer, Value> heapTable) throws MyException {

//        Value value=expression.evaluate(symbolTable,heapTable);
//        if(value.getType().equals(new ReferenceType()))
//        {
//            ReferenceValue refValue = (ReferenceValue) value;
//            Integer integer = refValue.getAddr();
//            if(heapTable.isDefined(integer)) return heapTable.get(integer);
//            else throw new MyException("The address is not a key in the heap table");
//        }
//        else throw new MyException("the expression is not of Reference Type");

        if(expression.evaluate(symbolTable,heapTable) instanceof ReferenceValue)
        {
            int address=((ReferenceValue) expression.evaluate(symbolTable,heapTable)).getAddr();
            Value heap_value= heapTable.get(address);
            return heap_value;
        }
        throw new MyException("Not a reference Value");
    }

    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        Type typ=expression.typecheck(typeEnv);
        if (typ instanceof ReferenceType) {
            ReferenceType reft =(ReferenceType) typ;
            return reft.getInner();
        } else
            throw new MyException("the rH argument is not a Ref Type");
    }



    public String toString() {
        return "HeapReading (" + expression.toString() + ')';
    }
}