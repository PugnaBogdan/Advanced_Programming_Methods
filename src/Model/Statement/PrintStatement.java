package Model.Statement;

import Model.Collection.Dictionary.MyIDictionary;
import Model.Collection.List.MyIList;
import Model.Expresions.Expression;
import Model.ProgramState;
import Model.Exceptions.MyException;
import Model.Type.Type;
import Model.Value.Value;

public class PrintStatement implements IStatement {
    private Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    public String toString(){ return "print(" +expression.toString()+")";}
    public ProgramState execute(ProgramState state) throws MyException {
        MyIList<Value> output=state.getOutput();
        MyIDictionary<String, Value> symbolTable=state.getSymbolTable();

        output.add(expression.evaluate(symbolTable,state.getHeapTable()));
        return null;
    }
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws
            MyException{
        expression.typecheck(typeEnv);
        return typeEnv;
    }

    public IStatement deepCopy()
    {
        return new PrintStatement(expression);
    }
}
