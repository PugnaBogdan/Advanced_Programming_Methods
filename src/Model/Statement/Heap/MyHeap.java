package Model.Statement.Heap;


import Model.Collection.Dictionary.MyDictionary;
import Model.Collection.Dictionary.MyIDictionary;

import java.util.*;

public class MyHeap<T, K> implements MyIHeap<T, K> {
    int addr;
    MyIDictionary<T, K> heap;
    public MyHeap(){
        addr = 0;
        heap = new MyDictionary<>();
    }
    @Override
    public int getNewAddress() {
        addr++;
        return addr;
    }

    @Override
    public int getAddress(){
        return addr;
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public K get(T key) {
        return heap.get(key);
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    @Override
    public K put(T key, K value) {
        return heap.put(key, value);
    }

    @Override
    public boolean isDefined(T key) {
        return heap.isDefined(key);
    }

    @Override
    public Set keySet() {
        return heap.keySet();
    }

    @Override
    public void remove(T id) {
        heap.remove(id);
    }

    @Override
    public boolean containsKey(T name) {
        return heap.containsKey(name);
    }

    @Override
    public Map<T, K> getValues() {
        return heap.getValues();
    }

    @Override
    public void setValues(Map new_values) {
        heap.setValues(new_values);
    }
    @Override
    public String toString(){
        return heap.toString();
    }

}