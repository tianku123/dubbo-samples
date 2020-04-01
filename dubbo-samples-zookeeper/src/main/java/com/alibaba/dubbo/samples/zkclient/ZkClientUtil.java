package com.alibaba.dubbo.samples.zkclient;

import org.I0Itec.zkclient.ZkClient;

import java.util.List;

public class ZkClientUtil {
    private static String zkServer = "127.0.0.1:2181";//zookeeper地址
    private static ZkClient zkClient;

    public static ZkClient connectZkClient() {
        if (zkClient != null) return zkClient;
        zkClient = new ZkClient(zkServer);//创建zookeeper的java客户端连接
        return zkClient;
    }
    public static void closeZkClient() {
        if (zkClient != null) {
            zkClient.close();
            zkClient = null;
        }
    }

    /**
     *  遍历展示目录下的所有节点
     * @author LAN
     * @date 2018年12月3日
     * @param root
     */
    public static void showZkPath(String root) {
        showZkPath(connectZkClient(), root);
    }
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


    /**
     *  遍历展示目录下的所有节点
     * @author test
     * @date 2018年12月3日
     * @param root
     */
    public static void showZkPathData(String root, ZkSerialize serializer) {
        showZkPathData(connectZkClient(), root, serializer);
    }
    public static void showZkPathData(ZkClient zkClient, String root, ZkSerialize serializer) {
        zkClient.setZkSerializer(serializer);
        List<String> children = zkClient.getChildren(root);
        if(children.isEmpty()){
            return;
        }
        for(String s:children){
            String childPath = root.endsWith("/")?(root+s):(root+"/"+s);
            Object data = zkClient.readData(childPath, true);
            if(data!=null) {
                System.err.println(data.getClass());
            }
            System.err.println(childPath+"("+data+")");
            showZkPathData(zkClient, childPath, serializer);
        }
    }

}
