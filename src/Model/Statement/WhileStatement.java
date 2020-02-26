package Model.Statement;

import Model.Exceptions.MyException;
import Model.Expresions.Expression;
import Model.ProgramState;
import Model.Collection.Dictionary.MyIDictionary;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;
import Model.Collection.Stack.MyIStack;

public class WhileStatement implements IStatement {
    private Expression expression;
    private IStatement statement;

    public WhileStatement(Expression expression, IStatement statement){
        this.expression = expression;
        this.statement = statement;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public IStatement getStatement() {
        return statement;
    }

    public void setStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public String toString() {
        return "while( " + expression.toString() + ") { " + statement.toString() + " }";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        MyIStack<IStatement> stack = state.getStack();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIDictionary<Integer, Value> heapTable = state.getHeapTable();


        Value expressionResult = expression.evaluate(symbolTable, heapTable);

        if (!expressionResult.getType().equals(new BoolType())) {
            throw new MyException("No a boolean condition\n");
        } else {
            BoolValue BoolCond = (BoolValue) expressionResult;
            if (BoolCond.getValue()) {
                stack.push(this);
                stack.push(statement);
            }
        }
        return null;
    }
    public  MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type ExpressionType = expression.typecheck(typeEnv);
        if (ExpressionType.equals(new BoolType())) {
            statement.typecheck(typeEnv);
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");
    }
    public IStatement deepCopy()
    {
        return new WhileStatement(expression.deepCopy(),statement);
    }
}