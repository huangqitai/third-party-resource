package com.qitai.registerRelease;

import org.junit.Test;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    @Test
    public void creatServer(){
        //创建一个服务注册管理器
        Registry registry = null;
        try {
            registry = LocateRegistry.createRegistry(18080);
        } catch (RemoteException e) {
        }

        try {
            //传建一个服务
            ServiceImpl si = new ServiceImpl();
            //给服务绑定命名
            registry.rebind("myServer",si);

            System.out.print("我的服务");

        } catch (RemoteException e) {
        }

    }
}
