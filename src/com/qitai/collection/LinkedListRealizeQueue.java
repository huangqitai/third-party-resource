package com.qitai.collection;

import java.util.LinkedList;

public class LinkedListRealizeQueue<E> extends LinkedList<E>{

    public LinkedListRealizeQueue() {
    }

    public boolean add(E e){
        int size = this.size();
        this.addLast(e);
        if(this.size()>size){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public E peek() {
        if(this.isEmpty()){
            return null;
        }else
        return this.getFirst();
    }


}
