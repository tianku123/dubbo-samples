package com.alibaba.dubbo.samples.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * 监听节点变化测试类
 */
public class ZookeeperSubscribeNodeListener {

    public static void main(String[] args) {
        new ZookeeperSubscribeNodeListener().subscribeDataChanges();
    }

    private void subscribeDataChanges() {
        ZkClient zkClient = ZkClientUtil.connectZkClient();//创建zookeeper的java客户端连接
        if (!zkClient.exists("/test")) {
            zkClient.createPersistent("/test");
        }
     
        zkClient.subscribeChildChanges("/test/Node", new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                String childs = "";
                if (currentChilds != null && currentChilds.size() > 0) {
                    childs += "[";
                    for (String s : currentChilds) {
                        childs += s + ",";
                    }
                    childs += "]";
                }
                System.out.println("ChildChange:" + parentPath + ",childs:" + childs);
            }
        });
        zkClient.createPersistent("/test/Node");
        sleep(100);
        zkClient.createPersistent("/test/Node/n1");
        sleep(100);
        zkClient.createPersistent("/test/Node/n2");
        sleep(100);
        zkClient.createPersistent("/test/Node/n3");
        sleep(100);
        zkClient.delete("/test/Node/n1");
        sleep(100);
        zkClient.delete("/test/Node/n2");
        sleep(100);
        zkClient.delete("/test/Node/n3");
        sleep(3000);
        System.out.println("****");
        zkClient.deleteRecursive("/test/Node");
        sleep(3000);
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
