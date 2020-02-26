package Model.Statement.File;

import Model.Collection.Dictionary.MyIDictionary;

import Model.Collection.Stack.MyIStack;
import Model.Expresions.Expression;
import Model.ProgramState;
import Model.Statement.IStatement;
import Model.Exceptions.MyException;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.Value;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class openFile implements IStatement {

    private Expression expression;

    public openFile(Expression var) {
        this.expression = var;
    }

    public String toString() {
        return "openFile(" + expression.toString() + ")";
    }


    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack = state.getStack();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();


        Value value = expression.evaluate(state.getSymbolTable(),state.getHeapTable());
        if(!value.getType().equals(new StringType())){
            throw new MyException("Expression on OpenFile is not a StringType\n");
        }
        StringValue x = (StringValue) value;
        if(!fileTable.isDefined(x)){
            throw new MyException("The key already exists in the File Table\n");
        }
        try{
            BufferedReader br = new BufferedReader(new FileReader(x.getValue()));

            state.getFileTable().put(x,br);
        }
        catch (IOException exception)
        {
           throw new MyException(exception.getMessage());
        }

        return null;
    }

    public  MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type ExpressionType = expression.typecheck(typeEnv);
        if (ExpressionType.equals(new StringType())) {
            return typeEnv;
        }
        else
            throw new MyException("The Reading expression type is not a string");
    }
    public IStatement deepCopy()
    {
        return new openFile(expression);
    }
}