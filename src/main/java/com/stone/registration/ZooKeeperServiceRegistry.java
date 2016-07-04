package com.stone.registration;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by young on 16/7/4.
 */
public class ZooKeeperServiceRegistry implements ServiceRegistry{

    @Autowired
    CuratorFramework curatorFramework;
    private  ConcurrentHashMap<String, String> uriToZnodePath=new ConcurrentHashMap<>();

    @Override
    public void registerService(String name, String uri) {
        try {
            String znode = "/services/" + name;

            if (curatorFramework.checkExists().forPath(znode) == null) {
                curatorFramework.create().creatingParentsIfNeeded().forPath(znode);
            }

            String znodePath = curatorFramework
                    .create()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(znode+"/_", uri.getBytes());

            uriToZnodePath.put(uri, znodePath);
        } catch (Exception ex) {
            throw new RuntimeException("Could not register service \""
                    + name
                    + "\", with URI \"" + uri + "\": " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void unregisterService(String name, String uri) {
        try {
            if (uriToZnodePath.contains(uri)) {
                curatorFramework.delete().forPath(uriToZnodePath.get(uri));
            }
        } catch (Exception ex) {
            throw new RuntimeException("Could not unregister service \""
                    + name
                    + "\", with URI \"" + uri + "\": " + ex.getLocalizedMessage());
        }
    }

    @Override
    public String discoverServiceURI(String name) {
        try {
            String znode = "/services/" + name;

            List<String> uris = curatorFramework.getChildren().forPath(znode);
            return new String(curatorFramework.getData().forPath(ZKPaths.makePath(znode, uris.get(0))));
        } catch (Exception ex) {
            throw new RuntimeException("Service \"" + name + "\" not found: " + ex.getLocalizedMessage());
        }
    }
}
