package org.mycode.util;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class JDBCConnectionUtil {
    private static final Logger log = Logger.getLogger(JDBCConnectionUtil.class);
    private final static String LINK_TO_CONFIG = "./src/main/resources/config.properties";
    private static BasicDataSource ds = new BasicDataSource();
    private static Map<String, Connection> connectionMap = new HashMap<>();
    static {
        Properties properties = new Properties();
        try(FileReader fr = new FileReader(LINK_TO_CONFIG)){
            properties.load(fr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ds.setDriverClassName(properties.getProperty("jdbc.driver"));
        ds.setUrl(properties.getProperty("jdbc.url"));
        ds.setUsername(properties.getProperty("jdbc.user"));
        ds.setPassword(properties.getProperty("jdbc.password"));
    }
    public static Connection getConnection(String forWitchModel) throws SQLException {
        log.debug("Getting connection from pool");
        if(!connectionMap.containsKey(forWitchModel)){
            connectionMap.put(forWitchModel, ds.getConnection());
        }
        return connectionMap.get(forWitchModel);
    }
    public static void closeConnection(String forWitchModel) throws SQLException {
        if(connectionMap.containsKey(forWitchModel)){
            log.debug("Closing connection for "+forWitchModel);
            connectionMap.get(forWitchModel).close();
            connectionMap.remove(forWitchModel);
        }
    }
}
