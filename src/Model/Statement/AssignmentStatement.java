package Model.Statement;

import Model.Collection.Dictionary.MyIDictionary;
import Model.Collection.Stack.MyIStack;
import Model.Expresions.Expression;
import Model.ProgramState;
import Model.Type.Type;
import Model.Exceptions.MyException;
import Model.Value.Value;

public class AssignmentStatement implements IStatement {
    private String id;
    private Expression expression;

    public AssignmentStatement(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    public String toString(){ return id+"="+ expression.toString();}
    public ProgramState execute(ProgramState state) throws MyException{

        MyIStack<IStatement> stack=state.getStack();
        MyIDictionary<String, Value> symbolTable= state.getSymbolTable();

        Value value = expression.evaluate(symbolTable,state.getHeapTable());

        if (symbolTable.isDefined(id)) {
            Type typeId = (symbolTable.get(id)).getType();

            if ((value.getType()).equals(typeId))
                symbolTable.put(id, value);

            else
                throw new MyException("declared type of variable" + id + " and type of the assigned expression do not match\n");
        }
        else
            throw new MyException("the used variable" +id + " was not declared before\n");

        return null;
    }

    public MyIDictionary<String,Type> typecheck(MyIDictionary<String, Type> typeEnv) throws
            MyException{
        Type typevar = typeEnv.get(id);
        Type typexp = expression.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");
    }


    public IStatement deepCopy()
    {
        return new AssignmentStatement(new String(id),expression);
    }
}
