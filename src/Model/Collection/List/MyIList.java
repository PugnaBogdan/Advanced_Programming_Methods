package Model.Collection.List;

import java.util.List;
import java.util.Optional;

public interface MyIList<T> {
    public void add(T value);
    public int size();
    public T set(int index, T value);
    public T get(int index);
    public List<T> getValues();
}