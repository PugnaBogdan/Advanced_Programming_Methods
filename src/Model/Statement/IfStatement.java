package Model.Statement;

import Model.Collection.Dictionary.MyIDictionary;
import Model.Collection.Stack.MyIStack;
import Model.Expresions.Expression;
import Model.ProgramState;
import Model.Exceptions.MyException;
import Model.Type.Type;
import Model.Value.Value;
import Model.Type.BoolType;
import Model.Value.BoolValue;

public class IfStatement implements IStatement {
    private Expression expression;
    private IStatement thenStatement;
    private IStatement elseStatement;
    public IfStatement(Expression e, IStatement t, IStatement el)
    {
        expression=e;
        thenStatement=t;
        elseStatement=el;
    }

    public String toString(){
        return "IF("+ expression.toString()+") THEN(" +thenStatement.toString()
                +")ELSE("+elseStatement.toString()+")";}

    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack=state.getStack();
        MyIDictionary<String, Value> symbolTable= state.getSymbolTable();
        if(expression.evaluate(symbolTable,state.getHeapTable()).getType().equals(new BoolType()))
        {
            BoolValue v1=(BoolValue) expression.evaluate(symbolTable,state.getHeapTable());
            if(v1.getValue())
                stack.push(thenStatement);
            else stack.push(elseStatement);
        }
        else throw new MyException("The type of the expression is not bool!\n");
        return null;

    }
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws
            MyException{
        Type typexp=expression.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenStatement.typecheck(typeEnv);
            elseStatement.typecheck(typeEnv);
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");
    }

    public IStatement deepCopy()
    {
        return new IfStatement(expression,thenStatement.deepCopy(),elseStatement.deepCopy());
    }
}
