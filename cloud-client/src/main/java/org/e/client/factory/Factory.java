package org.e.client.factory;

import org.e.client.service.NetworkService;
import org.e.client.service.impl.IONetworkService;

public class Factory {

    public static NetworkService getNetworkService(){
        return IONetworkService.getInstance();
    }
}
