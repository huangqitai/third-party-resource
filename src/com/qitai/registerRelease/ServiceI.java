package com.qitai.registerRelease;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceI extends Remote {
    public String print(String s) throws RemoteException;
}
