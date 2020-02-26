package Model.Statement.Heap;

import Model.Collection.Dictionary.MyIDictionary;
import Model.Exceptions.MyException;
import Model.Expresions.Expression;
import Model.Statement.IStatement;
import Model.ProgramState;
import Model.Type.ReferenceType;
import Model.Type.Type;
import Model.Value.Value;
import Model.Value.ReferenceValue;

public class HeapWriting implements IStatement {
    private Expression expression;
    private String variable_name;

    public HeapWriting(String variable_name, Expression expression) {
        this.expression = expression;
        this.variable_name = variable_name;
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        Type VariableType = typeEnv.get(variable_name);
        Type ExpressionType = expression.typecheck(typeEnv);
        if (VariableType.equals(new ReferenceType(ExpressionType)))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

//        MyIDictionary<String, Value> symbolTabel = state.getSymbolTable();
//        MyIHeap<Integer, Value> Heap = state.getHeapTable();
//
//        if(!symbolTabel.isDefined(variable_name)) {
//            throw new MyException("The variable is not defined\n");
//        }
//        if(!(symbolTabel.get(variable_name).getType().equals(new ReferenceType()))) {
//            throw new MyException("Not a reference Value\n");
//        }
//        ReferenceValue referenced_value=(ReferenceValue) symbolTabel.get(variable_name);
//        int address=referenced_value.getAddr();
//        if (Heap.isDefined(address)) {
//            if(expression.evaluate(symbolTabel, Heap).getType().equals(referenced_value.getLocationType())) {
//                Heap.put(address,expression.evaluate(symbolTabel, Heap));
//            }
//            else { throw new MyException("Not the same Type\n"); }
//        }
//        else { throw new MyException("The address is not in use\n"); }
//        return state;

        MyIDictionary<String, Value> symbolTabel = state.getSymbolTable();
        MyIDictionary<Integer,Value> Heap = state.getHeapTable();
        if(symbolTabel.isDefined(variable_name)) {
            if(symbolTabel.get(variable_name).getType() instanceof ReferenceType) {
                ReferenceValue referenced_value = (ReferenceValue) symbolTabel.get(variable_name);
                int address = referenced_value.getAddr();
                if (Heap.isDefined(address)) {
                    if (expression.evaluate(symbolTabel, Heap).getType().equals(Heap.get(address).getType())) {
                        Heap.put(address, expression.evaluate(symbolTabel, Heap));
                        return null; }
                    else { throw new MyException("Not the same Type"); } }
                else { throw new MyException("The address is not in use"); } }
            throw new MyException("Not a reference Value"); }
        else throw new MyException("The variable is not defined");


    }

    public IStatement deepCopy()
    {
        return new HeapWriting(new String(variable_name),expression);
    }


    public String toString() {
        return "HeapWrite (" +variable_name+","+expression.toString() + ')';
    }
}
