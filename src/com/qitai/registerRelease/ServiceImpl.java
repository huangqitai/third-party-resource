package com.qitai.registerRelease;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServiceImpl extends UnicastRemoteObject implements ServiceI , Serializable {

    private static final long serialVersionUID = 1L;

    protected ServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String print(String s) throws RemoteException{
        System.out.println("客户端信息"+s);
        return "当前时间";
    }
}
