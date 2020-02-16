package org.mycode.service;

import java.util.HashMap;
import java.util.Map;

public class ServiceFactory {
    private static Map<String, Service> serviceMap = new HashMap<>();
    public static Service getServiceByName(String name){
        if(!serviceMap.containsKey(name)){
            switch (name){
                case "SkillService":
                    serviceMap.put(name, new SkillService());
                    break;
                case "AccountService":
                    serviceMap.put(name, new AccountService());
                    break;
                case "DeveloperService":
                    serviceMap.put(name, new DeveloperService());
                    break;
                default:
                    return null;
            }
        }
        return serviceMap.get(name);
    }
}
