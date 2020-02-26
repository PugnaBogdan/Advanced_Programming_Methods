package Model.Statement.Heap;

import Model.Collection.Dictionary.MyIDictionary;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface MyIHeap<T, K> extends MyIDictionary<T,K> {
    public int getNewAddress();
    public int getAddress();
}