package com.stone.discovery;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.ZKPaths;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by young on 16/7/4.
 */
public class ZookeeperServiceDiscovery implements ServiceDiscovery{
    @Autowired
    CuratorFramework curatorFramework;
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
