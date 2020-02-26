package Model.Statement;

import Model.Collection.Dictionary.MyIDictionary;
import Model.Exceptions.MyException;
import Model.Expresions.Expression;
import Model.Expresions.RelationalExpression;
import Model.Expresions.VariableExpression;
import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.Type;

public class ForStatement implements IStatement {
    private String v;
    private Expression exp1, exp2, exp3;
    private IStatement stmt;

    public ForStatement(String v, Expression exp1, Expression exp2, Expression exp3, IStatement statement) {
        this.v = v;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.stmt = statement;
    }

    public String toString(){
        return "for("+ v +"="+ exp1 +"; "+ v +"<"+ exp2 +"; "+ v +"="+ exp3 +") "+ stmt;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        state.getStack().push(new WhileStatement(new RelationalExpression("<", new VariableExpression(v), exp2), new CompoundStatement(stmt,
                new AssignmentStatement(v, exp3))));
        state.getStack().push(new AssignmentStatement(v, exp1));
        state.getStack().push(new VariableDeclarationStatement(v,new IntType()));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.put(v, new IntType());
        if(exp1.typecheck(typeEnv).equals(new IntType()) &&
                exp2.typecheck(typeEnv).equals(new IntType()) &&
                exp3.typecheck(typeEnv).equals(new IntType()))
            return typeEnv;
        else throw new MyException("Exception: type is Wrong");

    }

    public IStatement deepCopy()
    {
        return new ForStatement(v, exp1.deepCopy(), exp2.deepCopy(), exp3.deepCopy(), stmt.deepCopy());
    }

}
