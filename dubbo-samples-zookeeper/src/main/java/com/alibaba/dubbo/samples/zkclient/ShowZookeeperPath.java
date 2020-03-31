package com.alibaba.dubbo.samples.zkclient;

import org.I0Itec.zkclient.ZkClient;

import java.util.List;

public class ShowZookeeperPath {
    private static String zkServer = "127.0.0.1:2181";//zookeeper地址

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient(zkServer);//创建zookeeper的java客户端连接

        showZkPath(zkClient, "/");

        zkClient.close();
    }

    /**
     *  遍历展示目录下的所有节点
     * @author LAN
     * @date 2018年12月3日
     * @param zkClient
     * @param root
     */
    public static void showZkPath(ZkClient zkClient, String root) {
        List<String> children = zkClient.getChildren(root);//获取节点下的所有直接子节点
        if (children.isEmpty()) {
            return;
        }
        for (String s : children) {
            String childPath = root.endsWith("/") ? (root + s) : (root + "/" + s);
            System.err.println(childPath);
            showZkPath(zkClient, childPath);//递归获取所有子节点
        }
    }

}
