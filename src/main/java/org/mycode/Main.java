package org.mycode;

import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }
        tomcat.setPort(Integer.parseInt(webPort));
        tomcat.addWebapp("/", new File("/").getAbsolutePath());
        tomcat.init();
        tomcat.start();
        tomcat.getServer().await();
    }
}
