package com.qitai.registerRelease;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

public class ServerRe {
    private  static final String BASE_SERVICES = "/services";
    private static final String  SERVICE_NAME="/products";

    @Test
    public void te(){
        register("127.0.0.1",2181);
    }
    public static  void register(String address,int port) {
        try {
            ZooKeeper zooKeeper = new ZooKeeper("localhost:2181",5000,(watchedEvent)->{});
            Stat exists = zooKeeper.exists(BASE_SERVICES + SERVICE_NAME, false);
            if(exists==null) {
                zooKeeper.create(BASE_SERVICES + SERVICE_NAME,"".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            String server_path = address+":"+port;
            //创建的临时的有序节点
            //临时的话断开连接了可以监听到,有序节点创建代表每一个节点否则相同节点名称无法创建
            zooKeeper.create(BASE_SERVICES + SERVICE_NAME+"/child",server_path.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println("产品服务注册成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
