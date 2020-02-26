package Model.Collection.Dictionary;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface MyIDictionary<T, K> {
    public int size();
    public K get(T key);
    public boolean isEmpty();
    public K put(T key, K value);
    public boolean isDefined(T key);
    public Set<T> keySet();
    public void remove(T id);
    public boolean containsKey(T name);
    public Map<T,K>  getValues();
    public void setValues(Map<T,K> new_values);
}