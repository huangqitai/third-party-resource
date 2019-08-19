package com.qitai.designmode.prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Prototype implements Cloneable , Serializable {

    private static final long serialVersionUID = 1L;
    private String string;


    @Override
    public Object clone() throws CloneNotSupportedException {
        Prototype prototype =  (Prototype) super.clone();
        return prototype;
    }

    /*
    深复制,所拥有的属性都重新创建
     */
    public Object deepClone() throws IOException, ClassNotFoundException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(this);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);

        return ois.readObject();
    }
}
