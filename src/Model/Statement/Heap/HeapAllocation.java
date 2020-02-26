package Model.Statement.Heap;

import Model.Exceptions.MyException;
import Model.Statement.IStatement;
import Model.Expresions.Expression;
import Model.ProgramState;
import Model.Collection.Dictionary.MyIDictionary;
import Model.Type.*;
import Model.Value.*;

public class HeapAllocation implements IStatement {
    private String var_name;
    private Expression expression;
    //private AddressBuilder b = new AddressBuilder();

    public HeapAllocation(String var_name, Expression expression){
        this.expression = expression;
        this.var_name = var_name;
    }

    @Override
    public String toString(){
        return "HeapAloc( " + var_name + ", " + expression.toString() + " )";
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        Type typevar = typeEnv.get(var_name);
        Type typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new ReferenceType(typexp)))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

          MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
          MyIHeap<Integer, Value> heapTable = state.getHeapTable();

        if(!symbolTable.isDefined(var_name))
         throw new MyException("Variable name is not a variable in symbolTable\n");

        Value referenced= symbolTable.get(var_name);
        if(!(referenced instanceof ReferenceValue))
            throw new MyException("not refValue\n");

        Value value = expression.evaluate(state.getSymbolTable(),state.getHeapTable());
        if(!referenced.getType().equals( new ReferenceType(value.getType())))
        {
            throw new MyException("Inner types dont match\n");
        }

        Integer heapAddress = heapTable.getNewAddress();
        heapTable.put(heapAddress, value);
        symbolTable.put(var_name,new ReferenceValue(heapAddress, value.getType()));



        return null;


    }

    public IStatement deepCopy()
    {
        return new HeapAllocation(new String(var_name),expression);
    }


}
