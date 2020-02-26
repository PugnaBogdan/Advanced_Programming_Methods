package Model.Collection.List;

import Model.Collection.List.MyIList;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MyList<T> implements MyIList<T> {
    private Vector<T> list;

    public MyList() {
        this.list = new Vector<T>(10);
    }

    @Override
    public void add(T value){
        list.add(value);
    }

    @Override
    public int size(){
        return list.size();
    }

    @Override
    public T set(int index, T value){
        return list.set(index,value);
    }

    @Override
    public T get(int index){
        return list.get(index);
    }

    @Override
    public String toString(){
        return list.toString();
    }

    public List<T> getValues()
    {
        return this.list;
    }
}