package Model.Collection.Dictionary;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MyDictionary<T, K> implements MyIDictionary<T, K> {
    private Map<T,K> dictionary;

    public MyDictionary() {
        this.dictionary = new ConcurrentHashMap<>(10);
    }

    @Override
    public int size(){
        return dictionary.size();
    }

    @Override
    public synchronized K get(T key){
        return dictionary.get(key);
    }

    @Override
    public boolean isEmpty(){
        return dictionary.isEmpty();
    }

    @Override
    public synchronized K put(T key, K value){
        return dictionary.put(key,value);
    }

    @Override
    public Set<T> keySet() {
        return dictionary.keySet();
    }

    @Override
    public boolean containsKey(T name) {
        return dictionary.containsKey(name);
    }

    @Override
    public void remove(T id){
        dictionary.remove(id);
    }

    @Override
    public boolean isDefined(T key){
        try{
            K value=dictionary.get(key);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public void setValues(Map<T,K> new_values) {
        dictionary = new_values;
    }

    @Override
    public Map<T,K>  getValues()
    {
        return dictionary;
    }

    @Override
    public String toString(){
        return dictionary.toString();
    }
}