package com.alibaba.dubbo.samples.zkclient;

import org.I0Itec.zkclient.ZkClient;

public class ZookeeperDataOperation {

    public static void main(String[] args) {
        ZkClient zkClient = ZkClientUtil.connectZkClient();//创建zookeeper的java客户端连接

        if(!zkClient.exists("/test")) {
            zkClient.createPersistent("/test");
        }
        User user = new User();
        User user2 = new User();
        user.setName("阮浩");
        user2.setName("张三");
        zkClient.setZkSerializer(new ZkSerialize());//这里先设置好序列化工具再写入数据
        zkClient.createEphemeral("/test/ruanhao", user);
        zkClient.createEphemeral("/test/zhangsan");
        zkClient.writeData("/test/zhangsan", user2);
        ZkClientUtil.showZkPathData(zkClient, "/test", new ZkSerialize());//展示test目录下的所有子目录

        ZkClientUtil.closeZkClient();
    }

}
