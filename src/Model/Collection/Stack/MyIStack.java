package Model.Collection.Stack;
import Model.Exceptions.MyException;

import java.util.Stack;

public interface MyIStack<T> {

    public T pop() throws MyException;
    public void push(T v);
    public boolean isEmpty();
    public Stack<T> getStack();
}