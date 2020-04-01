package com.alibaba.dubbo.samples.zkclient;

import org.I0Itec.zkclient.ZkClient;

public class ZookeeperOperation {

    public static void main(String[] args) {
        ZkClient zkClient = ZkClientUtil.connectZkClient();//创建zookeeper的java客户端连接

        if(!zkClient.exists("/test")) {
            zkClient.createPersistent("/test");
        }
        System.err.println("***************在/test下分别创建4种节点***************");
        if(!zkClient.exists("/test/永久节点1")) {
            zkClient.createPersistent("/test/永久节点1");
        }
        String s1 = zkClient.createPersistentSequential("/test/永久顺序节点", null);
        zkClient.createEphemeral("/test/临时节点1");
        zkClient.createEphemeralSequential("/test/临时顺序节点1", null);
        ZkClientUtil.showZkPath(zkClient, "/test");//展示test目录下的所有子目录

        System.err.println("***************关闭客户端再创建新的客户端***************");
        ZkClientUtil.closeZkClient();
        zkClient = ZkClientUtil.connectZkClient();//创建zookeeper的java客户端连接

        ZkClientUtil.showZkPath(zkClient, "/test");//展示test目录下的所有子目录

        System.err.println("***************删除节点s1***************");
        zkClient.delete(s1);//删除某个节点，如果该节点下有子节点，则会报错
        ZkClientUtil.showZkPath(zkClient, "/test");//展示test目录下的所有子目录

        zkClient.deleteRecursive("/test");//强制删除某个节点，并且删除节点下的所有子节点
        zkClient.close();
    }

}
