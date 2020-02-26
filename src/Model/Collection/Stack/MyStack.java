package Model.Collection.Stack;
import Model.Exceptions.MyException;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    private Stack<T>  stack;

    public MyStack() {
        this.stack = new Stack<T>();
    }

    public T pop() throws MyException {
        try {
            return stack.pop();
        }
        catch (Exception exception){
            throw new MyException("stack is empty");
        }
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty()
    {
        return stack.isEmpty();
    }

    @Override
    public String toString(){
        return stack.toString();
    }

    @Override
    public Stack<T> getStack() {
        return stack;
    }
}