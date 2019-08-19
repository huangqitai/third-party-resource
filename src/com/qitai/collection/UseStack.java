package com.qitai.collection;

import org.junit.Test;


public class UseStack {
    @Test
    public void useStack(){
        Stack stack = new Stack();
        stack.push(6);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(11);
        stack.push(7);
        System.out.println(stack.size());
        stack.print();

    }

    @Test
    public void useQueue(){
        Queue queue = new Queue(10);
        queue.add(9);
        queue.add(7);
        queue.add(2);
        queue.add(3);
        queue.add(9);
        queue.add(6);

        queue.print();

        System.out.println(queue.poll());

        System.out.println(queue.size());

        queue.remove();
        System.out.println(queue.poll());
    }
}
