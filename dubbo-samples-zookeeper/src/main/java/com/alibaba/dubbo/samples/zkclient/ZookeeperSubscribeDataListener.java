package com.alibaba.dubbo.samples.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * 监听数据变化测试类
 */
public class ZookeeperSubscribeDataListener {

    public static void main(String[] args) {
        new ZookeeperSubscribeDataListener().subscribeDataChanges();
    }

    private void subscribeDataChanges() {
        ZkClient zkClient = ZkClientUtil.connectZkClient();//创建zookeeper的java客户端连接
        if(!zkClient.exists("/test")) {
            zkClient.createPersistent("/test");
        }
        //注册监听事件
        zkClient.subscribeDataChanges("/test/Node", new IZkDataListener() {
            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("DataDeleted:"+dataPath);
            }
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("DataChange:"+dataPath+",data:"+data);
            }
        });
        System.out.println("****************************************");
        zkClient.createPersistent("/test/Node");
        for (int i = 0; i< 5; i++) {
            sleep(100);
            zkClient.writeData("/test/Node", i);
        }
        zkClient.delete("/test/Node");
        sleep(2000);
        zkClient.unsubscribeAll();
        zkClient.close();
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
