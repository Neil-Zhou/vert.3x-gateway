package com.stone.registration;
/**
 * The Service registry knows all service verticles, a verticle registers here and will be traced. The registry also notify the router to add/remove routes to the services.
 * Created by young on 16/7/4.
 */
public interface ServiceRegistry {

    public void registerService(String name, String uri);

    public void unregisterService(String name, String uri);

}
